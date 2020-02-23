package slogo.view;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import slogo.controller.Turtle;

public class TurtleCanvas extends GridPane implements IVisualize {

  private static final double CANVAS_WIDTH = 300;
  private static final double CANVAS_HEIGHT = 300;
  private static final double TRANSLATE_X = CANVAS_WIDTH / 2.0;
  private static final double TRANSLATE_Y = CANVAS_HEIGHT / 2.0;

  private static final Color DEFAULT_PEN_COLOR = Color.WHITE;
  private static final int DEFAULT_PEN_THICKNESS = 1;
  private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;
  private static final double PADDING = 5;
  private static final double GAP = 2;

  private Turtle myTurtle;

  private Canvas myCanvas;
  private Pane myCanvasHolder;
  private GraphicsContext myGraphicsContext;

  private double[] myTurtleLocation;

  TurtleCanvas() {
    initializeCanvas();
    initializeDefaults();
    initializeLayoutPane();

    myTurtleLocation = new double[]{0, 0};
  }

  private void initializeCanvas() {
    myCanvasHolder = new Pane();
    myCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
    myCanvasHolder.getChildren().add(myCanvas);

    myGraphicsContext = myCanvas.getGraphicsContext2D();
  }

  private void initializeDefaults() {
    setPenThickness(DEFAULT_PEN_THICKNESS);
    setPenColor(DEFAULT_PEN_COLOR);
    setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
  }

  private void initializeLayoutPane() {
    Pane menuBar = makeMenuBar();
    this.add(menuBar, 0, 0);
    this.add(myCanvasHolder, 0, 1);
    setGrowPriority(myCanvasHolder);

    this.setPadding(new Insets(PADDING));
    this.setHgap(GAP);
    this.setVgap(GAP);

    this.setBorder(new Border(new BorderStroke(Color.GREEN,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    setGrowPriority(this);
  }

  private Pane makeMenuBar() {
    HBox menu = new HBox();

    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setOnAction(e -> {
        Color c = colorPicker.getValue();
        setBackgroundColor(c);
    });

    menu.getChildren().add(colorPicker);

    menu.setMinHeight(25);

    return menu;
  }

  @Override
  public void drawPath(Path p) {
    for (PathElement pe : p.getElements()) {
      if (pe instanceof LineTo) {
        drawLine(pe.isAbsolute(),
            ((LineTo) pe).getX(), ((LineTo) pe).getY());
      }
      else if (pe instanceof MoveTo) {
        moveTo(((MoveTo) pe).getX(), ((MoveTo) pe).getY());
      }
    }
  }

  private void drawLine(boolean absolute, double x, double y) {
    double[] destination;
    if (absolute) {
      destination = new double[]{x, y};
    } else {
      destination = new double[]{myTurtleLocation[0] + x, myTurtleLocation[1] + y};
    }

    myGraphicsContext.strokeLine(
        myTurtleLocation[0] + TRANSLATE_X, myTurtleLocation[1] + TRANSLATE_Y,
        destination[0] + TRANSLATE_X, destination[1] + TRANSLATE_Y
    );

    myTurtleLocation = destination;
  }

  private void moveTo(double x, double y) {
    myTurtleLocation = new double[]{x, y};
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
    myCanvasHolder.setBackground(background);
  }

  @Override
  public void clear() {
    myGraphicsContext.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
  }

  @Override
  public Bounds getBounds() {
    return this.getLayoutBounds();
  }

  private void setGrowPriority(Node node) {
    GridPane.setHgrow(node, Priority.ALWAYS);
    GridPane.setVgrow(node, Priority.ALWAYS);
  }
}
