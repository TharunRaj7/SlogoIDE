package slogo.model;

import slogo.commands.ICommand;
import slogo.controller.Turtle;

public interface IParse {

  /**
   * Sets input to a private local string to be able to manage regex and
   * other aspects from there
   * @param input
   * @throws syntaxException
   * @throws parserException
   */
  void parse(String input);

  /**
   * Passes the instance of the turtle to the parser to then send to the commands
   * @param turtle
   */
  void giveTurtle(Turtle turtle);

  /**
   * Instantiates command to send to send to the manager
   * @param turtle
   */
  ICommand makeCommand(Turtle turtle);

}
