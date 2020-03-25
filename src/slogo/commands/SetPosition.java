package slogo.commands;

import slogo.controller.TurtleController;
import slogo.utility.Location;
import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class SetPosition implements ICommand {

    TurtleController myTurtle;
    int myArgs = 2;
    double myDist;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public SetPosition (TurtleController turtle) {
        myTurtle = turtle;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs() {
        return check_arg() == myArgs;
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
        arguments.get(1).execute();
        double futureX = arguments.get(0).returnVal();
        double futureY = arguments.get(1).returnVal();

        myDist = Math.sqrt(Math.pow(myTurtle.getLocation().getX() - futureX, 2.0) + Math.pow(myTurtle.getLocation().getY() - futureY, 2.0));

        Location future = new Location(futureX, futureY);
        myTurtle.moveTo(future);
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return myDist;
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
