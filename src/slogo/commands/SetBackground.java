package slogo.commands;

import slogo.controller.TurtleController;

import java.util.ArrayList;

public class SetBackground implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 1;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public SetBackground (TurtleController turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() { return arguments.size() == myArgs; }

    @Override
    public void setArgument(ICommand command) {
        arguments.add(command);
    }

    @Override
    public void execute() {
        
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