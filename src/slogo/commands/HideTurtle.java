package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.Arrays;

public class HideTurtle implements ICommand{

    Turtle myTurtle;
    int myArgs = 1;
    private ArrayList<Double> arguments = new ArrayList<Double>();

    public HideTurtle (Turtle turtle) {
        myTurtle = turtle;
        arguments.add(0.0);
    }

    public HideTurtle (Turtle turtle, double dist) {
        this(turtle);
        arguments.add(0.0);
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
        myTurtle.hide();
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