package slogo.commands;


import slogo.controller.TurtleController;
import slogo.view.ExceptionFeedback;

import java.util.ArrayList;


public class BlockCommand implements ICommand {

    private ArrayList<ICommand> myCommands = new ArrayList<>();
    private ArrayList<Double> myReturnVals = new ArrayList<>();
    private double returnValue;
    private Node recent_node = null;

    public BlockCommand(){
        // Shouldn't do anything
    }

    public BlockCommand(TurtleController turtleController) {
        // Shouldn't do anything
    }

    @Override
    public boolean enoughArgs() {
        return true;
    }

    @Override
    public void setArgument(ICommand command) {
        myCommands.add(command);
    }

    @Override
    public void execute() {
        Manager blockManager = new Manager();
        for (ICommand command : myCommands) {
            blockManager.addCommand(command);
        }
        returnValue = myCommands.get(0).returnVal();
        for (ICommand command : myCommands) {
            myReturnVals.add(command.returnVal());
                command.clearArgs();
        }
    }

    @Override
    public double returnVal() {
        return returnValue;
    }

    @Override
    public void clearArgs() {
        // Shouldn't do anything
    }


    public Variables getVar (int index) {
        if (myCommands.get(index) instanceof Variables) {
            return (Variables) myCommands.get(index);
        } else { return null; }
    }

    protected double getRetVals (int index) {
        return myReturnVals.get(index);
    }

    protected int argSize(){
        return myCommands.size();
    }


    protected boolean checkTree(){
        ArrayList<ICommand> myCommandsCopy = new ArrayList<>();

        for (ICommand command : myCommands){
            try{
                if (!((BlockCommand) command).checkTree()){
                    return false;
                }
            }catch (Exception e){
                ExceptionFeedback.throwException(ExceptionFeedback.ExceptionType.INPUT_EXCEPTION,"Wrong input");
            }
            myCommandsCopy.add(command.copy(command));
        }

        for (ICommand command : myCommandsCopy) {
            tree_traverse(command);
        }

        return recent_node == null;
    }

    private void tree_traverse(ICommand command){
        Node command_node = new Node(command);
        if (recent_node == null) {
            recent_node = command_node;
            if (recent_node.getData().enoughArgs()) {
                recent_node = null;
            }
        }

        else {
            recent_node.addChild(command_node);
            recent_node.getData().setArgument(command);
            recent_node = command_node;
            while (recent_node.getData().enoughArgs() && recent_node.getParent() != null) {
                recent_node = recent_node.getParent();
            }
            if (recent_node.getData().enoughArgs() && recent_node.getParent() == null) {
                recent_node = null;
            }
        }
    }

}
