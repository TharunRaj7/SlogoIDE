package slogo.commands;

import slogo.controller.TurtleController;
import java.util.ArrayList;

public class GetPenColor implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 1;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public GetPenColor(TurtleController turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() { return arguments.size() == myArgs; }

    @Override
    public void setArgument(ICommand command) { arguments.add(command); }

    @Override
    public void execute() { arguments.get(0).execute(); }

    @Override
    public double returnVal() { return myTurtle.getPenColorIndex(); }

    @Override
    public void clearArgs() { arguments.clear(); }
}