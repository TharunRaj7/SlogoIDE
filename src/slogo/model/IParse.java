package slogo.model;

import slogo.controller.Turtle;

public interface IParse {

  /**
   * Sets input to a private local string to be able to manage regex and
   * other aspects from there
   * @param input
   */
  void parse(String input);

  /**
   * Passes the instance of the turtle to the
   * @param turtle
   */
  void giveTurtle(Turtle turtle);

}
