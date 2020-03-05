package slogo.view.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import slogo.view.element.GuiElement;

public class WorkspaceFactory {

  private static final String DEFAULT_ELEMENTS_PACKAGE = "slogo.view.element.";

  public static Workspace defaultWorkspace(String language) {
    return new DefaultWorkspace(language);
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

    return new DefaultWorkspace("English");
  }
}
