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
import slogo.view.element.Console;
import slogo.view.element.GuiElement;
import slogo.view.element.ScriptEditor;
import slogo.view.element.TurtleCanvas;
import slogo.view.element.VariableExplorer;

public class WorkspaceFactory {

  private static final String DEFAULT_ELEMENTS_PACKAGE = "slogo.view.element.";

  private static final List<List<String>> DEFAULT_ELEMENTS = List.of(
      List.of(TurtleCanvas.class.getName(), ScriptEditor.class.getName()),
      List.of(Console.class.getName(), VariableExplorer.class.getName())
  );
  private static final List<Double> DEFAULT_VERTICAL_DIVIDERS = List.of(
      0.7
  );
  private static final List<List<Double>> DEFAULT_HORIZONTAL_DIVIDERS = List.of(
      List.of(0.5),
      List.of(0.7)
  );


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

      System.out.println("Successfully created layout");

    } catch (Exception e) {
      e.printStackTrace();
    }

    return workspace;
  }

  public static Workspace fromXML(String filepath) {
    try {
      File file = new File(filepath);

      DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      Document doc = dBuilder.parse(file);

      String type = doc.getFirstChild().getAttributes().item(0).getNodeValue();

      Workspace w = new CustomWorkspace("English");

      List<GuiElement> elements = new ArrayList<>();
      NodeList nodes = doc.getChildNodes().item(0).getChildNodes();
      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Class elementClass = Class.forName(DEFAULT_ELEMENTS_PACKAGE + node.getNodeName());
          System.out.println(Arrays.toString(elementClass.getDeclaredMethods()));
          //elementClass.getMethod("fromXMLElement", Element.class).invoke(elementClass, node);
        }
      }
      //System.out.println(elements);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

}
