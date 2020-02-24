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
import slogo.model.Parser;
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

  public SLogoFrame() {
    super();
  }

  @Override
  public void start(Stage primaryStage) {
    initializeLayoutPane();
    initializeStage(primaryStage);
    primaryStage.show();
  }

  private void initializeLayoutPane() {
    SplitPane topRow = new SplitPane();
    SplitPane botRow = new SplitPane();

    TurtleCanvas tc = new TurtleCanvas();
    TurtleTesting.testPathDrawing(tc);

    topRow.getItems().add(tc);
    topRow.getItems().add(new ScriptEditor(new Parser()));
    topRow.setDividerPositions(0.5f);

    botRow.getItems().add(new Console(new Parser()));

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
