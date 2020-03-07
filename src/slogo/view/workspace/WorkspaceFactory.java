package slogo.view.workspace;

import static java.util.Map.entry;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import slogo.controller.TurtleController;
import slogo.model.Parser;
import slogo.view.ExceptionFeedback;
import slogo.view.ExceptionFeedback.ExceptionType;
import slogo.view.element.Console;
import slogo.view.element.GuiElement;
import slogo.view.element.ResourcePanel;
import slogo.view.element.ScriptEditor;
import slogo.view.element.TurtleCanvas;
import slogo.view.element.VariableExplorer;

public class WorkspaceFactory {

  private static final String DEFAULT_ELEMENTS_PACKAGE = "slogo.view.element.";

  private static final List<List<String>> DEFAULT_ELEMENTS = List.of(
      List.of(TurtleCanvas.class.getName(), ScriptEditor.class.getName()),
      List.of(Console.class.getName(), VariableExplorer.class.getName(), ResourcePanel.class.getName())
  );
  private static final List<Double> DEFAULT_VERTICAL_DIVIDERS = List.of(
      0.7
  );
  private static final List<List<Double>> DEFAULT_HORIZONTAL_DIVIDERS = List.of(
      List.of(0.5),
      List.of(0.4, 0.7)
  );
  public static final String DEFAULT_LANGUAGE = "English";


  public static Workspace defaultWorkspace(String language) {
    CustomWorkspace workspace = new CustomWorkspace(language);
    TurtleController tc = new TurtleController();
    Parser parser = new Parser(tc, language);
    workspace.setParser(parser);

    Map<String, Object> parameters = Map.ofEntries(
    entry("class slogo.controller.TurtleController", tc),
    entry("class java.util.ResourceBundle", workspace.getResourceBundle()),
    entry("class slogo.model.Parser", parser)
    );

    try {
      List<List<GuiElement>> elements = new ArrayList<>();
      for (List<String> row : DEFAULT_ELEMENTS) {
        List<GuiElement> elementRow = new ArrayList<>();
        for (String element : row) {
          Constructor<?> constructor = Class.forName(element).getDeclaredConstructors()[0];
          Object[] param = new Object[constructor.getParameterCount()];
          for (int i = 0; i < constructor.getParameterCount(); i++) {
            param[i] = parameters.get(constructor.getParameterTypes()[i].toString());
          }
          GuiElement ge = (GuiElement) constructor.newInstance(param);
          elementRow.add(ge);
        }
        elements.add(elementRow);
      }

      workspace.setLayout(elements, DEFAULT_VERTICAL_DIVIDERS, DEFAULT_HORIZONTAL_DIVIDERS);

    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.GUI_EXCEPTION,
          "Failed to create the default workspace." +
              "Please close the application and report this error.");
    }

    return workspace;
  }

  public static Workspace fromXML(String filepath) {
    try {
      CustomWorkspace workspace = null;
      TurtleController tc = new TurtleController();
      Parser parser;

      File file = new File(filepath);

      DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = dBuilder.parse(file);

      String language;

      List<List<GuiElement>> rows = new ArrayList<>();
      List<Double> vDividers = new ArrayList<>();
      List<List<Double>> hDividers = new ArrayList<>();

      NodeList nodes = doc.getChildNodes().item(0).getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);
        if (node.getNodeName().equals("language")) {
          language = node.getTextContent();
          workspace = new CustomWorkspace(language);
          parser = new Parser(tc, language);
          workspace.setParser(parser);
        }
        if (node.getNodeName().equals("layout")) {
          rows = processRows(workspace, tc, node.getChildNodes());
          vDividers = processVerticalDividers(node.getChildNodes());
          hDividers = processHorizontalDividers(node.getChildNodes());
        }
      }

      workspace.setLayout(rows, vDividers, hDividers);

      return workspace;

    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.XML_EXCEPTION,
          "Failed to build workspace from XML file. The file is corrupted or otherwise" +
              "cannot be loaded. Please check the XML syntax or try another file.");
    }

    return null;
  }

  private static List<List<GuiElement>> processRows(CustomWorkspace workspace, TurtleController tc, NodeList nodes) {

    Map<String, Object> parameters = Map.ofEntries(
        entry("class slogo.controller.TurtleController", tc),
        entry("class java.util.ResourceBundle", workspace.getResourceBundle()),
        entry("class slogo.model.Parser", workspace.getParser())
    );

    List<List<GuiElement>> layout = new ArrayList<>();
    try {
      for (int r = 0; r < nodes.getLength(); r++) {
        List<GuiElement> rowElements = new ArrayList<>();
        Node row = nodes.item(r);
        if (!row.getNodeName().equals("row"))
          continue;

        for (int e = 0; e < row.getChildNodes().getLength(); e++) {
          if (row.getChildNodes().item(e).getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) row.getChildNodes().item(e);

            Constructor<?> constructor =
                Class.forName(DEFAULT_ELEMENTS_PACKAGE + element.getNodeName())
                    .getDeclaredConstructors()[0];

            Object[] param = new Object[constructor.getParameterCount()];
            for (int i = 0; i < constructor.getParameterCount(); i++) {
              param[i] = parameters.get(constructor.getParameterTypes()[i].toString());
            }
            GuiElement ge = (GuiElement) constructor.newInstance(param);
            ge.setContentsFromXMLElement(element);
            rowElements.add(ge);
          }
        }

        layout.add(rowElements);
      }
    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.XML_EXCEPTION,
          "Failed to load GUI elements from XML file. Check layout syntax or try another file.");
    }

    return layout;
  }

  private static List<List<Double>> processHorizontalDividers(NodeList nodes) {
    List<List<Double>> dividers = new ArrayList<>();

    for (int n = 0; n < nodes.getLength(); n++) {
      Node node = nodes.item(n);
      if (node.getNodeName().equals("horizontalDividers")) {
        for (int r = 0; r < node.getChildNodes().getLength(); r++) {
          if (node.getChildNodes().item(r).getNodeType() == Node.ELEMENT_NODE) {
            List<Double> rowDividers = new ArrayList<>();
            Node row = node.getChildNodes().item(r);
            for (String divider : row.getTextContent().split("\\s+")) {
              rowDividers.add(Double.valueOf(divider));
            }
            dividers.add(rowDividers);
          }
        }
        break;
      }
    }
    return dividers;
  }

  private static List<Double> processVerticalDividers(NodeList nodes) {
    List<Double> dividers = new ArrayList<>();

    for (int n = 0; n < nodes.getLength(); n++) {
      Node node = nodes.item(n);
      if (node.getNodeName().equals("verticalDividers")) {
        for (String divider : node.getTextContent().split("\\s+")) {
          dividers.add(Double.valueOf(divider));
        }
        break;
      }
    }
    return dividers;
  }


}
