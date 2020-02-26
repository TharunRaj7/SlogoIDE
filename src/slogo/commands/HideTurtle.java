package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.Arrays;

public class HideTurtle implements ICommand{

    Turtle myTurtle;
    int myArgs = 0;
    private ArrayList<Double> arguments = new ArrayList<Double>();

    public HideTurtle (Turtle turtle) {
        myTurtle = turtle;
    }

    public HideTurtle (Turtle turtle, double dist) {
        this(turtle);
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
    public void setArgument (ICommand arg) {

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
        return 0.0;
    }

    @Override
    public void clearArgs() {
        // Should do nothing
    }


    public int check_arg(){
        return arguments.size();
    }
}