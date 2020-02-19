package slogo.commands;

public interface IManager {

    /**
     * Adds a command to the command tree to be executed
     * @param command
     */
    void addCommand(ICommand command);

    /**
     * Adds an argument to the leaf of the current command
     * Should check the command it's under if the args
     * are satisfied to execute
     * @param arg
     */
    void addArg(double arg);
}
