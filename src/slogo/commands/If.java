package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;

public class If extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 2;
    private double retVal = 0.0;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public If (TurtleController turtle) {
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
            retVal = 1.0;
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
