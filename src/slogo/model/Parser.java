package slogo.model;

import slogo.commands.Argument;
import slogo.commands.ICommand;
import slogo.commands.Manager;
import slogo.controller.Turtle;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class Parser implements IParse {
    public static final String WHITESPACE = "\\s+";
    private Turtle myTurtle;
    private String myLanguage;
    private Manager manager = new Manager();

    public Parser(Turtle turtle, String language) {
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

        lang.addPatterns(myLanguage);
        lang.addPatterns("Syntax");

        parseText(lang, Arrays.asList(input.split(WHITESPACE)));

    }

    /**
     * Passes the instance of the turtle to the parser to then send to the commands
     * @param turtle
     */
    public void giveTurtle(Turtle turtle) { myTurtle = turtle; }

    /**
     * Instantiates command to send to send to the manager
     * @param turtle
     */
    public ICommand makeCommand(Turtle turtle, String commandType) {
        try {
            Class<?> cls = Class.forName(commandType);
            Object command;
            Constructor constructor = cls.getConstructor(Turtle.class);
            command = constructor.newInstance(turtle);
            ICommand returnCommand = (ICommand) command;
            manager.addCommand(returnCommand);
            return returnCommand;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ParserException(e);
        }
    }

    // given some text, prints results of parsing it using the given language
    private void parseText (ProgramParser lang, List<String> lines) {
        for (String line : lines) {
            if (line.trim().length() > 0) {
                if (lang.getSymbol(line).equals("Constant")) {
                    System.out.println(line);
                    manager.addCommand(new Argument(Float.parseFloat(line)));
                }
                else if (lang.getSymbol(line).equals("Variable")) { giveVariable(line, 0); }
                else {
                    System.out.println(lang.getSymbol(line));
                    makeCommand(myTurtle, "slogo.commands." + lang.getSymbol(line));
                }
            }
        }
        System.out.println();
    }

    private void giveArgument(double arg) { manager.addArg(arg); }

    private void giveVariable(String varName, double arg) { manager.addVariable(varName); }
}
