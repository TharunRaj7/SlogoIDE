package slogo.commands;

import slogo.controller.TurtleController;

import java.util.ArrayList;

public abstract class Command {

    protected TurtleController myTurtle;
    protected int myArgs;
    protected double returnVal;
    protected ArrayList<Command> arguments = new ArrayList<>();

    public Command (TurtleController turtle) { myTurtle = turtle; }

    public boolean enoughArgs () { return arguments.size() == myArgs; }

    public void setArgument (Command arg) { arguments.add(arg); }

    public void execute () {
        for (Command command : arguments) {
            command.execute();
        }
    }

    public double returnVal () { return returnVal; }

}
