package slogo.controller;

import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import slogo.utility.Location;
import slogo.view.element.TurtleCanvas;


import java.util.ArrayList;
import java.util.List;

public class TurtleController {
    //TODO: implement in tellTurtle to hide turtles when inactive
    private List<Turtle> turtles;
    private List<Turtle> activeTurtles;
    private TurtleCanvas turtleCanvas;

    //TODO: getters give last active turtle
    public TurtleController (){
        turtles = new ArrayList<>();
        activeTurtles = new ArrayList<>();
    }

    public void giveTurtleCanvas (TurtleCanvas tc){
        this.turtleCanvas = tc;
        // make one turtle initially
        Turtle turtle = new Turtle(1, new Location(0,0), 0.0, "slogo/view/resources/Turtle.gif");
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
        Turtle newTurtle = new Turtle(id, new Location(0,0), 0.0, "slogo/view/resources/Turtle.gif");
        newTurtle.giveTurtleCanvas(this.turtleCanvas);
        turtles.add(newTurtle);
        return newTurtle;
    }

    private void showActiveTurtlesOnCanvas() {
        for (Turtle turtle : activeTurtles){
            DropShadow borderGlow = new DropShadow();
            borderGlow.setColor(Color.RED);
            borderGlow.setOffsetX(0f);
            borderGlow.setOffsetY(0f);
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


    public void moveRelative (double distance){
        for (Turtle turtle : activeTurtles){
            turtle.moveRelative(distance);
        }
    }


    public void moveTo (Location l){
        for (Turtle turtle : activeTurtles){
            turtle.moveTo(l);
        }
    }

    public void rotate (double angle){
        for (Turtle turtle : activeTurtles){
            turtle.rotate(angle);
        }
    }

    public void towards (double x, double y){
        for (Turtle turtle : activeTurtles){
            turtle.towards(x, y);
        }
    }

    public void setHeading (double angle){
        for (Turtle turtle : activeTurtles){
            turtle.setHeading(angle);
        }
    }

    public void penUp(){
        for (Turtle turtle : activeTurtles){
            turtle.penUp();
        }
    }

    public void penDown(){
        for (Turtle turtle : activeTurtles){
            turtle.penDown();
        }
    }

    public void show(){
        for (Turtle turtle : activeTurtles){
            turtle.show();
        }
    }

    public void hide(){
        for (Turtle turtle : activeTurtles){
            turtle.hide();
        }
    }

    public void clear (){
        turtleCanvas.clear();
        turtles.clear();
        activeTurtles.clear();
        giveTurtleCanvas(this.turtleCanvas);
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
        return new Location(0,0);
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
}
