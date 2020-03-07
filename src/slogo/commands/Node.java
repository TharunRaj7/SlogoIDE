package slogo.commands;

import java.util.ArrayList;
import java.util.List;


public class Node {

    private slogo.commands.ICommand data;

    private List<Node> children = new ArrayList<>();

    private Node parent = null;

    public Node(ICommand data) {
        this.data = data;
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChildren(List<Node> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Node> getChildren() {
        return children;
    }

    public ICommand getData() {
        return data;
    }

    public void setData(ICommand data) {
        this.data = data;
    }

    public Node getParent() {
        return parent;
    }

    private void setParent(Node parent) {
        this.parent = parent;
    }


}