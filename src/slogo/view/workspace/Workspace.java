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

  /**
   * Initializes resources and layout for a new workspace with a provided language.
   * @param language frame language
   */
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

  /**
   * Sets the workspace's language, updating component GuiElements.
   * @param language the new language
   */
  public void setLanguage(String language) {
    initializeResources(language);
    myParser.updateLanguage(language);
    for (GuiElement guie : myGuiElements) {
      guie.updateResources(myResources);
    }
  }

  /**
   * Populates an XML file with information describing this workspace.
   *
   * Used to store a workspace in an XML for importing/exporting.
   * @param filepath the XML file to write to
   */
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

  /**
   * Sets this workspace's parser's language independent of its GUI elements' language.
   *
   * Used when GUI resources do not exist for a language, but parsing should still happen in that
   * language. e.g., German GUI labels do not exist, so English is displayed by default, but
   * parsing still happens in German.
   * @param language the new language
   */
  public void setParserLanguage(String language) {
    myParser.updateLanguage(language);
  }
}
