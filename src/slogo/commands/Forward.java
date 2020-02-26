package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.Arrays;

public class Forward implements ICommand{

    Turtle myTurtle;
    int myArgs = 1;
    double myDist;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

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
        myTurtle.moveRelative(arguments.get(0).returnVal());
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return arguments.get(0).returnVal();
    }

    public void add_arg(ICommand arg){
        arguments.add(arg);
    }

    public int check_arg(){
        return arguments.size();
    }
}
