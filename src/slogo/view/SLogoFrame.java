package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.controller.Turtle;
import slogo.model.Parser;
import slogo.utility.Location;
import slogo.view.element.Console;
import slogo.view.element.GuiElement;
import slogo.view.element.ScriptEditor;
import slogo.view.element.TurtleCanvas;
import slogo.view.element.VariableExplorer;
import slogo.view.utility.MenuFactory;

public class SLogoFrame extends Application implements IFrame {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 600;
  private static final double PADDING = 5;
  private static final String DEFAULT_RESOURCES_PACKAGE = SLogoFrame.class.getPackageName() + ".resources.";
  private static final String DEFAULT_LANGUAGE = "English";

  private Stage myPrimaryStage;
  private Pane myLayout;
  private List<GuiElement> myGuiElements;
  private Parser myParser;
  private ResourceBundle myResources;

  public SLogoFrame() {
    super();
  }

  @Override
  public void start(Stage primaryStage) {
    myPrimaryStage = primaryStage;
    initializeResources(DEFAULT_LANGUAGE);
    initializeLayoutPane();
    initializeStage();
    myPrimaryStage.show();
  }

  private void initializeResources(String language) {
    try {
      myResources = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + language);
    } catch (Exception e) {
      initializeResources(getLanguage());
    }
  }

  private void initializeLayoutPane() {
    VBox layout = new VBox();
    SplitPane topRow = new SplitPane();
    SplitPane botRow = new SplitPane();
    myGuiElements = new ArrayList<>();

    Turtle turtle = new Turtle(new Location(0, 0), 0.0, "slogo/view/resources/Turtle.gif");
    TurtleCanvas tc = new TurtleCanvas(turtle, myResources);
    myGuiElements.add(tc);
    turtle.giveTurtleCanvas(tc);
    myParser = new Parser(turtle, getLanguage());

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

    layout.getChildren().add(MenuFactory.makeSlogoMenu(this));
    layout.getChildren().add(sp);

    myLayout = layout;
  }

  private void initializeStage() {
    myPrimaryStage.setTitle(myResources.getString("title"));
    Scene primaryScene = new Scene(myLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
    myPrimaryStage.setScene(primaryScene);
  }

  public void close() {
    myPrimaryStage.close();
  }

  public void setLanguage(String language) {
    initializeResources(language);
    myParser.updateLanguage(getLanguage());
    for (GuiElement guie : myGuiElements) {
      guie.updateResources(myResources);
    }
    for (Object n : myLayout.getChildren()) {
      if (n instanceof MenuBar) {
        myLayout.getChildren().remove(n);
        break;
      }
    }
    myLayout.getChildren().add(0, MenuFactory.makeSlogoMenu(this));
    myPrimaryStage.setTitle(myResources.getString("title"));
  }

  public ResourceBundle getResources() {
    return myResources;
  }

  private String getLanguage() {
    return myResources.getBaseBundleName().substring(
        myResources.getBaseBundleName().lastIndexOf('.')+1
    );
  }
}
