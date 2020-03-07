package slogo.commands;

import slogo.controller.TurtleController;
import java.util.HashMap;
import java.util.Map;

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

    public void setVal(Double val) {
        variables = allVariables.get(myTurtle);
        if(variables == null) { variables = new HashMap<>(); }
        variables.put(myVarName, val);
        allVariables.put(myTurtle, variables);
    }

    @Override
    public boolean enoughArgs() {
        return true;
    }

    @Override
    public void setArgument(ICommand command) {

    }

    @Override
    public void execute() {
        // Should be empty
    }

    @Override
    public double returnVal() {
        return (double) allVariables.get(myTurtle).get(myVarName);
    }

    @Override
    public void clearArgs() {
        // shouldn't do anything
    }

    public Map<String, Double> getMap () {
        return allVariables.get(myTurtle);
    }
}
