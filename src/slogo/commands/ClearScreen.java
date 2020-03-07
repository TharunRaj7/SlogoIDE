package slogo.commands;

import slogo.controller.TurtleController;

public class ClearScreen implements ICommand {

    private TurtleController myTurtle;
    private double distMoved;

    public ClearScreen (TurtleController turtle) { myTurtle = turtle; }

    @Override
    public boolean enoughArgs() {
        return true;
    }

    @Override
    public void setArgument(ICommand command) {
        // Should be empty
    }

    @Override
    public void execute() {
        distMoved = Math.sqrt(Math.pow(myTurtle.getLocation().getX(), 2.0) + Math.pow(myTurtle.getLocation().getY(), 2.0));
        myTurtle.clear();
    }

    @Override
    public double returnVal() {
        return distMoved;
    }

    @Override
    public void clearArgs () {
        // Shouldn't do anything
    }
}
