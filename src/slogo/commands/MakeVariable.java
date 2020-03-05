package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;
import java.util.Arrays;

public class MakeVariable implements ICommand{

    TurtleController myTurtle;
    int myArgs = 2;
    private ArrayList<ICommand> arguments = new ArrayList<ICommand>();

    public MakeVariable(TurtleController turtle) {
        myTurtle = turtle;
    }

    public MakeVariable() {
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
        add_arg(arg);
    }

    /**
     * Either uses setters on the turtle or calls other commands with the turtle
     * and arguments already provided
     */
    public void execute () {
        // TODO: Call on turtle to move it forward the given distance
        arguments.get(1).execute();
        if (arguments.get(0) instanceof Variables){
            ((Variables) arguments.get(0)).setVal(arguments.get(1).returnVal());
        }

    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal () {
        return arguments.get(1).returnVal();
    }

    @Override
    public void clearArgs() {
        arguments.clear();
    }

    public void add_arg(ICommand arg){
        arguments.add(arg);
    }

    public int check_arg(){
        return arguments.size();
    }
}

