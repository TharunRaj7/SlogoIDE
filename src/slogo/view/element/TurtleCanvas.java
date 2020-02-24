package slogo.view.element;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import slogo.controller.Turtle;

public class TurtleCanvas extends GuiElement implements IVisualize {

  private static final double MIN_CANVAS_WIDTH = 200;
  private static final double MIN_CANVAS_HEIGHT = 200;
  private static final double MAX_CANVAS_WIDTH = 1600;
  private static final double MAX_CANVAS_HEIGHT = 1600;
  private double TRANSLATE_X = MAX_CANVAS_WIDTH / 2.0;
  private double TRANSLATE_Y = MAX_CANVAS_HEIGHT / 2.0;

  private static final double MIN_MENU_HEIGHT = 25;

  private static final Color DEFAULT_PEN_COLOR = Color.WHITE;
  private static final int DEFAULT_PEN_THICKNESS = 1;
  private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;

  private Turtle myTurtle;

  private Canvas myCanvas;
  private Pane myCanvasHolder;
  private GraphicsContext myGraphicsContext;

  private List<Path> myPaths;

  private double[] myTurtleLocation;

  public TurtleCanvas() {
    initializeCanvas();
    initializeDefaults();
    initializeLayoutPane();

    myTurtleLocation = new double[]{0, 0};
  }

  private void initializeCanvas() {
    myCanvasHolder = new Pane();
    myCanvas = new Canvas(MAX_CANVAS_WIDTH, MAX_CANVAS_HEIGHT);
    myCanvasHolder.getChildren().add(myCanvas);
    myCanvasHolder.setMinWidth(MIN_CANVAS_WIDTH);
    myCanvasHolder.setMinHeight(MIN_CANVAS_HEIGHT);

    myGraphicsContext = myCanvas.getGraphicsContext2D();

    myPaths = new ArrayList<>();
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
    setGrowPriorityAlways(myCanvasHolder);

//    this.setBorder(new Border(new BorderStroke(Color.GREEN,
//        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
  }

  private Pane makeMenuBar() {
    HBox menu = new HBox();

    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setOnAction(e -> {
        Color c = colorPicker.getValue();
        setBackgroundColor(c);
    });

    menu.getChildren().add(colorPicker);

    menu.setMinHeight(MIN_MENU_HEIGHT);

    return menu;
  }

  @Override
  public void drawPath(Path p) {
    handlePathDrawing(p);
    myPaths.add(p);
  }

  private void handlePathDrawing(Path p) {
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
    clearCanvas();
    myPaths.clear();
  }

  private void clearCanvas() {
    myGraphicsContext.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
  }

  @Override
  public Bounds getBounds() {
    return this.getLayoutBounds();
  }

  @Override
  public void resize(double width, double height) {
    TRANSLATE_X = width / 2.0;
    TRANSLATE_Y = height / 2.0;

    redrawPaths();

    super.resize(width, height);
  }

  private void redrawPaths() {
    clearCanvas();
    initializeDefaults();
    myTurtleLocation = new double[]{0, 0};
    if (!myPaths.isEmpty()) {
      for (Path p : myPaths) {
        handlePathDrawing(p);
      }
    }
  }

}