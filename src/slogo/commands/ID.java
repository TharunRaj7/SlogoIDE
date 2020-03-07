package slogo.commands;

import slogo.controller.TurtleController;

public class ID implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 0;

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
        return 0;
    }

    @Override
    public void clearArgs() {
        // Should be empty
    }
}
