package slogo.commands;

import slogo.controller.TurtleController;
import slogo.utility.Location;

public class Home implements ICommand{

    private TurtleController myTurtle;
    private double distMoved;

    public Home (TurtleController turtle) { myTurtle = turtle; }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs() { return true; }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param command
     */
    public void setArgument(ICommand command) {
        // Should be empty
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute() {
        distMoved = Math.sqrt(Math.pow(myTurtle.getLocation().getX(), 2.0) + Math.pow(myTurtle.getLocation().getY(), 2.0));
        Location origin = new Location(0, 0);
        myTurtle.moveTo(origin);
        myTurtle.setHeading(0.0);
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() { return distMoved; }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        // Does nothing
    }
}
