package slogo.view;

import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import slogo.controller.Turtle;

public class TurtleCanvas extends Pane implements IVisualize {

  private static final double CANVAS_WIDTH = 300;
  private static final double CANVAS_HEIGHT = 300;
  private static final Color DEFAULT_PEN_COLOR = Color.BLACK;
  private static final int DEFAULT_PEN_THICKNESS = 1;
  private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;

  private Turtle myTurtle;

  private GridPane myLayoutPane;
  private Canvas myCanvas;
  private GraphicsContext myGraphicsContext;

  TurtleCanvas() {
    initializeCanvas();
    initializeDefaults();
    initializeLayoutPane();
  }

  private void initializeCanvas() {
    myCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    this.getChildren().add(myCanvas);

    myGraphicsContext = myCanvas.getGraphicsContext2D();
  }

  private void initializeDefaults() {
    setPenThickness(DEFAULT_PEN_THICKNESS);
    setPenColor(DEFAULT_PEN_COLOR);
    setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
  }

  private void initializeLayoutPane() {
    myLayoutPane = new GridPane();
    myCanvas = new Canvas();
    myLayoutPane.add(myCanvas, 0, 0);
  }


  @Override
  public void drawPath(Path p) {
    // TODO: Draw new paths
  }

  @Override
  public void setPenColor(Color c) {
    myGraphicsContext.setStroke(c);
  }

  @Override
  public void setHeading(int angle) {
    // TODO: set turtle heading
  }

  @Override
  public void setPenThickness(int thickness) {
    myGraphicsContext.setLineWidth(thickness);
  }

  @Override
  public void setBackgroundColor(Color c) {
    BackgroundFill backgroundFill = new BackgroundFill(c, null, null);
    Background background = new Background(backgroundFill);
    this.setBackground(background);
  }

  @Override
  public void clear() {
    myGraphicsContext.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
  }

  @Override
  public Bounds getBounds() {
    return this.getLayoutBounds();
  }
}
