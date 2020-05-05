package slogo.commands;

import slogo.controller.TurtleController;
import slogo.view.ExceptionFeedback;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class MakeVariable implements ICommand{

    TurtleController myTurtle;
    int myArgs = 2;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

    public MakeVariable(TurtleController turtle) {
        myTurtle = turtle;
    }

    public MakeVariable() {
        // Shouldn't do anything
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
        arguments.get(1).execute();
        try {
            ((Variables) arguments.get(0)).setVal(arguments.get(1).returnVal());
            myTurtle.addTableData(((Variables) arguments.get(0)).getMap());
        } catch (Exception e) {
            ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
        }
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return arguments.get(1).returnVal();
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
    }
}

