package slogo.commands;

/**
 * @author Andrew Krier
 * @author Vineet Alaparthi
 */
public class Manager implements IManager {

    private Node recent_node = null;

    public Manager () {
        // Shouldn't do anything
    }

    /**
     * Adds a command to the command tree to be executed
     * @param command
     */
    public void addCommand(ICommand command) {

        Node command_node = new Node(command);
        if (recent_node == null){
            firstNode(command_node);
        }
        else {
            addNode(command_node, command);
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
        return command.returnVal();
    }

    private void firstNode (Node command_node) {
        recent_node = command_node;
        if (recent_node.getData().enoughArgs()) {
            runCommand(recent_node.getData());
            recent_node = null;
        }
    }

    private void addNode (Node command_node, ICommand command) {
        recent_node.addChild(command_node);
        recent_node.getData().setArgument(command);
        recent_node = command_node;
        while (recent_node.getData().enoughArgs() && recent_node.getParent() != null) {
            recent_node = recent_node.getParent();
        }
        if (recent_node.getData().enoughArgs() && recent_node.getParent() == null){
            runCommand(recent_node.getData());
            recent_node = null;
        }
    }
}
