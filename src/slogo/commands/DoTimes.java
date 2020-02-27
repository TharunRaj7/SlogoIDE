package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;

public class DoTimes implements ICommand {

    private Turtle myTurtle;
    int myArgs = 2;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private int[] doArgs = new int[1];
    private Variables variable;

    public DoTimes (Turtle turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() {
        return checkArgs() == myArgs;
    }

    @Override
    public void setArgument(ICommand command) {
        arguments.add(command);
    }

    @Override
    public void execute() {
        if (arguments.get(0) instanceof BlockCommand) {
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

    private int checkArgs() { return arguments.size(); }
}