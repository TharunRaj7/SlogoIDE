package slogo.commands;

import slogo.controller.TurtleController;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class Argument implements ICommand {

    private double myArgument;

    public Argument(double argument) {
        myArgument = argument;
    }

    public Argument (TurtleController turtleController){
        // Shouldn't do anything
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs() {
        return true;
    }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param arg
     */
    public void setArgument (ICommand arg) {
        // Shouldn't do anything
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        // Should be empty outside of procedural stuff
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return myArgument;
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        // Shouldn't do anything
    }
}
