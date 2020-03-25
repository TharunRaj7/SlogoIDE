package slogo.commands;

import slogo.controller.TurtleController;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class SetHeading implements ICommand{

    TurtleController myTurtle;
    int myArgs = 1;
    double myAngle;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public SetHeading (TurtleController turtle) {
        myTurtle = turtle;
    }

    public SetHeading (TurtleController turtle, double angle) {
        this(turtle);
        myAngle = angle;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs () {
        return check_arg() == myArgs;
    }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param command
     */
    public void setArgument (ICommand command) {
        arguments.add(command);
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        arguments.get(0).returnVal();
        myTurtle.setHeading(arguments.get(0).returnVal());
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return myAngle;
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
    }

    public int check_arg(){
        return arguments.size();
    }
}

