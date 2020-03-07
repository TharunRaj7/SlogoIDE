package slogo.controller;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import slogo.utility.Location;
import slogo.view.element.TurtleCanvas;
import slogo.view.element.VariableExplorer;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TurtleController {

    public static final String DEFAULT_TURTLE_IMAGE = "data/resources/images/Turtle.png";
    private List<Turtle> turtles;
    private List<Turtle> activeTurtles;
    private TurtleCanvas turtleCanvas;
    private List<Turtle> turtlesAskHolder;
    private VariableExplorer variableExplorer;
    private Turtle currentTurtle;

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
        turtleCanvas.addAllTurtleImages();
    }

    public void tellTurtles (List<Integer> id){
        List<Turtle> turtlesActive = new ArrayList<>();
        for (int i : id){
            turtlesActive.add(findOrCreateTurtle(i));
        }
        activeTurtles.clear();
        activeTurtles.addAll(turtlesActive);
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

    public void askTurtles (List<Integer>id){
        this.turtlesAskHolder = List.copyOf(activeTurtles);
        tellTurtles(id);
    }

    public void restore (){
        List<Integer> id = new ArrayList<>();
        for (Turtle turtle : turtlesAskHolder){
            id.add(turtle.getId());
        }
        tellTurtles(id);
    }


    public void moveRelative (double distance){
        for (Turtle turtle : activeTurtles){
            turtle.moveRelative(distance);
            currentTurtle = turtle;
        }
    }


    public void moveTo (Location l){
        for (Turtle turtle : activeTurtles){
            turtle.moveTo(l);
            currentTurtle = turtle;
        }
    }

    public void rotate (double angle){
        for (Turtle turtle : activeTurtles){
            turtle.rotate(angle);
            currentTurtle = turtle;
        }
    }

    public void towards (double x, double y){
        for (Turtle turtle : activeTurtles){
            turtle.towards(x, y);
            currentTurtle = turtle;
        }
    }

    public void setHeading (double angle){
        for (Turtle turtle : activeTurtles){
            turtle.setHeading(angle);
            currentTurtle = turtle;
        }
    }

    public void penUp(){
        for (Turtle turtle : activeTurtles){
            turtle.penUp();
            currentTurtle = turtle;
        }
    }

    public void penDown(){
        for (Turtle turtle : activeTurtles){
            turtle.penDown();
            currentTurtle = turtle;
        }
    }

    public void show(){
        for (Turtle turtle : activeTurtles){
            turtle.show();
            currentTurtle = turtle;
        }
    }

    public void hide(){
        for (Turtle turtle : activeTurtles){
            turtle.hide();
            currentTurtle = turtle;
        }
    }

    public void clear (){
        turtleCanvas.clear();
        turtles.clear();
        activeTurtles.clear();
        giveTurtleCanvas(this.turtleCanvas);
    }

    public Location getLocation () {
        return turtles.get(turtles.size() - 1).getLocation();
    }
    public double getHeading() {
        return turtles.get(turtles.size() - 1).getHeading();
    }


    public boolean getPenDown() {
        return turtles.get(turtles.size() - 1).getPenDown();
    }


    public boolean getShowing() {
        return turtles.get(turtles.size() - 1).getShowing();
    }


    //get all the turtle images
    public List<ImageView> getActiveTurtleImages(){
        List<ImageView> ret = new ArrayList<>();
        for (Turtle turtle : activeTurtles){
            ret.add(turtle.getImage());
        }
        return ret;
    }

    public List<ImageView> getAllTurtleImages(){
        List<ImageView> ret = new ArrayList<>();
        for (Turtle turtle : turtles){
           ret.add(turtle.getImage());
        }
        return ret;
    }
    // get the location for a specific turtle
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

    public void addTableData(Map<String, Double> map) {
        variableExplorer.addTableData(map);
    }

    public void giveVariableExplorer(VariableExplorer v) {
        this.variableExplorer = v;
    }
    public int getLastId () {
        return currentTurtle.getId();
    }
    public int numberOfTurtlesCreated(){
        return turtles.size();
    }
}
