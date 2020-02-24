package slogo.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import slogo.controller.Turtle;
import slogo.model.Parser;
import slogo.utility.Location;
import slogo.utility.TurtleTesting;
import slogo.view.element.Console;
import slogo.view.element.GuiElement;
import slogo.view.element.ScriptEditor;
import slogo.view.element.TurtleCanvas;

public class SLogoFrame extends Application implements IFrame {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 600;
  private static final double PADDING = 5;
  private Control myLayout;
  private Turtle myTurtle;
  private TurtleCanvas tc;

  public SLogoFrame() {
    super();
  }

  @Override
  public void start(Stage primaryStage) {
    initializeComponents();
    initializeLayoutPane();
    initializeStage(primaryStage);
    primaryStage.show();
  }

  private void initializeComponents() {
    tc = new TurtleCanvas();
    myTurtle = new Turtle(tc, new Location(0,0), 0.0, "");
  }

  private void initializeLayoutPane() {
    SplitPane topRow = new SplitPane();
    SplitPane botRow = new SplitPane();

    //TurtleTesting.testPathDrawing(tc);

    topRow.getItems().add(tc);
    Parser parser = new Parser();
    parser.giveTurtle(myTurtle);
    topRow.getItems().add(new ScriptEditor(parser));
    topRow.setDividerPositions(0.5f);

    botRow.getItems().add(new Console(parser));

    SplitPane sp = new SplitPane();
    sp.setOrientation(Orientation.VERTICAL);
    sp.setPadding(new Insets(PADDING));
    sp.getItems().addAll(topRow, botRow);
    sp.setDividerPositions(0.7f);

    myLayout = sp;
  }

  private void initializeStage(Stage primaryStage) {
    primaryStage.setTitle("SLogo Interpreter");
    // TODO: pull title from resources file
    Scene primaryScene = new Scene(myLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setScene(primaryScene);
  }

  @Override
  public void addPanel(Pane pane, int columnIndex, int rowIndex) {
    //myLayoutPane.add(pane, columnIndex, rowIndex);
    // do nothing; pending API change, since initializing should be handled by SLogoFrameFactory
  }

}
