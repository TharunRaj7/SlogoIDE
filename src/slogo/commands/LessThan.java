package slogo.commands;

import slogo.controller.TurtleController;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class LessThan implements ICommand{

    private TurtleController myTurtle;
    private int myArgs = 2;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

    public LessThan(TurtleController turtle) {
        myTurtle = turtle;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs () {
        return arguments.size() == myArgs;
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
        if (arguments.get(0).returnVal()<arguments.get(1).returnVal()){
            return 1.0;
        }
        else{
            return 0.0;
        }
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() { arguments.clear(); }

}
