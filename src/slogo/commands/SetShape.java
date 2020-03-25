package slogo.commands;

import slogo.controller.TurtleController;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class SetShape implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 1;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public SetShape(TurtleController turtle) {
        myTurtle = turtle;
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
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute() {
        arguments.get(0).execute();
        myTurtle.setShape((int) arguments.get(0).returnVal());
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() { return arguments.get(0).returnVal(); }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() { arguments.clear(); }
}
