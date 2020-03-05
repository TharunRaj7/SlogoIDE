package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;

public class ToManager extends To implements ICommand {

    private TurtleController myTurtle;
    int myArgs;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();
    private BlockCommand commands;

    private Variables variable;

    public ToManager (TurtleController turtle) {
        super(turtle);
        myArgs = 0;
    }

    @Override
    public boolean enoughArgs() {
        return checkArgs() == myArgs;
    }

    @Override
    public void setArgument(ICommand command) {

    }

    @Override
    public void execute() {
        commands.execute();
    }

    //TODO: Change the name of this method please
    public void execute2(Name name){
        ArrayList<BlockCommand> params = to_parameters.get(name);
        myArgs = (params.get(0)).argSize();
        for (int i = 0; i < arguments.size(); i++){
            (params.get(0)).getVar(i).setVal(arguments.get(i).returnVal());
        }
        commands = (BlockCommand)params.get(1);
    }

    public boolean isInMap(Name name){
        return to_parameters.containsKey(name);
    }

    @Override
    public double returnVal() {
        return 0.0;

    }

    @Override
    public void clearArgs() {

    }

    private int checkArgs() {
        return 0;

    }


}
