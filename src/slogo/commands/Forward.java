package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;
import java.util.Arrays;

public class Forward implements ICommand{

    private TurtleController myTurtle;
    private int myArgs = 1;
    private double myDist;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

    public Forward (TurtleController turtle) {
        myTurtle = turtle;
    }

    public Forward (TurtleController turtle, double dist) {
        this(turtle);
        myDist = dist;
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
        /*
        myDist = arg.returnVal();
        add_arg(arg);

         */
        arguments.add(arg);
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        // TODO: Call on turtle to move it forward the given distance
        arguments.get(0).execute();
        System.out.println("Executed Forward");
        myTurtle.moveRelative(arguments.get(0).returnVal());
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return arguments.get(0).returnVal();
    }

    @Override
    public void clearArgs() { arguments.clear(); }

}
