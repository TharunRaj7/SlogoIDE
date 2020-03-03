package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import slogo.controller.Turtle;
import slogo.model.Parser;
import slogo.utility.Location;
import slogo.view.element.Console;
import slogo.view.element.GuiElement;
import slogo.view.element.ScriptEditor;
import slogo.view.element.TurtleCanvas;
import slogo.view.element.VariableExplorer;

public class Workspace extends Tab {

  private static final double PADDING = 5;

  private Node myLayout;
  private List<GuiElement> myGuiElements;

  private Parser myParser;
  private ResourceBundle myResources;

  public Workspace(String language) {
    initializeResources(language);
    initializeLayoutPane();
    this.setText("Workspace");
    this.setContent(myLayout);

    this.setOnCloseRequest(e -> {
      if (this.getTabPane().getTabs().size()-1 <= 1) {
        this.getTabPane().setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
      }
    });

  }

  private void initializeLayoutPane() {
    SplitPane topRow = new SplitPane();
    SplitPane botRow = new SplitPane();
    myGuiElements = new ArrayList<>();

    Turtle turtle = new Turtle(new Location(0, 0), 0.0, "slogo/view/resources/Turtle.gif");
    TurtleCanvas tc = new TurtleCanvas(turtle, myResources);
    myGuiElements.add(tc);
    turtle.giveTurtleCanvas(tc);
    myParser = new Parser(turtle, SLogoFrame.getResourceLanguage(myResources));

    topRow.getItems().add(tc);
    ScriptEditor se = new ScriptEditor(myParser, turtle, myResources);
    topRow.getItems().add(se);
    myGuiElements.add(se);
    topRow.setDividerPositions(0.5f);

    Console console = new Console(myParser);
    botRow.getItems().add(console);
    myGuiElements.add(console);
    VariableExplorer ve = new VariableExplorer();
    botRow.getItems().add(ve);
    myGuiElements.add(ve);
    botRow.setDividerPositions(0.7f);

    SplitPane sp = new SplitPane();
    sp.setOrientation(Orientation.VERTICAL);
    sp.setPadding(new Insets(PADDING));
    sp.getItems().addAll(topRow, botRow);
    sp.setDividerPositions(0.7f);

    myLayout = sp;
  }


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
}
