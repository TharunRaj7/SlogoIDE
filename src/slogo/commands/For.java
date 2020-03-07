package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;

public class For extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    int myArgs = 2;
    private ArrayList<BlockCommand> arguments = new ArrayList<>();
    private int[] forArgs = new int[3];
    private Variables variable;

    public For (TurtleController turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() {
        return checkArgs() == myArgs;
    }

    @Override
    public void setArgument(ICommand command) {
        arguments.add((BlockCommand)command);
    }

    @Override
    public void execute() {

        //TODO
        if (arguments.get(0) instanceof BlockCommand) {
            BlockCommand firstArg = (BlockCommand) arguments.get(0);
            variable = firstArg.getVar(0);
            variable.setVal(0.0);

            firstArg.execute();

            for (int i = 0; i < forArgs.length; i++) {
                forArgs[i] = (int) firstArg.getRetVals(i + 1);
            }

            variable.setVal((double) forArgs[0]);


            //TODO
            if(arguments.get(1) instanceof BlockCommand) {
                while (variable.returnVal() <= forArgs[1]) {
                    arguments.get(1).execute();
                    variable.setVal(variable.returnVal() + forArgs[2]);
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
