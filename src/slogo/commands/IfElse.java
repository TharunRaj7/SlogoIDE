package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;

public class IfElse implements ICommand {

    private Turtle myTurtle;
    int myArgs = 3;
    double retVal = 0.0;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public IfElse (Turtle turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() {
        return checkArgs() == myArgs;
    }

    @Override
    public void setArgument(ICommand command) {
        arguments.add(command);
    }

    @Override
    public void execute() {
        arguments.get(0).execute();
        if(arguments.get(0).returnVal() != 0.0) {
            arguments.get(1).execute();
            retVal = arguments.get(1).returnVal();
        } else {
            arguments.get(2).execute();
            retVal = arguments.get(2).returnVal();
        }
    }

    @Override
    public double returnVal() {
        return retVal;
    }

    @Override
    public void clearArgs() {
        arguments.clear();
    }

    private int checkArgs() { return arguments.size(); }
}
