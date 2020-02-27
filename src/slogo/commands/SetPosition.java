package slogo.commands;

import slogo.controller.Turtle;
import slogo.utility.Location;

import java.util.ArrayList;

public class SetPosition implements ICommand {

    Turtle myTurtle;
    int myArgs = 2;
    double myDist;
    private ArrayList<ICommand> arguments = new ArrayList<>();

    public SetPosition (Turtle turtle) {
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() {
        return check_arg() == myArgs;
    }

    @Override
    public void setArgument(ICommand command) {
        arguments.add(command);
    }

    @Override
    public void execute() {
        arguments.get(0).execute();
        arguments.get(1).execute();
        double futureX = arguments.get(0).returnVal();
        double futureY = arguments.get(0).returnVal();

        myDist = Math.sqrt(Math.pow(myTurtle.getLocation().getX() - futureX, 2.0) + Math.pow(myTurtle.getLocation().getY() - futureY, 2.0));

        Location future = new Location(futureX, futureY);
        myTurtle.moveTo(future);
    }

    @Override
    public double returnVal() {
        return myDist;
    }

    @Override
    public void clearArgs() {
        arguments.clear();
    }

    private int check_arg(){
        return arguments.size();
    }
}
