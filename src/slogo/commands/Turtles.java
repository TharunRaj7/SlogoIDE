package slogo.commands;

import slogo.controller.TurtleController;

public class Turtles implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 0;

    public Turtles (TurtleController turtle) {
        myTurtle = turtle;
    }

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
        // Should be empty
    }

    @Override
    public double returnVal() {
        return myTurtle.numberOfTurtlesCreated();
    }

    @Override
    public void clearArgs() {
        // Should be empty
    }
}