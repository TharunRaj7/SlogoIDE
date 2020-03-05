package slogo.commands;

import slogo.controller.Turtle;

import java.util.ArrayList;

public class Repeat extends BlockCommand implements ICommand {

    private Turtle myTurtle;
    int myArgs = 3;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    private Variables repcount = new Variables(":repcount");
    private static ArrayList<Double> repcountTracker;
    private int repcountIndex;

    public Repeat (Turtle turtle) {
        myTurtle = turtle;
        /*
        if(repcountTracker == null) { repcountTracker = new ArrayList<>(); }
        repcountIndex = repcountTracker.size();
        if(repcountTracker.size() != 0) {
            repcountTracker.set(repcountTracker.size() - 1, repcount.returnVal());
        }
        repcountTracker.add(1.0);
        System.out.println(repcountTracker);
        */
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
        //repcount.setVal(repcountTracker.get(repcountIndex));

        if (arguments.get(2) instanceof BlockCommand) {

            for (int i = 1; i <= arguments.get(1).returnVal(); i++) {
                //System.out.println("Calling repeated block");
                arguments.get(2).execute();
                //System.out.println("Ended calling repeated block");
                repcount.setVal((double) i);
            }
            /*
            while (arguments.get(0).returnVal() <= arguments.get(1).returnVal()) {
                System.out.println("Calling repeated block");
                arguments.get(2).execute();
                //repcount.setVal(repcount.returnVal() + 1.0);
                double iter = repcountTracker.get(repcountIndex) + 1;
                repcountTracker.set(repcountIndex, iter);
                System.out.println(repcountTracker);
                repcount.setVal(iter);
            }
            repcountTracker.set(repcountIndex, 1.0);

             */
        }
    }

    @Override
    public double returnVal() {

        for (ICommand command: arguments) {
            if (command instanceof BlockCommand) { return command.returnVal(); }
        }
        return 0.0;

        //System.out.println(arguments);
        //return arguments.get(2).returnVal();
    }

    @Override
    public void clearArgs() {
        /*
        for ( ICommand command : arguments) {
            command.clearArgs();
        }

         */
        arguments.clear();
        //repcount.setVal(1.0);
        setArgument(repcount);
    }

    private int checkArgs() { return arguments.size(); }
}
