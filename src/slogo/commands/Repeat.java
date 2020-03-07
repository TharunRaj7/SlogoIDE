package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;

public class Repeat extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    int myArgs = 3;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private Variables repcount = new Variables(":repcount");

    public Repeat (TurtleController turtle) {
        myTurtle = turtle;
        repcount.setVal(1.0);
        setArgument(repcount);
    }

    @Override
    public boolean enoughArgs() { return checkArgs() == myArgs; }

    @Override
    public void setArgument(ICommand command) { arguments.add(command); }

    @Override
    public void execute() {
        arguments.get(1).execute();

        if (arguments.get(2) instanceof BlockCommand) {
            System.out.println(arguments.get(1).returnVal());

            for (int i = 1; i <= arguments.get(1).returnVal(); i++) {
                //System.out.println("Calling repeated block");
                arguments.get(2).execute();
                //System.out.println("Ended calling repeated block");
                repcount.setVal((double) i);
            }
        }
    }

    @Override
    public double returnVal() {
        for (ICommand command: arguments) {
            if (command instanceof BlockCommand) { return command.returnVal(); }
        }
        return 0.0;
    }

    @Override
    public void clearArgs() {
        arguments.clear();
        setArgument(repcount);
    }

    private int checkArgs() { return arguments.size(); }
}
