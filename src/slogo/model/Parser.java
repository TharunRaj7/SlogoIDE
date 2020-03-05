package slogo.model;

import slogo.commands.*;
import slogo.controller.Turtle;
import slogo.controller.TurtleController;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Parser implements IParse {

    public static final String WHITESPACE = "\\s+";
    public static final String NEWLINE = "\\n+";

    private boolean isBlock = false;
    private Map<String, Double> varList = new HashMap<>();
    private List<BlockCommand> blockCommandQueue = new ArrayList<>();
    private TurtleController myTurtle;
    private String myLanguage;
    private Manager manager = new Manager();
    private BlockCommand myBlockCommand;

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
            System.out.println(line);
            line = line.split("#")[0];
            if(line.isEmpty()) { lines.remove(line); }
            System.out.println(line);
            line.trim();
            commentLess = commentLess + " " + line;
        }

        System.out.println(commentLess);
        parseText(lang, Arrays.asList(commentLess.split(WHITESPACE)));

    }

    /**
     * Passes the instance of the turtle to the parser to then send to the commands
     * @param turtle
     */
    public void giveTurtle(TurtleController turtle) { myTurtle = turtle; }

    /**
     * Instantiates command to send to send to the manager
     * @param turtle
     */
    public ICommand makeCommand(TurtleController turtle, String commandType) {
        try {
            Class<?> cls = Class.forName(commandType);
            Object command;
            Constructor constructor = cls.getConstructor(TurtleController.class);
            command = constructor.newInstance(turtle);
            ICommand returnCommand = (ICommand) command;
            if(blockCommandQueue.size() != 0) { //isBlock) {
                blockCommandQueue.get(blockCommandQueue.size() - 1).setArgument(returnCommand);
            } else {
                manager.addCommand(returnCommand);
            }
            return returnCommand;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ParserException(e);
        }
    }

    // TODO: Clean this code
    // given some text, prints results of parsing it using the given language
    private void parseText (ProgramParser lang, List<String> lines) {
        for (String line : lines) {
            if (line.trim().length() > 0) {
                if (lang.getSymbol(line).equals("ListStart")) {
                    System.out.println("List begins");
                    blockCommandQueue.add(new BlockCommand());
                } else if (lang.getSymbol(line).equals("ListEnd")) {
                    System.out.println("List ends");
                    if(blockCommandQueue.size() == 1) {
                        manager.addCommand(blockCommandQueue.get(0));
                    } else {
                        blockCommandQueue.get(blockCommandQueue.size() - 2).setArgument(blockCommandQueue.get(blockCommandQueue.size() - 1));
                    }
                    blockCommandQueue.remove(blockCommandQueue.size() - 1);
                } else if (lang.getSymbol(line).equals("Constant")) {
                    System.out.println(line);
                    if(blockCommandQueue.size() != 0) {
                        blockCommandQueue.get(blockCommandQueue.size() - 1).setArgument(new Argument(Float.parseFloat(line)));
                    } else {
                        manager.addCommand(new Argument(Float.parseFloat(line)));
                    }
                } else if (lang.getSymbol(line).equals("Variable")) { giveVariable(line); }
                else {
                    System.out.println(lang.getSymbol(line));
                    makeCommand(myTurtle, "slogo.commands." + lang.getSymbol(line));
                }
            }
        }
        System.out.println();
    }

    private void giveVariable(String varName) {
      if (blockCommandQueue.size() != 0) {
          blockCommandQueue.get(blockCommandQueue.size() - 1).setArgument(new Variables(varName));
      } else {
        System.out.println(varName);
        manager.addCommand(new Variables(varName));
      }
    }

    public void updateLanguage(String language) {
        myLanguage = language;
    }
}
