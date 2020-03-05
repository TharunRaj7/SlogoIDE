package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;

public class ToManager extends To implements ICommand {

    private Turtle myTurtle;
    int myArgs;

    private Variables variable;

    public ToManager (Turtle turtle) {
        super(turtle);
        myArgs = 0;
    }

    @Override
    public boolean enoughArgs() {
        return checkArgs() == myArgs;
    }

    @Override
    public void setArgument(ICommand command) {

    }

    @Override
    public void execute() {

    }

    @Override
    public double returnVal() {
        return 0.0;

    }

    @Override
    public void clearArgs() {

    }

    private int checkArgs() {
        return 0;

    }


}
