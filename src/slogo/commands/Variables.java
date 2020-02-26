package slogo.commands;

public class Variables implements ICommand{
    @Override
    public boolean enoughArgs() {
        return false;
    }

    @Override
    public void setArgument(ICommand command) {
        // TODO:
    }

    @Override
    public void execute() {

    }

    @Override
    public double returnVal() {
        return 0;
    }
}
