package slogo.commands;

import slogo.controller.TurtleController;
import slogo.view.ExceptionFeedback;

import java.util.ArrayList;

public class DoTimes extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 2;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private int[] doArgs = new int[1];
    private Variables variable;

    public DoTimes (TurtleController turtle) {
        myTurtle = turtle;
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
        try{
            BlockCommand firstArg = (BlockCommand) arguments.get(0);
            variable = firstArg.getVar(0);
            variable.setVal(1.0);

            firstArg.execute();

            for (int i = 0; i < doArgs.length; i++) {
                doArgs[i] = (int) firstArg.getRetVals(i + 1);
            }

            if(arguments.get(1) instanceof BlockCommand) {
                while (variable.returnVal() <= doArgs[0]) {
                    arguments.get(1).execute();
                    variable.setVal(variable.returnVal() + 1);
                }
            }
        } catch (Exception e){
            ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
        }

    }

    @Override
    public double returnVal() {
        return arguments.get(1).returnVal();
    }

    @Override
    public void clearArgs() {
        arguments.clear();
    }
}