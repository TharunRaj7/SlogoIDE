package slogo.model;

import slogo.commands.ICommand;
import slogo.commands.Manager;
import slogo.controller.Turtle;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser implements IParse {
    public static final String WHITESPACE = "\\s+";
    private Turtle myTurtle;
    private Manager manager = new Manager();

    public Parser(Turtle turtle) {
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
        // TODO: Get the important information from the input String
        // TODO: Assign the necessary values
        // TODO: Make the necessary commands
        // TODO: This is the hard part
        // TODO: Make sure to not let this method get out of control

        ProgramParser lang = new ProgramParser();

        lang.addPatterns("English");
        lang.addPatterns("Chinese");
        lang.addPatterns("French");
        lang.addPatterns("German");
        lang.addPatterns("Italian");
        lang.addPatterns("Portuguese");
        lang.addPatterns("Russian");
        lang.addPatterns("Spanish");
        lang.addPatterns("Urdu");
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
            return returnCommand;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ParserException(e);
        }
    }

    // given some text, prints results of parsing it using the given language
    private void parseText (ProgramParser lang, List<String> lines) {
        for (String line : lines) {
            if (line.trim().length() > 0) {
                if (lang.getSymbol(line) == "Constant") { giveArgument(Float.parseFloat(line)); }
                else if (lang.getSymbol(line) == "Variable") { giveVariable(line, 0); }
                else { makeCommand(myTurtle, lang.getSymbol(line)); }
            }
        }
        System.out.println();
    }

    private void giveArgument(double arg) { manager.addArg(arg); }

    private void giveVariable(String varName, double arg) { manager.addVariable(varName, arg); }
}
