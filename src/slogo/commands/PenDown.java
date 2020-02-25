package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.Arrays;

public class PenDown implements ICommand{

    Turtle myTurtle;
    int myArgs = 1;
    private ArrayList<Double> arguments = new ArrayList<Double>();

    public PenDown (Turtle turtle) {
        myTurtle = turtle;
        arguments.add(1.0);
    }

    public PenDown (Turtle turtle, double dist) {
        this(turtle);
        arguments.add(1.0);
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs () {
        return check_arg() == myArgs;
    }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param arg
     */
    public void setArgument (double arg) {

    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        // TODO: Call on turtle to move it forward the given distance
        myTurtle.penDown();
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return arguments.get(0);
    }



    public int check_arg(){
        return arguments.size();
    }
}
