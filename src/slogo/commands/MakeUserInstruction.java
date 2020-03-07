package slogo.commands;

import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.util.ArrayList;
import java.util.HashMap;

public class MakeUserInstruction extends BlockCommand implements ICommand {

    private TurtleController myTurtle;
    int myArgs = 3;
    private ArrayList<ICommand> arguments = new ArrayList<>();
    protected static HashMap<Name, ArrayList<BlockCommand>> to_parameters = new HashMap<>();
    protected static boolean overwrite;

    public MakeUserInstruction () {
        // Should be empty
    }

    public MakeUserInstruction (TurtleController turtle) {
        System.out.println("Setting overwrite to true");
        overwrite = true;
        myTurtle = turtle;
    }

    @Override
    public boolean enoughArgs() {
        return checkArgs() == myArgs;
    }

    @Override
    public void setArgument(ICommand command) {
        System.out.println("Setting overwrite to false");
        overwrite = false;
        arguments.add(command);
    }

    @Override
    public void execute() {
        if(returnVal() == 1) {
            ArrayList<BlockCommand> user_command_args = new ArrayList<>();
            user_command_args.add((BlockCommand) arguments.get(1));
            for (int i = 0; i < user_command_args.get(0).argSize(); i++) {
                user_command_args.get(0).getVar(i).setVal(0.0);
            }
            user_command_args.add((BlockCommand) arguments.get(2));
            //to_parameters.put(arguments.get(0), user_command_args);
            to_parameters.put((Name) arguments.get(0), user_command_args);
        }
        // TODO: Throw an error corresponding with incorrect tree format
    }

    @Override
    public double returnVal() {
        try{
            if (((BlockCommand)arguments.get(2)).checkTree()){
                return 1.0;
            }
        }catch (Exception e){}
        return 0.0;
    }

    @Override
    public void clearArgs() {
        arguments.clear();
    }

    private int checkArgs() { return arguments.size(); }
}
