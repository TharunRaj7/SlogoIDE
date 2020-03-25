package slogo.commands;

import slogo.controller.TurtleController;
import slogo.view.ExceptionFeedback;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class For extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 2;
    private ArrayList<BlockCommand> arguments = new ArrayList<>();
    private int[] forArgs = new int[3];
    private Variables variable;

    public For (TurtleController turtle) {
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
        arguments.add((BlockCommand)command);
    }

    /**
     * Runs the block command the given amount of times
     * If a block command is not given, it throws an error with ExceptionFeedback
     */
    public void execute() {
        try{
            BlockCommand firstArg = (BlockCommand) arguments.get(0);
            variable = firstArg.getVar(0);
            variable.setVal(0.0);

            firstArg.execute();

            for (int i = 0; i < forArgs.length; i++) {
                forArgs[i] = (int) firstArg.getRetVals(i + 1);
            }

            variable.setVal((double) forArgs[0]);

            try{
                while (variable.returnVal() <= forArgs[1]) {
                    arguments.get(1).execute();
                    variable.setVal(variable.returnVal() + forArgs[2]);
                }
            }catch (Exception e){
                ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
            }
        }catch (Exception e){
            ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
        }
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return arguments.get(1).returnVal();
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
    }

}
