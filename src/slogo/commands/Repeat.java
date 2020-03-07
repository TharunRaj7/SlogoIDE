package slogo.commands;

import slogo.controller.Turtle;
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
            try{
                return command.returnVal();
            }catch (Exception e){
                ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
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
