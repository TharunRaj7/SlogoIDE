package slogo.commands;

import slogo.controller.TurtleController;

import java.util.ArrayList;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class ToManager extends MakeUserInstruction implements ICommand {

    private TurtleController myTurtle;
    int myArgs;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();
    private BlockCommand commands;
    private ArrayList<BlockCommand> params;

    public ToManager (TurtleController turtle) {
        myTurtle = turtle;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs() {
        return arguments.size() == myArgs;
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
        for (int i = 0; i < arguments.size(); i++){
            (params.get(0)).getVar(i).setVal(arguments.get(i).returnVal());
        }
        commands.execute();
    }

    /**
     * Called from the parser to add the name's execution block to the execution of
     * this command
     * @param name
     */
    public void execute2(Name name){
        params = to_parameters.get(name);
        myArgs = params.get(0).argSize();
        commands = params.get(1);
    }

    /**
     * Checks if the name given is in the hash map for user made instructions
     * @param name
     * @return
     */
    public boolean isInMap(Name name){
        return to_parameters.containsKey(name);
    }

    public boolean isOverwrite () {
        return overwrite;
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return commands.returnVal();
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
    }
}
