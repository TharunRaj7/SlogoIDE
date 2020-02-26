package slogo.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Variables implements ICommand{

    private String myVarName;
    private Map<String,Double> variables;
    private ArrayList<String> current_variables = new ArrayList<>();

    public Variables(String varName, Map<String, Double> varList) {
        variables = varList;
        myVarName = varName;
    }

    public void setVal(double val) {
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
        return (double) variables.get(myVarName);
    }
}
