package slogo.controller;

import javafx.scene.image.ImageView;
import slogo.utility.Location;
import slogo.view.element.TurtleCanvas;


import java.util.ArrayList;
import java.util.List;

public class TurtleController {
    //TODO: implement in tellTurtle to hide turtles when inactive
    private List<Turtle> turtles;
    private List<Turtle> activeTurtles;
    private TurtleCanvas turtleCanvas;

    public TurtleController (){
        turtles = new ArrayList<>();
        activeTurtles = new ArrayList<>();
    }

    public void giveTurtleCanvas (TurtleCanvas tc){
        this.turtleCanvas = tc;
        // make one turtle initially
        Turtle turtle = new Turtle(1, new Location(0,0), 0.0, turtleCanvas, "slogo/view/resources/Turtle.gif");
        turtles.add(turtle);
        activeTurtles.add(turtle);
    }

    public void tellTurtles (List<Integer> id){
        List<Turtle> turtlesActive = new ArrayList<>();
        for (int i : id){
            turtlesActive.add(findOrCreateTurtle(i));
        }
        activeTurtles = turtlesActive;
    }

    private Turtle findOrCreateTurtle(int id) {
        for (Turtle item : turtles){
            if (item.getId() == id){
                return item;
            }
        }
        Turtle newTurtle = new Turtle(id, new Location(0,0), 0.0, turtleCanvas, "slogo/view/resources/Turtle.gif");
        turtles.add(newTurtle);
        return newTurtle;
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
        turtles.clear();
        activeTurtles.clear();
        giveTurtleCanvas(this.turtleCanvas);
        turtleCanvas.clear();
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
