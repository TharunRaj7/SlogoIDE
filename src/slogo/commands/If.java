package slogo.commands;

import slogo.controller.TurtleController;

import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class If extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 2;
    private double retVal = 0.0;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public If (TurtleController turtle) {
        myTurtle = turtle;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs() {
        return arguments.size() == myArgs;
    }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param command
     */
    public void setArgument(ICommand command) {
        arguments.add(command);
    }

    /**
     * Runs the block command the given amount of times
     * If a block command is not given, it throws an error with ExceptionFeedback
     */
    public void execute() {
        arguments.get(0).execute();
        if(arguments.get(0).returnVal() != 0.0) {
            arguments.get(1).execute();
            retVal = 1.0;
        }
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return retVal;
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
    }

}
