package slogo.commands;

import slogo.controller.TurtleController;

public class Tell extends BlockCommand implements ICommand{

    TurtleController myShell;
    int myArgs = 1;
    double val;

    @Override
    public boolean enoughArgs() {
        return false;
    }

    @Override
    public void setArgument(ICommand command) {

    }

    @Override
    public void execute() {

    }

    @Override
    public double returnVal() {
        return 0;
    }

    @Override
    public void clearArgs() {

    }
}
