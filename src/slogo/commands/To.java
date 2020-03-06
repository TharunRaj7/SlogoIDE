package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;
import java.util.HashMap;

public class To extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    int myArgs = 3;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    protected static HashMap<Name, ArrayList<BlockCommand>> to_parameters = new HashMap<>();


    public To (TurtleController turtle) {
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
        if (returnVal() == 1.0) {
            ArrayList<BlockCommand> user_command_args = new ArrayList<>();
            user_command_args.add((BlockCommand) arguments.get(1));
            for (int i = 0; i < user_command_args.get(0).argSize(); i++) {
                user_command_args.get(0).getVar(i).setVal(null);
            }
            user_command_args.add((BlockCommand) arguments.get(2));
            to_parameters.put((Name) arguments.get(0), user_command_args);
        }
    }

    @Override
    public double returnVal() {
        try{
            if (((BlockCommand)arguments.get(2)).checkTree()){
                return 1.0;
            }
        }catch (Exception e){}
        return 0.0;
    }

    @Override
    public void clearArgs() {
        arguments.clear();
    }

    private int checkArgs() { return arguments.size(); }
}
