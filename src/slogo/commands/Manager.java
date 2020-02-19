package slogo.commands;

import javax.swing.tree.TreeNode;

public class Manager implements IManager{

    public Manager () {
        // TODO: Initialize command tree, hold onto it as a private variable
    }

    /**
     * Adds a command to the command tree to be executed
     * @param command
     */
    public void addCommand(ICommand command) {
        // TODO: Add a command to the most recent node on the TreeList, check if the command has enough args to run
        // TODO: Set that command to the current node
    }

    /**
     * Adds an argument to the leaf of the current command
     * Should check the command it's under if the args
     * are satisfied to execute
     * @param arg
     */
    public void addArg(double arg) {
        // TODO: Add a value as a leaf to the most recent command
        // TODO: Check if that argument is enough for the command to run. If so, run it
    }

    /**
     * Adds a variable to the map held in the manager
     * Able to be recalled as an argument for command calls later
     * @param name
     * @param value
     */
    public void addVariable(String name, double value) {
        // TODO: Add the variable given the string name and value to the map of variables
    }

    /**
     * Runs the command with the arguments, and returns a double to
     * be used as another argument or to be passed to a variable
     * @param command
     * @param args
     * @return val for argument or variable
     */
    public double runCommand(ICommand command, double ... args) {
        command.execute();
        return command.returnVal();
    }
}
