package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.Arrays;

public class Back implements ICommand{

    Turtle myTurtle;
    int myArgs = 1;
    double myDist;
    private double arg1;
    private ArrayList<Double> arguments = new ArrayList<Double>();

    public Back (Turtle turtle) {
        myTurtle = turtle;
    }

    public Back (Turtle turtle, double dist) {
        this(turtle);
        myDist = dist;
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
        myDist = arg;
        add_arg(arg);
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        // TODO: Call on turtle to move it forward the given distance
        myTurtle.moveForward(-arguments.get(0));
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return myDist;
    }

    public void add_arg(double arg){
        arguments.add(arg);
    }

    public int check_arg(){
        return arguments.size();
    }
}

