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

    /**
     * Adds a variable to the map held in the manager
     * Able to be recalled as an argument for command calls later
     * @param name
     * @param value
     */
    void addVariable(String name, double value);

    /**
     * Runs the command with the arguments, and returns a double to
     * be used as another argument or to be passed to a variable
     * @param command
     * @param args
     * @return val for argument or variable
     */
    double runCommand(ICommand command);
}
