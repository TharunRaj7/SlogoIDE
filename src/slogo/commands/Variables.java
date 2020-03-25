package slogo.commands;

import slogo.controller.TurtleController;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class Variables implements ICommand{

    private TurtleController myTurtle;
    private String myVarName;
    private static Map<TurtleController, Map<String, Double>> allVariables;
    private Map<String,Double> variables;

    public Variables(String varName, TurtleController turtle) {
        if(allVariables == null) { allVariables = new HashMap<>(); }
        myTurtle = turtle;
        myVarName = varName;
        variables = allVariables.get(turtle);
    }

    public Variables(TurtleController turtle){
        myTurtle = turtle;
    }


    /**
     * Sets the value of the variable
     * @param val
     */
    public void setVal(Double val) {
        variables = allVariables.get(myTurtle);
        if(variables == null) { variables = new HashMap<>(); }
        variables.put(myVarName, val);
        allVariables.put(myTurtle, variables);
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs() {
        return true;
    }

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
        // Should be empty
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        return (double) allVariables.get(myTurtle).get(myVarName);
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        // shouldn't do anything
    }

    /**
     * Returns a copy of the map of the variable names and their values
     * @return
     */
    public Map<String, Double> getMap () {
        return allVariables.get(myTurtle);
    }
}
