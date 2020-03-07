package slogo.commands;

import slogo.controller.TurtleController;

public class Name implements ICommand {

    private TurtleController myTurtle;
    private String myName;

    public Name(TurtleController turtle) {

    }

    public Name(TurtleController turtle, String name) {
        myTurtle = turtle;
        myName = name;
    }

    @Override
    public boolean equals(Object o) {
        try {
            Name n = (Name) o;
            return myName.equals(n.myName);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return myName.hashCode();
    }

    @Override
    public boolean enoughArgs() {
        return true;
    }

    @Override
    public void setArgument(ICommand command) {
        // Do nothing, no args
    }

    @Override
    public void execute() {
        // Do nothing, just a name
    }

    @Override
    public double returnVal() {
        return 0;
    }

    @Override
    public void clearArgs() {
        // Do nothing, no args
    }
}
