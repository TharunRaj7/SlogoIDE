package slogo.view.element;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
import slogo.utility.Location;
import slogo.view.utility.ButtonFactory;

public class TurtleCanvas extends GuiElement implements IVisualize {

  private static final double MIN_CANVAS_WIDTH = 200;
  private static final double MIN_CANVAS_HEIGHT = 200;
  private static final double MAX_CANVAS_WIDTH = 1600;
  private static final double MAX_CANVAS_HEIGHT = 1600;

  private double TRANSLATE_X = MAX_CANVAS_WIDTH / 2.0;
  private double TRANSLATE_Y = MAX_CANVAS_HEIGHT / 2.0;

  private static final double MENU_HEIGHT = 25;

  private static final Color DEFAULT_PEN_COLOR = Color.WHITE;
  private static final int DEFAULT_PEN_THICKNESS = 1;
  private static final Color DEFAULT_BACKGROUND_COLOR = Color.BLACK;

  private Canvas myCanvas;
  private Pane myCanvasHolder;
  private GraphicsContext myGraphicsContext;
  private Turtle myTurtle;

  private List<Path> myPaths;

  public TurtleCanvas(Turtle turtle, ResourceBundle resources) {
    myTurtle = turtle;
    initializeCanvas();
    initializeDefaults();
    initializeLayoutPane(resources);
  }

  private void initializeCanvas() {
    myCanvasHolder = new Pane();
    myCanvas = new Canvas(MAX_CANVAS_WIDTH, MAX_CANVAS_HEIGHT);
    myCanvasHolder.getChildren().addAll(myCanvas, myTurtle.getImage());
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

  private void initializeLayoutPane(ResourceBundle resources) {
    Pane menuBar = makeMenuBar(resources);
    this.getChildren().clear();
    this.add(menuBar, 0, 0);
    this.add(myCanvasHolder, 0, 1);
    setGrowPriorityAlways(myCanvasHolder);
  }

  private Pane makeMenuBar(ResourceBundle resources) {
    HBox menu = new HBox();

    ColorPicker colorPicker = new ColorPicker();
    colorPicker.setOnAction(e -> {
        Color c = colorPicker.getValue();
        setBackgroundColor(c);
    });
    colorPicker.setValue(DEFAULT_BACKGROUND_COLOR);

    menu.getChildren().add(colorPicker);

    Button clearButton = ButtonFactory.button(resources.getString("clear"), e -> {
      clear();
      myTurtle.setLocation(Location.ORIGIN);
      myTurtle.setHeading(0);
    });
    menu.getChildren().add(clearButton);

    menu.setMinHeight(MENU_HEIGHT);
    menu.setMaxHeight(MENU_HEIGHT);

    menu.setSpacing(GAP);

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
    Location source = myTurtle.getLocation();
    Location destination = new Location(x, y);
    if (!absolute) {
      destination = destination.add(myTurtle.getLocation());
    }

    myGraphicsContext.strokeLine(
        source.getX()      + TRANSLATE_X, source.getY()      + TRANSLATE_Y,
        destination.getX() + TRANSLATE_X, destination.getY() + TRANSLATE_Y
    );

    myTurtle.setLocation(destination);
  }

  private void moveTo(double x, double y) {
    myTurtle.setLocation(new Location(x, y));
  }

  @Override
  public void setPenColor(Color c) {
    myGraphicsContext.setStroke(c);
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
    TRANSLATE_X = width / 2.0 - PADDING;
    TRANSLATE_Y = (height - MENU_HEIGHT - GAP) / 2.0 - PADDING;

    redrawPaths();

    super.resize(width, height);
  }

  private void redrawPaths() {
    clearCanvas();
    myTurtle.setLocation(Location.ORIGIN);
    if (!myPaths.isEmpty()) {
      for (Path p : myPaths) {
        handlePathDrawing(p);
      }
    }
  }

  public double getTRANSLATE_X() {
    return TRANSLATE_X;
  }

  public double getTRANSLATE_Y() {
    return TRANSLATE_Y;
  }

  @Override
  public void updateResources(ResourceBundle resources) {
    initializeLayoutPane(resources);
  }

}
