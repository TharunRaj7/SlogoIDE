package slogo.commands;

import slogo.controller.TurtleController;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class Turtles implements ICommand {

    private TurtleController myTurtle;

    public Turtles (TurtleController turtle) {
        myTurtle = turtle;
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
     * @param command
     */
    public void setArgument(ICommand command) {
        // Should be empty
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute() {
        // Should be empty
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return myTurtle.numberOfTurtlesCreated();
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        // Should be empty
    }
}