package slogo.commands;

public class Argument implements ICommand {

    double myArgument;

    public Argument(double argument) {
        myArgument = argument;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    @Override
    public boolean enoughArgs() {
        return true;
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
        // Should be empty outside of procedural stuff
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return myArgument;
    }

    @Override
    public void clearArgs() {
        // Shouldn't do anything
    }
}
