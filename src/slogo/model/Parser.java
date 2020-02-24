package slogo.model;

import slogo.commands.Forward;
import slogo.commands.ICommand;
import slogo.controller.Turtle;
import slogo.utility.Location;

public class Parser implements IParse {
    Turtle myTurtle;
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
        //String[] lines = input.split("\n");
        String[] lines = input.split(" ");
//        for (String s : lines){
//            System.out.println(s);
//        }
        if (lines[0].equals("fd")){
            myTurtle.moveRelative(Integer.parseInt(lines[1]));
        }
        if (lines[0].equals("rt")){
            myTurtle.rotate(Double.parseDouble(lines[1]));
        }

        if (lines[0].equals("setxy")){
            myTurtle.moveTo(new Location(Double.parseDouble(lines[1]), Double.parseDouble(lines[2])));
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
