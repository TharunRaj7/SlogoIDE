package slogo.view.workspace;

import static java.util.Map.entry;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

  private static final String MISSING_LANGUAGE = "MissingLanguage";


  /**
   * Builds a default workspace using declared constant specifications.
   * @param language the frame language
   * @return new Workspace
   */
  public static Workspace defaultWorkspace(String language) {
    CustomWorkspace workspace = new CustomWorkspace(language);
    TurtleController tc = new TurtleController();
    workspace.setParser(new Parser(tc, language));

    Map<String, Object> parameters = getConstructionParametersForWorkspace(workspace, tc);

    try {
      List<List<GuiElement>> elements = new ArrayList<>();
      for (List<String> row : DEFAULT_ELEMENTS) {
        List<GuiElement> elementRow = new ArrayList<>();
        for (String element : row) {
          Constructor<?> constructor = Class.forName(element).getDeclaredConstructors()[0];
          GuiElement ge = createGuiElementFromConstructor(parameters, constructor);
          elementRow.add(ge);
        }
        elements.add(elementRow);
      }

      workspace.setLayout(elements, DEFAULT_VERTICAL_DIVIDERS, DEFAULT_HORIZONTAL_DIVIDERS);

    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.GUI_EXCEPTION,
          "Failed to create the default workspace." +
              " Please close the application and report this error.");
    }

    return workspace;
  }

  private static Map<String, Object> getConstructionParametersForWorkspace(CustomWorkspace workspace, TurtleController tc) {
    return Map.ofEntries(
        entry("class slogo.controller.TurtleController", tc),
        entry("class java.util.ResourceBundle", workspace.getResourceBundle()),
        entry("class slogo.model.Parser", workspace.getParser())
    );
  }

  /**
   * Builds a workspace from an XML file.
   *
   * Reads XML element node names, then uses reflection to create the appropriate GuiElement for
   * each node and set its contents.
   * @param filepath the XML file
   * @return new Workspace
   */
  public static Workspace fromXML(String filepath) {
    try {
      File file = new File(filepath);

      DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = dBuilder.parse(file);

      String language = null;

      NodeList nodes = doc.getChildNodes().item(0).getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);
        if (node.getNodeName().equals("language")) {
          language = node.getTextContent();
        }
        if (node.getNodeName().equals("layout")) {
          if (language == null) {
            language = MISSING_LANGUAGE;
          }

          return createWorkspaceFromLayoutNode(language, node);
        }
      }
    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.XML_EXCEPTION,
          "Failed to build workspace from XML file. The file is corrupted or otherwise" +
              "cannot be loaded. Please check the XML syntax or try another file.");
    }

    return null;
  }

  private static CustomWorkspace createWorkspaceFromLayoutNode(String language, Node node) {
    List<List<GuiElement>> rows;
    List<Double> vDividers;
    List<List<Double>> hDividers;
    TurtleController tc = new TurtleController();
    CustomWorkspace workspace = createWorkspace(language, tc);

    rows = processRows(workspace, tc, node.getChildNodes());
    vDividers = processVerticalDividers(node.getChildNodes());
    hDividers = processHorizontalDividers(node.getChildNodes());
    workspace.setLayout(rows, vDividers, hDividers);
    return workspace;
  }

  private static CustomWorkspace createWorkspace(String language, TurtleController tc) {
    CustomWorkspace workspace;
    Parser parser;
    workspace = new CustomWorkspace(language);
    parser = new Parser(tc, language);
    workspace.setParser(parser);
    return workspace;
  }

  private static List<List<GuiElement>> processRows(CustomWorkspace workspace, TurtleController tc, NodeList nodes) {

    Map<String, Object> parameters = getConstructionParametersForWorkspace(workspace, tc);

    List<List<GuiElement>> layout = new ArrayList<>();
    try {
      for (int r = 0; r < nodes.getLength(); r++) {
        Node row = nodes.item(r);
        if (!row.getNodeName().equals("row"))
          continue;

        List<GuiElement> rowElements = processRow(parameters, row);

        layout.add(rowElements);
      }
    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.XML_EXCEPTION,
          "Failed to load GUI elements from XML file. Check layout syntax or try another file.");
    }

    return layout;
  }

  private static List<GuiElement> processRow(Map<String, Object> parameters, Node row)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
    List<GuiElement> rowElements = new ArrayList<>();
    for (int e = 0; e < row.getChildNodes().getLength(); e++) {
      if (row.getChildNodes().item(e).getNodeType() == Node.ELEMENT_NODE) {
        Element element = (Element) row.getChildNodes().item(e);

        GuiElement ge = createGuiElement(parameters, element);
        rowElements.add(ge);
      }
    }
    return rowElements;
  }

  private static GuiElement createGuiElement(Map<String, Object> parameters, Element element)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
    Constructor<?> constructor =
        Class.forName(DEFAULT_ELEMENTS_PACKAGE + element.getNodeName())
            .getDeclaredConstructors()[0];

    GuiElement ge = createGuiElementFromConstructor(parameters, constructor);
    ge.setContentsFromXMLElement(element);
    return ge;
  }

  private static GuiElement createGuiElementFromConstructor(Map<String, Object> parameters,
      Constructor<?> constructor)
      throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
    Object[] param = new Object[constructor.getParameterCount()];
    for (int i = 0; i < constructor.getParameterCount(); i++) {
      param[i] = parameters.get(constructor.getParameterTypes()[i].toString());
    }
    return (GuiElement) constructor.newInstance(param);
  }

  private static List<List<Double>> processHorizontalDividers(NodeList nodes) {
    List<List<Double>> dividers = new ArrayList<>();

    for (int n = 0; n < nodes.getLength(); n++) {
      Node node = nodes.item(n);
      if (node.getNodeName().equals("horizontalDividers")) {
        dividers = processHorizontalDividersNode(node);
        break;
      }
    }
    return dividers;
  }

  private static List<List<Double>> processHorizontalDividersNode(Node node) {
    List<List<Double>> dividers = new ArrayList<>();
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
