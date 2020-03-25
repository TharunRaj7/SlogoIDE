package slogo.commands;

import slogo.controller.Turtle;
import slogo.utility.MathOps;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class Sine implements ICommand{

    Turtle myTurtle;
    int myArgs = 1;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

    public Sine(Turtle turtle) {
        myTurtle = turtle;
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
        arguments.add(arg);
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        // Shouldn't do anything
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return MathOps.sin(arguments.get(0).returnVal());
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() { arguments.clear(); }

    private int check_arg(){
        return arguments.size();
    }
}
