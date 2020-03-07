package slogo.commands;

import slogo.controller.TurtleController;
import java.util.ArrayList;

public class SetPenColor implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 1;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public SetPenColor(TurtleController turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() { return arguments.size() == myArgs; }

    @Override
    public void setArgument(ICommand command) { arguments.add(command); }

    @Override
    public void execute() {
        arguments.get(0).execute();
        myTurtle.setPenColor((int) arguments.get(0).returnVal());
    }

    @Override
    public double returnVal() { return arguments.get(0).returnVal(); }

    @Override
    public void clearArgs() { arguments.clear(); }
}
