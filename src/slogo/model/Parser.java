package slogo.model;

import slogo.commands.ICommand;
import slogo.controller.Turtle;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser implements IParse {
    public static final String WHITESPACE = "\\s+";
    Turtle myTurtle;

    public Parser(Turtle turtle) {
        myTurtle = turtle;
    }

    /**
     * Sets input to a private local string to be able to manage regex and
     * other aspects from there
     * @param input
     * @throws syntaxException
     * @throws parserException
     */
    public void parse(String input) {
        // TODO: Get the important information from the input String
        // TODO: Assign the necessary values
        // TODO: Make the necessary commands
        // TODO: This is the hard part
        // TODO: Make sure to not let this method get out of control
        ArrayList<String> instructions = new ArrayList<>();
        String[] lines = input.split("\n");
        for(String line : lines) {
            String[] comm = line.split(" ");
            for(String command : comm) {
                instructions.add(command);
            }
        }

    }

    /**
     * Passes the instance of the turtle to the parser to then send to the commands
     * @param turtle
     */
    public void giveTurtle(Turtle turtle) {
        myTurtle = turtle;
    }

    /**
     * Instantiates command to send to send to the manager
     * @param turtle
     */
    public ICommand makeCommand(Turtle turtle) {
        // TODO: Make a command that can be passed to the manager
        // TODO: Should be able to handle zero arguments
        return null;
    }
}
