package slogo.model;

import slogo.commands.*;
import slogo.controller.TurtleController;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser implements IParse {

    public static final String WHITESPACE = "\\s+";
    public static final String NEWLINE = "\\n+";

    private List<BlockCommand> blockQueue = new ArrayList<>();
    private TurtleController myTurtle;
    private String myLanguage;
    private Manager manager = new Manager();

    public Parser(TurtleController turtle, String language) {
        myLanguage = language;
        myTurtle = turtle;
    }

    /**
     * Sets input to a private local string to be able to manage regex and
     * other aspects from there
     * @param input
     * @throws syntaxException
     * @throws ParserException
     */
    public void parse(String input) {

        ProgramParser lang = new ProgramParser();
        String commentLess = "";

        lang.addPatterns(myLanguage);
        lang.addPatterns("Syntax");

        List<String> lines = Arrays.asList(input.split(NEWLINE));
        for(String line : lines) {
            //System.out.println(line);
            line = line.split("#")[0];
            if(line.isEmpty()) { lines.remove(line); }
            //System.out.println(line);
            line.trim();
            commentLess = commentLess + " " + line;
        }

        //System.out.println(commentLess);
        parseText(lang, Arrays.asList(commentLess.split(WHITESPACE)));

    }

    /**
     * Passes the instance of the turtle to the parser to then send to the commands
     * @param turtle
     */
    public void giveTurtle(TurtleController turtle) { myTurtle = turtle; }

    public void updateLanguage(String language) {
        myLanguage = language;
    }

    private void parseText (ProgramParser lang, List<String> lines) {
        for (String line : lines) {
            if (line.trim().length() > 0) {
                if (lang.getSymbol(line).equals("ListStart")) {
                    startList();
                } else if (lang.getSymbol(line).equals("ListEnd")) {
                    endList();
                } else if (lang.getSymbol(line).equals("Constant")) {
                    addConst(line);
                } else if (lang.getSymbol(line).equals("Variable")) { giveVariable(line); }
                else {
                    //System.out.println(lang.getSymbol(line));
                    makeCommand(myTurtle, "slogo.commands." + lang.getSymbol(line));
                }
            }
        }
        //System.out.println();
    }

    /**
     * Instantiates command to send to send to the manager
     * @param turtle
     */
    public void makeCommand(TurtleController turtle, String commandType) {
        try {
            Class<?> cls = Class.forName(commandType);
            Object command;
            Constructor constructor = cls.getConstructor(TurtleController.class);
            command = constructor.newInstance(turtle);
            ICommand returnCommand = (ICommand) command;
            if(blockQueue.size() != 0) {
                blockQueue.get(blockQueue.size() - 1).setArgument(returnCommand);
            } else {
                manager.addCommand(returnCommand);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            Name name = new Name(turtle, commandType);
            ToManager toManager = new ToManager(turtle);

            if(toManager.isInMap(name) && !toManager.isOverwrite()) {
                //System.out.println("Got in Name if statement");
                toManager.execute2(name);
                manager.addCommand(toManager);
            } else {
                //System.out.println("Did not get in Name if statement");
                manager.addCommand(name);
            }
        }
    }

    private void startList() {
        //System.out.println("List begins");
        blockQueue.add(new BlockCommand());
    }

    private void endList() {
        //System.out.println("List ends");
        if(blockQueue.size() == 1) {
            manager.addCommand(blockQueue.get(0));
        } else {
            blockQueue.get(blockQueue.size() - 2).setArgument(blockQueue.get(blockQueue.size() - 1));
        }
        blockQueue.remove(blockQueue.size() - 1);
    }

    private void addConst(String line) {
        //System.out.println(line);
        if(blockQueue.size() != 0) {
            blockQueue.get(blockQueue.size() - 1).setArgument(new Argument(Float.parseFloat(line)));
        } else {
            manager.addCommand(new Argument(Float.parseFloat(line)));
        }
    }

    private void giveVariable(String varName) {
        if (blockQueue.size() != 0) {
            //System.out.println("Adding " + varName + " to the block");
            blockQueue.get(blockQueue.size() - 1).setArgument(new Variables(varName, myTurtle));
        } else {
            //System.out.println(varName);
            manager.addCommand(new Variables(varName, myTurtle));
        }
    }
}
