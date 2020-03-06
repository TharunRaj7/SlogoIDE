package slogo.view.workspace;

import java.io.File;
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

  protected static final double PADDING = 5;

  protected Node myLayout;
  protected List<GuiElement> myGuiElements;

  protected Parser myParser;
  protected ResourceBundle myResources;

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

  protected void initializeResources(String language) {
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
    List<Element> elements = new ArrayList<>();
    for (GuiElement guiElement : myGuiElements) {
      elements.add(guiElement.toXMLElement());
    }

    XMLBuilder xmlBuilder = XMLBuilder.newInstance();
    xmlBuilder.buildXML(filepath, "workspace", this.getClass().getSimpleName(), elements);
  }
}
