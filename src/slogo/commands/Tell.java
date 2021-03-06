package slogo.commands;

import slogo.controller.TurtleController;
import slogo.view.ExceptionFeedback;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class Tell extends BlockCommand implements ICommand{

    TurtleController myShell;
    int myArgs = 1;
    double val;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private List<Integer> turtles = new ArrayList<>();

    public Tell(TurtleController turtleController){
        myShell = turtleController;
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
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute() {
        arguments.get(0).execute();

        try{
            for (int i = 0; i < ((BlockCommand) arguments.get(0)).argSize(); i++ ){
                turtles.add((int) ((BlockCommand) arguments.get(0)).getRetVals(i));
                val = (int) ((BlockCommand) arguments.get(0)).getRetVals(i);
            }
        } catch (Exception e){
            ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
        }
        myShell.tellTurtles(turtles);
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return val;
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
    }
}
