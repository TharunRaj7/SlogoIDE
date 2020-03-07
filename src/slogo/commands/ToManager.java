package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;

public class ToManager extends MakeUserInstruction implements ICommand {

    private TurtleController myTurtle;
    int myArgs;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();
    private BlockCommand commands;
    private ArrayList<BlockCommand> params;

    private Variables variable;

    public ToManager (TurtleController turtle) {
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
        for (int i = 0; i < arguments.size(); i++){
            System.out.println("Value from first variable:");
            System.out.println(arguments.get(i).returnVal());
            (params.get(0)).getVar(i).setVal(arguments.get(i).returnVal());
        }

        commands.execute();
    }

    //TODO: Change the name of this method please
    public void execute2(Name name){
        params = to_parameters.get(name);
        myArgs = params.get(0).argSize();
        System.out.println("Number of args:");
        System.out.println((params.get(0)).argSize());
        commands = params.get(1);
    }

    public boolean isInMap(Name name){
        return to_parameters.containsKey(name);
    }

    public boolean isOverwrite () {
        return overwrite;
    }

    @Override
    public double returnVal() {
        return commands.returnVal();
    }

    @Override
    public void clearArgs() {
        arguments.clear();
    }

    private int checkArgs() {
        return arguments.size();

    }


}
