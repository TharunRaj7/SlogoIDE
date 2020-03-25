package slogo.commands;

import slogo.controller.TurtleController;
import slogo.view.ExceptionFeedback;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class Repeat extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 3;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private Variables repcount;

    public Repeat (TurtleController turtle) {
        myTurtle = turtle;
        repcount = new Variables(":repcount", myTurtle);
        repcount.setVal(1.0);
        setArgument(repcount);
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs() { return arguments.size() == myArgs; }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param command
     */
    public void setArgument(ICommand command) { arguments.add(command); }

    /**
     * Runs the block command the given amount of times
     * If a block command is not given, it throws an error with ExceptionFeedback
     */
    public void execute() {
        arguments.get(1).execute();

        if (arguments.get(2) instanceof BlockCommand) {
            for (int i = 1; i <= arguments.get(1).returnVal(); i++) {
                arguments.get(2).execute();
                repcount.setVal((double) i);
            }
        }
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        for (ICommand command: arguments) {
            try {
                return ((BlockCommand) command).returnVal();
            } catch (Exception e){
                //ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
                // Shouldn't do anything
            }
        }
        return 0.0;
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
        setArgument(repcount);
    }
}
