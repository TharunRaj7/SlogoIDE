package slogo.view.workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import org.w3c.dom.Element;
import slogo.model.Parser;
import slogo.view.SLogoFrame;
import slogo.view.element.GuiElement;
import slogo.view.utility.XMLBuilder;

public abstract class Workspace extends Tab {

  Node myLayout;
  List<GuiElement> myGuiElements;

  Parser myParser;
  ResourceBundle myResources;

  public Workspace(String language) {
    initializeResources(language);
    initializeLayoutPane();
    this.setContent(myLayout);

    this.setOnCloseRequest(e -> {
      if (this.getTabPane().getTabs().size()-1 <= 1) {
        this.getTabPane().setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
      }
    });
  }

  protected abstract void initializeLayoutPane();

  private void initializeResources(String language) {
    try {
      myResources = ResourceBundle.getBundle(SLogoFrame.DEFAULT_RESOURCES_PACKAGE + language);
    } catch (Exception e) {
      initializeResources(SLogoFrame.getResourceLanguage(myResources));
    }
  }

  public void setLanguage(String language) {
    initializeResources(language);
    myParser.updateLanguage(language);
    for (GuiElement guie : myGuiElements) {
      guie.updateResources(myResources);
    }
  }

  public void sendToXML(String filepath) {
    XMLBuilder xmlBuilder = XMLBuilder.newInstance();
    List<Element> elements = new ArrayList<>();

    Element languageNode = xmlBuilder.createElement("language");
    languageNode.appendChild(xmlBuilder.createTextNode(SLogoFrame.getResourceLanguage(myResources)));
    elements.add(languageNode);

    Element layout = generateLayoutXMLElement();
    elements.add(layout);

    xmlBuilder.buildXML(filepath, "workspace", this.getClass().getSimpleName(), elements);
  }

  protected abstract Element generateLayoutXMLElement();

  public void setParserLanguage(String language) {
    myParser.updateLanguage(language);
  }
}
