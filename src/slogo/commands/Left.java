package slogo.commands;

import slogo.controller.TurtleController;

import java.util.ArrayList;

public class Left implements ICommand{

    private TurtleController myTurtle;
    private int myArgs = 1;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

    public Left (TurtleController turtle) {
        myTurtle = turtle;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs () {
        return arguments.size() == myArgs;
    }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param arg
     */
    public void setArgument (ICommand arg) {
        add_arg(arg);
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        arguments.get(0).execute();
        myTurtle.rotate(-arguments.get(0).returnVal());
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () { return arguments.get(0).returnVal(); }

    @Override
    public void clearArgs() {
        arguments.clear();
    }

    public void add_arg(ICommand arg){
        arguments.add(arg);
    }

}

