package slogo.controller;

import java.io.FileInputStream;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import slogo.utility.Location;
import slogo.view.ExceptionFeedback;
import slogo.view.ExceptionFeedback.ExceptionType;
import slogo.view.element.TurtleCanvas;
import slogo.view.element.VariableExplorer;


import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class TurtleController {

    public static final String DEFAULT_TURTLE_IMAGE = "data/resources/images/Turtle.png";
    private List<Turtle> turtles;
    private List<Turtle> activeTurtles;
    private TurtleCanvas turtleCanvas;
    private List<Turtle> turtlesAskHolder;
    private VariableExplorer variableExplorer;
    private Turtle currentTurtle;
    private ResourceBundle resource;


    public TurtleController (){
        turtles = new ArrayList<>();
        activeTurtles = new ArrayList<>();
    }


    public void giveTurtleCanvas (TurtleCanvas tc){
        this.turtleCanvas = tc;
        // make one turtle initially
        Turtle turtle = new Turtle(1, new Location(0,0), 0.0, DEFAULT_TURTLE_IMAGE);
        turtle.giveTurtleCanvas(this.turtleCanvas);
        turtles.add(turtle);
        activeTurtles.add(turtle);
        currentTurtle = turtle;
        turtleCanvas.addAllTurtleImages();
    }

    public void tellTurtles (List<Integer> id){
        List<Turtle> turtlesActive = new ArrayList<>();
        for (int i : id){
            turtlesActive.add(findOrCreateTurtle(i));
        }
        activeTurtles.clear();
        activeTurtles.addAll(turtlesActive);
        currentTurtle = turtlesActive.get(turtlesActive.size() - 1);
        showActiveTurtlesOnCanvas();
    }

    private Turtle findOrCreateTurtle(int id) {
        for (Turtle item : turtles){
            if (item.getId() == id){
                item.show();
                return item;
            }
        }
        Turtle newTurtle = new Turtle(id, new Location(0,0), 0.0,
            DEFAULT_TURTLE_IMAGE);
        newTurtle.giveTurtleCanvas(this.turtleCanvas);
        turtles.add(newTurtle);
        return newTurtle;
    }

    private void showActiveTurtlesOnCanvas() {
        for (Turtle turtle : activeTurtles){
            DropShadow borderGlow = new DropShadow();
            borderGlow.setColor(Color.GREEN);
            borderGlow.setSpread(0.8);
//            borderGlow.setOffsetX(0f);
//            borderGlow.setOffsetY(0f);
            turtle.getImage().setEffect(borderGlow);
        }
        //unhighlight inactive turtles
        turtleCanvas.addActiveTurtleImages();
        for (Turtle turtle : turtles){
            if (!activeTurtles.contains(turtle)){
                turtle.getImage().setEffect(null);
                turtle.setLocation(turtle.getLocation());
            }
        }
    }

    /**
     * Method to start the ask command
     * @param id
     */
    public void askTurtles (List<Integer>id){
        this.turtlesAskHolder = List.copyOf(activeTurtles);
        tellTurtles(id);
    }

    /**
     * Method to end the ask command
     */
    public void restore (){
        List<Integer> id = new ArrayList<>();
        for (Turtle turtle : turtlesAskHolder){
            id.add(turtle.getId());
        }
        tellTurtles(id);
    }


    /**
     * Method to move all the active turtles relatively
     * @param distance
     */
    public void moveRelative (double distance){
        for (Turtle turtle : activeTurtles){
            turtle.moveRelative(distance);
            currentTurtle = turtle;
        }
    }


    /**
     * Method to move all the active turtles absolutely
     * @param l
     */
    public void moveTo (Location l){
        for (Turtle turtle : activeTurtles){
            turtle.moveTo(l);
            currentTurtle = turtle;
        }
    }

    /**
     * Rotates all the active turtles
     * @param angle
     */
    public void rotate (double angle){
        for (Turtle turtle : activeTurtles){
            turtle.rotate(angle);
            currentTurtle = turtle;
        }
    }

    /**
     * sets heading of all the active turtles to a specific coordinate
     * @param x
     * @param y
     */
    public void towards (double x, double y){
        for (Turtle turtle : activeTurtles){
            turtle.towards(x, y);
            currentTurtle = turtle;
        }
    }

    /**
     * sets the heading of all the active turtles
     * @param angle
     */
    public void setHeading (double angle){
        for (Turtle turtle : activeTurtles){
            turtle.setHeading(angle);
            currentTurtle = turtle;
        }
    }

    /**
     * Retracts the pen for all the active turtles
     */
    public void penUp(){
        for (Turtle turtle : activeTurtles){
            turtle.penUp();
            currentTurtle = turtle;
        }
    }

    /**
     * engages the pen for all the active turtles
     */
    public void penDown(){
        for (Turtle turtle : activeTurtles){
            turtle.penDown();
            currentTurtle = turtle;
        }
    }

    /**
     * Show all active turtles on screen
     */
    public void show(){
        for (Turtle turtle : activeTurtles){
            turtle.show();
            currentTurtle = turtle;
        }
    }

    /**
     * Hides all the active turtles
     */
    public void hide(){
        for (Turtle turtle : activeTurtles){
            turtle.hide();
            currentTurtle = turtle;
        }
    }

    /**
     * clears the screen and resets all the turtles
     */
    public void clear (){
        turtleCanvas.clear();
        turtles.clear();
        activeTurtles.clear();
        giveTurtleCanvas(this.turtleCanvas);
    }

    /**
     * Getter for the location of the most recent turtle
     * @return
     */
    public Location getLocation () {
        return turtles.get(turtles.size() - 1).getLocation();
    }
    /**
     * Getter for the heading of the most recent turtle
     * @return
     */
    public double getHeading() {
        return turtles.get(turtles.size() - 1).getHeading();
    }

    /**
     * Getter for the pen status of the most recent turtle
     * @return
     */
    public boolean getPenDown() {
        return turtles.get(turtles.size() - 1).getPenDown();
    }


    /**
     * Getter for the showing state of the most recent turtle
     * @return
     */
    public boolean getShowing() {
        return turtles.get(turtles.size() - 1).getShowing();
    }


    /**
     * Getter for the active turtle images
     * @return
     */
    public List<ImageView> getActiveTurtleImages(){
        List<ImageView> ret = new ArrayList<>();
        for (Turtle turtle : activeTurtles){
            ret.add(turtle.getImage());
        }
        return ret;
    }

    /**
     * Getter for all the turtle images
     * @return
     */
    public List<ImageView> getAllTurtleImages(){
        List<ImageView> ret = new ArrayList<>();
        for (Turtle turtle : turtles){
           ret.add(turtle.getImage());
        }
        return ret;
    }

    /**
     * Getter for the location of a specific turtle
     * @param id
     * @return
     */
    public Location getLocation(int id){
        for (Turtle turtle : turtles){
            if (turtle.getId() == id){
                return turtle.getLocation();
            }
        }
        return Location.ORIGIN;
    }

    /**
     * resets the location and heading of all the turtles back to the initial state
     */
    public void resetTurtles() {
        for (Turtle turtle : turtles){
            turtle.setLocation(Location.ORIGIN);
            turtle.setHeading(0.0);
        }
    }

    /**
     * Resets the location of all turtles back to the origin
     */
    public void resetTurtleLocation() {
        for (Turtle turtle : turtles){
            turtle.setLocation(Location.ORIGIN);
        }
    }


    /**
     * Calls the relevant method to update the variable table data in the view
     * @param map
     */
    public void addTableData(Map<String, Double> map) {
        variableExplorer.addTableData(map);
    }

    /**
     * Gives the varible Explorer instance to the turtle
     * @param v
     */
    public void giveVariableExplorer(VariableExplorer v) {
        this.variableExplorer = v;
    }

    /**
     * returns the id of the turtle currently being used to draw commands
     * @return
     */
    public int getLastId () {
        return currentTurtle.getId();
    }

    /**
     * returns the total number of turtles already created
     * @return
     */
    public int numberOfTurtlesCreated(){
        return turtles.size();
    }

    private ResourceBundle getResourceBundleFromPath(String path) {
        try {
            File file = new File("data/resources");
            URL[] urls = {file.toURI().toURL()};
            ClassLoader loader = new URLClassLoader(urls);
            return ResourceBundle.getBundle(path, Locale.getDefault(), loader);
        } catch (Exception e) {
            ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.RESOURCE_EXCEPTION,
                    "Failed to load resources for Resource Panel. Check your directory structure" +
                            " and try loading resources again.");
        }
        return null;
    }

    /**
     * Setter method for the background.
     * @param index
     */
    public void setBackground (int index){
        resource = getResourceBundleFromPath("Colors");
        String colorString = Collections.list(resource.getKeys()).get(index);
        Color color = Color.valueOf(colorString);
        turtleCanvas.setBackgroundColor(color);
    }

    /**
     * Setter method for the pen color
     * @param index
     */
    public void setPenColor (int index){
        resource = getResourceBundleFromPath("Colors");
        String colorString = resource.getString(Collections.list(resource.getKeys()).get(index % resource.keySet().size()));
        Color color = Color.valueOf(colorString);
        for (Turtle turtle : activeTurtles){
            turtle.setPenColor(color);
            turtle.setColorIndex(index);
            currentTurtle = turtle;
        }
    }

    /**
     * Setter method for the pen size
     * @param penSize
     */
    public void setPenSize (int penSize){
        for (Turtle turtle : activeTurtles){
            turtle.setPenSize(penSize);
            currentTurtle = turtle;
        }
    }

    /**
     * Setter method for the image of the turtle
     * @param index
     */
    public void setShape (int index){
        try {
            resource = getResourceBundleFromPath("images");
            String imageName = resource.getString(
                Collections.list(resource.getKeys()).get(index % resource.keySet().size()));
            System.out.println(imageName);
            Image image = new Image(new FileInputStream(imageName));
            for (Turtle turtle : activeTurtles) {
                turtle.getImage().setImage(image);
                turtle.setShapeIndex(index);
                currentTurtle = turtle;
            }
        } catch (Exception e) {
            ExceptionFeedback.throwException(ExceptionType.RESOURCE_EXCEPTION,
                "Could not set Turtle image.");
        }
    }

    public int getPenColorIndex (){
        return currentTurtle.getColorIndex();
    }

    public int getShapeIndex () {
        return currentTurtle.getShapeIndex();
    }

}
