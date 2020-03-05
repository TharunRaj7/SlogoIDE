package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;
import slogo.utility.MathOps;

import java.util.ArrayList;
import java.util.Arrays;

public class Heading implements ICommand{

    TurtleController myTurtle;
    int myArgs = 0;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

    public Heading (TurtleController turtle) {
        myTurtle = turtle;
    }


    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs () {
        return check_arg() == myArgs;
    }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * @param arg
     */
    public void setArgument (ICommand arg) {
        arguments.add(arg);
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {

    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return myTurtle.getHeading();


    }

    @Override
    public void clearArgs() { arguments.clear(); }

    private void add_arg(ICommand arg){
        arguments.add(arg);
    }

    private int check_arg(){
        return arguments.size();
    }
}
