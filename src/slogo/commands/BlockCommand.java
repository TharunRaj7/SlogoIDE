package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;
import java.util.List;

public class BlockCommand implements ICommand {

    //private Turtle myTurtle;
    private ArrayList<ICommand> myCommands = new ArrayList<>();
    private double returnValue;

    public BlockCommand() {
        // Should be empty for now
    }

    @Override
    public boolean enoughArgs() {
        return true;
    }

    @Override
    public void setArgument(ICommand command) {
        // Blocks should not have arguments, therefore this should be used to add commands
        // to the command list
        myCommands.add(command);
    }

    @Override
    public void execute() {
        Manager blockManager = new Manager();
        for (ICommand command : myCommands) {
            blockManager.addCommand(command);
        }
        returnValue = blockManager.retCommand(myCommands.get(0));
    }

    @Override
    public double returnVal() {
        return returnValue;
    }
}
