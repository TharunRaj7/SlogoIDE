package slogo.commands;

import slogo.controller.TurtleController;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class SetTowards implements ICommand{

    TurtleController myTurtle;
    int myArgs = 2;
    double myCurrAngle;
    double myAngle;
    Double myX = null;
    Double myY = null;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

    public SetTowards (TurtleController turtle) {
        myTurtle = turtle;
    }

    public SetTowards (TurtleController turtle, double x, double y) {
        this(turtle);
        myX = x;
        myY = y;
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
        arguments.get(0).execute();
        arguments.get(1).execute();
        myTurtle.towards(arguments.get(0).returnVal(), arguments.get(1).returnVal());
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return Math.abs(myAngle - myCurrAngle);
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
    }

    private int check_arg(){
        return arguments.size();
    }
}
