package slogo.commands;

import slogo.controller.Turtle;
import slogo.utility.Location;

public class ClearScreen implements ICommand{

    Turtle myTurtle;
    double distMoved;
    //private ArrayList<ICommand> arguments = new ArrayList<>();

    public ClearScreen (Turtle turtle) { myTurtle = turtle; }

    @Override
    public boolean enoughArgs() { return true; }

    @Override
    public void setArgument(ICommand command) {
        // Should be empty
    }

    @Override
    public void execute() {
        //arguments.get(0).execute();
        distMoved = Math.sqrt(Math.pow(myTurtle.getLocation().getX(), 2.0) + Math.pow(myTurtle.getLocation().getY(), 2.0));
        Location origin = new Location(0, 0);
        myTurtle.moveTo(origin);
        myTurtle.setHeading(0.0);
        myTurtle.clear();
    }

    @Override
    public double returnVal() { return distMoved; }

    @Override
    public void clearArgs() {
        // Does nothing
    }
}
