package slogo.commands;

import slogo.controller.TurtleController;
import slogo.view.ExceptionFeedback;

import java.util.ArrayList;
import java.util.List;

public class Tell extends BlockCommand implements ICommand{

    TurtleController myShell;
    int myArgs = 1;
    double val;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private List<Integer> turtles = new ArrayList<>();

    public Tell(TurtleController turtleController){
        myShell = turtleController;
    }

    @Override
    public boolean enoughArgs() {
        return arguments.size() == myArgs;
    }

    @Override
    public void setArgument(ICommand command) {
        arguments.add(command);
    }

    @Override
    public void execute() {
        arguments.get(0).execute();

        try{
            for (int i = 0; i < ((BlockCommand) arguments.get(0)).argSize(); i++ ){
                turtles.add((int) ((BlockCommand) arguments.get(0)).getRetVals(i));
                val = (int) ((BlockCommand) arguments.get(0)).getRetVals(i);
            }
        }catch (Exception e){
            ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
        }

        myShell.tellTurtles(turtles);
    }

    @Override
    public double returnVal() {
        return val;
    }

    @Override
    public void clearArgs() {
        arguments.clear();

    }
}
