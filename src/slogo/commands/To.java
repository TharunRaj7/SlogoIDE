package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.HashMap;

public class To extends BlockCommand implements ICommand {

    private Turtle myTurtle;
    int myArgs = 3;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    protected static ArrayList<ICommand> names = new ArrayList<>();
    protected static ArrayList<Variables> variables = new ArrayList<>();
    protected static ArrayList<BlockCommand> commands = new ArrayList<>();
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
