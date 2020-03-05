package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.HashMap;

public class To extends BlockCommand implements ICommand {

    private Turtle myTurtle;
    int myArgs = 3;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    protected static HashMap<Name, ArrayList<BlockCommand>> to_parameters = new HashMap<>();


    public To (Turtle turtle) {
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
        ArrayList<BlockCommand> user_command_args = new ArrayList<>();
        user_command_args.add((BlockCommand)arguments.get(1));
        user_command_args.add((BlockCommand)arguments.get(2));
        to_parameters.put((Name)arguments.get(0),user_command_args);
    }

    @Override
    public double returnVal() {
        return 0.0;
    }

    @Override
    public void clearArgs() {
        arguments.clear();
    }

    private int checkArgs() { return arguments.size(); }
}
