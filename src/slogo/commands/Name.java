package slogo.commands;

import slogo.controller.TurtleController;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class Name implements ICommand {

    private TurtleController myTurtle;
    private String myName;

    public Name(TurtleController turtle) {
        // Shouldn't do anything
    }

    public Name(TurtleController turtle, String name) {
        myTurtle = turtle;
        myName = name;
    }

    /**
     * Overrides default object .equals
     * Compares the strings held instead
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        try {
            Name n = (Name) o;
            return myName.equals(n.myName);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Overrides default object.hashcode
     * Replaces value with what it would have been if it were just a string
     * @return
     */
    @Override
    public int hashCode() {
        return myName.hashCode();
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
        // Do nothing, no args
    }

    /**
     * Runs the block command the given amount of times
     * If a block command is not given, it throws an error with ExceptionFeedback
     */
    public void execute() {
        // Do nothing, just a name
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return 0;
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        // Do nothing, no args
    }
}
