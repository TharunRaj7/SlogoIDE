package slogo.commands;

import slogo.controller.TurtleController;
import slogo.view.ExceptionFeedback;
import java.util.ArrayList;

public class Repeat extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    private int myArgs = 3;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private Variables repcount;

    public Repeat (TurtleController turtle) {
        myTurtle = turtle;
        repcount = new Variables(":repcount", myTurtle);
        repcount.setVal(1.0);
        setArgument(repcount);
    }

    @Override
    public boolean enoughArgs() { return arguments.size() == myArgs; }

    @Override
    public void setArgument(ICommand command) { arguments.add(command); }

    @Override
    public void execute() {
        arguments.get(1).execute();

        if (arguments.get(2) instanceof BlockCommand) {
            for (int i = 1; i <= arguments.get(1).returnVal(); i++) {
                arguments.get(2).execute();
                repcount.setVal((double) i);
            }
        }
    }

    @Override
    public double returnVal() {
        for (ICommand command: arguments) {
            try {
                return ((BlockCommand) command).returnVal();
            } catch (Exception e){
                //ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
                // Shouldn't do anything
            }
        }
        return 0.0;
    }

    @Override
    public void clearArgs() {
        arguments.clear();
        setArgument(repcount);
    }
}
