package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.Arrays;

public class SetHeading implements ICommand{

    Turtle myTurtle;
    int myArgs = 2;
    Double myX = null;
    Double myY = null;
    private ArrayList<Double> arguments = new ArrayList<Double>();

    public SetHeading (Turtle turtle) {
        myTurtle = turtle;
    }

    public SetHeading (Turtle turtle, double x, double y) {
        this(turtle);
        myX = x;
        myY = y;
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
        if (myX.equals(null)){
            myX = arg;
            add_arg(arg);
        }
        else{
            myY = arg;
            add_arg(arg);
        }
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        // TODO: Call on turtle to move it forward the given distance
        myTurtle.setHeading(arguments.get(0));
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return myAngle;
    }

    public void add_arg(double arg){
        arguments.add(arg);
    }

    public int check_arg(){
        return arguments.size();
    }
}
