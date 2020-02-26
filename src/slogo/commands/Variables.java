package slogo.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Variables implements ICommand{

    private String myVarName;
    private static Map<String,Double> variables;

    public Variables(String varName) { // , Map<String, Double> varList
        //variables = varList;
        myVarName = varName;
    }

    public void setVal(double val) {
        if(variables == null) { variables = new HashMap<>(); }
        variables.put(myVarName, val);
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
        System.out.println(variables.get(myVarName) + "VarCall");
        return (double) variables.get(myVarName);
    }
}
