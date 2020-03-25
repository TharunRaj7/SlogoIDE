package slogo.view.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.geometry.Bounds;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import org.w3c.dom.Element;
import slogo.controller.Turtle;
import slogo.controller.TurtleController;
import slogo.utility.Location;
import slogo.view.utility.ButtonFactory;
import slogo.view.utility.XMLBuilder;

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
  private TurtleController turtleController;

  private Map<Turtle, List<Path>> myPaths;
  private Map<Path, List<Object>> myPathsProperties;

  /**
   * Initializes a TurtleCanvas.
   * @param turtleController TurtleController to use
   * @param resources language ResourceBundle
   */
  public TurtleCanvas(TurtleController turtleController, ResourceBundle resources) {
    this.turtleController = turtleController;
    initializeCanvas();
    initializeDefaults();
    initializeLayoutPane(resources);

    turtleController.giveTurtleCanvas(this);
  }

  private void initializeCanvas() {
    myCanvasHolder = new Pane();
    myCanvas = new Canvas(MAX_CANVAS_WIDTH, MAX_CANVAS_HEIGHT);
    myCanvasHolder.getChildren().add(myCanvas);
    myCanvasHolder.setMinWidth(MIN_CANVAS_WIDTH);
    myCanvasHolder.setMinHeight(MIN_CANVAS_HEIGHT);

    myGraphicsContext = myCanvas.getGraphicsContext2D();

    myPaths = new HashMap<>();
    myPathsProperties = new HashMap<>();
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

    Button clearButton = ButtonFactory.button(resources.getString("clear"), e -> turtleController.clear());
    menu.getChildren().add(clearButton);

    menu.setMinHeight(MENU_HEIGHT);
    menu.setMaxHeight(MENU_HEIGHT);

    menu.setSpacing(GAP);

    return menu;
  }

  /**
   * Adds all turtle images to the canvas.
   */
  public void addAllTurtleImages (){
    myCanvasHolder.getChildren().addAll(turtleController.getAllTurtleImages());
  }

  private void removeAllTurtleImages(){
    myCanvasHolder.getChildren().removeAll(turtleController.getAllTurtleImages());
  }

  /**
   * Adds active turtle images to the canvas, from the TurtleController.
   */
  public void addActiveTurtleImages() {
    for (ImageView image : turtleController.getActiveTurtleImages()){
      if (!myCanvasHolder.getChildren().contains(image)){
        myCanvasHolder.getChildren().addAll(image);
      }
    }
  }

  /**
   * Draws a new path with the provided Turtle.
   * @param turtle the Turtle with which to draw
   * @param p the Path to draw
   */
  @Override
  public void drawPath(Turtle turtle, Path p) {
    myPaths.putIfAbsent(turtle, new ArrayList<>());
    myPaths.get(turtle).add(p);
    myPathsProperties.putIfAbsent(p, List.of(turtle.getPenColor(), (int) turtle.getPenSize()));
    handlePathDrawing(turtle, p);
  }

  private void handlePathDrawing(Turtle turtle, Path p) {
    setPenColor((Color) myPathsProperties.get(p).get(0));
    setPenThickness((Integer) myPathsProperties.get(p).get(1));

    for (PathElement pe : p.getElements()) {
      if (pe instanceof LineTo) {
        drawLine(turtle, pe.isAbsolute(),
            ((LineTo) pe).getX(), ((LineTo) pe).getY());
      }
      else if (pe instanceof MoveTo) {
        moveTo(turtle, pe.isAbsolute(),
                ((MoveTo) pe).getX(), ((MoveTo) pe).getY());
      }
    }
  }

  private void drawLine(Turtle turtle, boolean absolute, double x, double y) {
    Location source = turtle.getLocation();
    Location destination = new Location(x, y);
    if (!absolute) {
      destination = destination.add(turtle.getLocation());
    }

    myGraphicsContext.strokeLine(
        source.getX()      + TRANSLATE_X, source.getY()      + TRANSLATE_Y,
        destination.getX() + TRANSLATE_X, destination.getY() + TRANSLATE_Y
    );

    turtle.setLocation(destination);
  }

  private void moveTo(Turtle turtle, boolean absolute, double x, double y) {
    Location source = turtle.getLocation();
    if (!absolute){
      turtle.setLocation(new Location(source.getX() + x, source.getY() + y));
    }else{
      turtle.setLocation(new Location(x,y));
    }
  }

  /**
   * Sets the pen color.
   * @param c the new Color
   */
  @Override
  public void setPenColor(Color c) {
    myGraphicsContext.setStroke(c);
  }

  /**
   * Sets the pen thickness.
   * @param thickness the new thickness, in pixels
   */
  @Override
  public void setPenThickness(int thickness) {
    myGraphicsContext.setLineWidth(thickness);
  }

  /**
   * Sets the canvas background color.
   * @param c the new background Color
   */
  @Override
  public void setBackgroundColor(Color c) {
    BackgroundFill backgroundFill = new BackgroundFill(c, null, null);
    Background background = new Background(backgroundFill);
    myCanvasHolder.setBackground(background);
  }

  /**
   * Clears the canvas and removes recorded paths.
   */
  @Override
  public void clear() {
    removeAllTurtleImages();
    clearCanvas();
    myPaths.clear();
    myPathsProperties.clear();
  }

  private void clearCanvas() {
    myGraphicsContext.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
  }

  /**
   * Overrides bounds checking to use layout bounds.
   * @return this.getLayoutBounds()
   */
  @Override
  public Bounds getBounds() {
    return this.getLayoutBounds();
  }

  /**
   * Updates coordinate translation and redraws paths on each resize.
   * @param width the new width
   * @param height the new height
   */
  @Override
  public void resize(double width, double height) {
    TRANSLATE_X = width / 2.0 - PADDING;
    TRANSLATE_Y = (height - MENU_HEIGHT - GAP) / 2.0 - PADDING;

    redrawPaths();

    super.resize(width, height);
  }

  private void redrawPaths() {
    clearCanvas();
    turtleController.resetTurtleLocation();

    if (!myPaths.isEmpty()) {
      for (Turtle turtle : myPaths.keySet()) {
        for (Path p : myPaths.get(turtle)){
          handlePathDrawing(turtle, p);
        }
      }
    }
  }

  /**
   * Gets the canvas' X-coordinate translation.
   * @return TRANSLATE_X
   */
  public double getTRANSLATE_X() {
    return TRANSLATE_X;
  }

  /**
   * Gets the canvas' Y-coordinate translation.
   * @return TRANSLATE_Y
   */
  public double getTRANSLATE_Y() {
    return TRANSLATE_Y;
  }

  /**
   * Updates the ResourceBundle being used.
   * @param resources the new ResourceBundle
   */
  @Override
  public void updateResources(ResourceBundle resources) {
    initializeLayoutPane(resources);
  }

  /**
   * Creates an XML element representing this GuiElement.
   * @return new XML Element node
   */
  @Override
  public Element toXMLElement() {
    XMLBuilder xmlBuilder = XMLBuilder.newInstance();
    Element root = xmlBuilder.createElement(this.getClass().getSimpleName());

    String background = myCanvasHolder.getBackground().getFills().get(0).getFill().toString();
    root.setAttributeNode(xmlBuilder.createAttribute("backgroundcolor", background));

    return root;
  }

  /**
   * Sets the contents of this GuiElement from an XML element.
   * @param element XML Element node
   */
  @Override
  public void setContentsFromXMLElement(Element element) {
    setBackgroundColor(Color.valueOf(element.getAttributes().item(0).getNodeValue()));
  }
}
