package slogo.commands;

import slogo.controller.Turtle;

public interface ICommand {

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @param numArgs
     * @return
     */
    boolean enoughArgs (int numArgs);

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     * @param turtle
     */
    void execute (Turtle turtle);

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    double returnVal ();
}
