package slogo.commands;

import slogo.controller.Turtle;

public class Forward implements ICommand {

    Turtle myTurtle;
    int myArgs = 1;
    double myDist;

    public Forward (Turtle turtle) {
        myTurtle = turtle;
    }

    public Forward (Turtle turtle, double dist) {
        this(turtle);
        myDist = dist;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @param numArgs
     * @return
     */
    public boolean enoughArgs (int numArgs) {
        return numArgs == myArgs;
    }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param arg
     */
    public void setArgument (double arg) {
        myDist = arg;
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        // TODO: Call on turtle to move it forward the given distance
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return myDist;
    }
}
