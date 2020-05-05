package slogo.commands;

import slogo.controller.TurtleController;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
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
        //System.out.println("Setting overwrite to true");
        overwrite = true;
        myTurtle = turtle;
    }

    /**
     * Checks to see if the number of arguments available are sufficient
     * to run the command
     * @return
     */
    public boolean enoughArgs() {
        return arguments.size() == myArgs;
    }

    /**
     * Gives the command an argument
     * Manager will check if sufficient and run if needed
     * Sets overwriting to false
     * @param command
     */
    public void setArgument(ICommand command) {
        //System.out.println("Setting overwrite to false");
        overwrite = false;
        arguments.add(command);
    }

    /**
     * If the commands in its block make up a valid tree, add the block
     * to the hashmap of user instructions
     */
    public void execute() {
        if(returnVal() == 1) {
            ArrayList<BlockCommand> user_command_args = new ArrayList<>();
            user_command_args.add((BlockCommand) arguments.get(1));
            for (int i = 0; i < user_command_args.get(0).argSize(); i++) {
                user_command_args.get(0).getVar(i).setVal(0.0);
            }
            user_command_args.add((BlockCommand) arguments.get(2));
            to_parameters.put((Name) arguments.get(0), user_command_args);
        }
    }

    /**
     * Is the output value that has to be present for every command
     * @return value designated by type of command
     */
    public double returnVal() {
        try{
            if (((BlockCommand)arguments.get(2)).checkTree()){
                return 1.0;
            }
        } catch (Exception e) {}
        return 0.0;
    }

    /**
     * Clears all the arguments that may be below this command
     */
    public void clearArgs() {
        arguments.clear();
    }
}
