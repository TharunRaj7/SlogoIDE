package slogo.commands;

import slogo.controller.TurtleController;

public class ID implements ICommand {

    private TurtleController myTurtle;

    public ID (TurtleController turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() { return true; }

    @Override
    public void setArgument(ICommand command) {
        // Should be empty
    }

    @Override
    public void execute() {
        // Should be empty
    }

    @Override
    public double returnVal() {
        return myTurtle.getLastId();
    }

    @Override
    public void clearArgs() {
        // Should be empty
    }
}
