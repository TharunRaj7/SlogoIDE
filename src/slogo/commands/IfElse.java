package slogo.commands;

import slogo.controller.TurtleController;

import java.util.ArrayList;

public class IfElse extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 3;
    private double retVal = 0.0;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public IfElse (TurtleController turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() {
        return arguments.size() == myArgs;
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

}
