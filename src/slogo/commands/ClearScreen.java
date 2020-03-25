package slogo.commands;

import slogo.controller.TurtleController;

public class ClearScreen implements ICommand {

    private TurtleController myTurtle;
    private double distMoved;

    public ClearScreen (TurtleController turtle) { myTurtle = turtle; }

    /**
     * Returns true because this should never have arguments
     * @return
     */
    public boolean enoughArgs() {
        return true;
    }

    /**
     * Arguments cannot be set for this command
     * @param command
     */
    public void setArgument(ICommand command) {
        // Should be empty
    }

    /**
     * Calculates return value, then clears the screen
     */
    public void execute() {
        distMoved = Math.sqrt(Math.pow(myTurtle.getLocation().getX(), 2.0) + Math.pow(myTurtle.getLocation().getY(), 2.0));
        myTurtle.clear();
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return distMoved;
    }

    /**
     * Can't clear args with no args in the first place
     */
    public void clearArgs () {
        // Shouldn't do anything
    }
}
