package slogo.view;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.controller.Turtle;
import slogo.model.Parser;
import slogo.utility.Location;
import slogo.view.element.Console;
import slogo.view.element.ScriptEditor;
import slogo.view.element.TurtleCanvas;
import slogo.view.utility.MenuFactory;

public class SLogoFrame extends Application implements IFrame {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 600;
  private static final double PADDING = 5;
  private static final String DEFAULT_RESOURCES_PACKAGE = SLogoFrame.class.getPackageName() + ".resources.";
  private static final String DEFAULT_LANGUAGE = "English";

  private Stage myPrimaryStage;
  private Pane myLayout;
  private Turtle myTurtle;
  private TurtleCanvas tc;
  private Parser myParser;
  private ResourceBundle myResources;

  public SLogoFrame() {
    super();
  }

  @Override
  public void start(Stage primaryStage) {
    myPrimaryStage = primaryStage;
    initializeResources(DEFAULT_LANGUAGE);
    initializeTurtleComponents();
    initializeParser();
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

  private void initializeTurtleComponents() {
    myTurtle = new Turtle(new Location(0,0), 0.0, "slogo/view/resources/Turtle.gif");
    tc = new TurtleCanvas(myTurtle, myResources);
    myTurtle.giveTurtleCanvas(tc);
  }

  private void initializeParser() {
    myParser = new Parser(myTurtle, getLanguage());
  }

  private void initializeLayoutPane() {
    VBox layout = new VBox();
    SplitPane topRow = new SplitPane();
    SplitPane botRow = new SplitPane();

    topRow.getItems().add(tc);

    topRow.getItems().add(new ScriptEditor(myParser, myResources));
    topRow.setDividerPositions(0.5f);

    botRow.getItems().add(new Console(myParser));

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
    initializeParser();
    initializeLayoutPane();
    initializeStage();
    tc.updateResources(myResources);
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
