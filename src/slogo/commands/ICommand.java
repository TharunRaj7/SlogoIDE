package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

public interface ICommand {

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    boolean enoughArgs ();

    /**
     * Gives the command an argument command
     * Manager will check if sufficient and run if needed
     * @param command
     */
    void setArgument (ICommand command);

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    void execute ();

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    double returnVal ();

    /**
     * Remove all the commands from arguments list
     */
    void clearArgs ();

    default ICommand copy(ICommand command){
        try {
            return this.getClass().getConstructor(TurtleController.class).newInstance(new TurtleController());
        } catch (Exception ignored){}

        return null;
    }

}
