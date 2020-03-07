package slogo.commands;

import slogo.controller.TurtleController;

import java.util.ArrayList;
import java.util.List;

public class Ask extends BlockCommand implements ICommand{

    TurtleController myShell;
    int myArgs = 2;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private List<Integer> turtles = new ArrayList<>();

    public Ask (TurtleController turtleController){
        myShell = turtleController;
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

    }

    @Override
    public double returnVal() {
        return 0.0;

    }

    @Override
    public void clearArgs() {
        arguments.clear();

    }
}
