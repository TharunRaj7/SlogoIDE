package slogo.commands;

import slogo.controller.Turtle;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager implements IManager{
    private Node recent_node = null;
    private HashMap<String,Double> variables = new HashMap<>();
    private ArrayList<String> current_variables = new ArrayList<>();

    public Manager () {
        // TODO: Initialize command tree, hold onto it as a private variable
    }

    /**
     * Adds a command to the command tree to be executed
     * @param command
     */
    public void addCommand(ICommand command) {
        // TODO: Add a command to the most recent node on the TreeList, check if the command has enough args to run
        // TODO: Set that command to the current node
        Node command_node = new Node(command);
        if (recent_node == null){
            recent_node = command_node;
            if (recent_node.getData().enoughArgs()) {
                runCommand(recent_node.getData());
                recent_node = null;
            }
        }
        else {
            recent_node.addChild(command_node);
            recent_node.getData().setArgument(command);
            recent_node = command_node;
            while (recent_node.getData().enoughArgs() && recent_node.getParent() != null) {
                //double return_val = runCommand(recent_node.getData());
                //recent_node.getParent().getData().setArgument(return_val);
                recent_node = recent_node.getParent();
            }
            if (recent_node.getData().enoughArgs() && recent_node.getParent() == null){
                //runCommand(recent_node.getData());
                //recent_node = null;
            }
            else{
                System.out.println("Not enough commands/arguments!");
            }

        }
    }

    /**
     * Adds an argument to the leaf of the current command
     * Should check the command it's under if the args
     * are satisfied to execute
     * @param arg
     */
    public void addArg(double arg) {
        // TODO: Add a value as a leaf to the most recent command
        // TODO: Check if that argument is enough for the command to run. If so, run it

        /*recent_node.getData().setArgument(arg);
        while (recent_node.getData().enoughArgs() && recent_node.getParent() != null){
            double return_val = runCommand(recent_node.getData());
            recent_node.getParent().getData().setArgument(return_val);
            recent_node = recent_node.getParent();
        }

        if (recent_node.getData().enoughArgs() && recent_node.getParent() == null){
            runCommand(recent_node.getData());
            recent_node = null;
        }

        else{
            System.out.println("Not enough commands/arguments!");
        }

         */

    }

    /**
     * Adds a variable to the map held in the manager
     * Able to be recalled as an argument for command calls later
     * @param name
     */
    public void addVariable(String name) {
        // TODO: Add the variable given the string name and value to the map of variables
        if (recent_node.getData().getClass().isInstance(new Make())){
            current_variables.add(name);
            variables.putIfAbsent(name,0.0);
        }
        else{
            addArg(variables.get(name));
        }
    }

    /**
     * Runs the command with the arguments, and returns a double to
     * be used as another argument or to be passed to a variable
     * @param command
     * @return val for argument or variable
     */
    public double runCommand(ICommand command) {
        command.execute();
        if (current_variables.size() > 0){
            variables.put(current_variables.get(-1),command.returnVal());
            current_variables.remove(-1);
        }
        return command.returnVal();
    }
}
