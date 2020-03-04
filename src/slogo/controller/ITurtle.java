package slogo.controller;

import javafx.scene.image.ImageView;
import slogo.utility.Location;

public interface ITurtle {

  /**
   * Moves the turtle forward or backward in the direction of its heading by a given distance.
   * <p>
   * This is 'relative movement', in that the turtle's final location is given relative to its
   * current location. Upon calling this method, the turtle will move and update the Canvas
   * accordingly.
   *
   * @param distance the distance to move. Positive distance is forward and negative distance is backward
   */
  void moveRelative(double distance);

  /**
   * Moves the turtle to an absolute location.
   * <p>
   * This is 'absolute movement', since the turtle's final location does not depend on its current
   * location. The turtle jumps to the given location without changing heading, and updates the
   * Canvas with the corresponding line if needed.
   *
   * @param l the Location to move to
   */
  void moveTo(Location l);

  /**
   * Rotates the turtle's heading by a given angle.
   * <p>
   * This is 'relative rotation', which adjusts the turtle's heading by a given offset from its
   * current heading. Positive angles indicate turning right, or clockwise; negative angles turn
   * left, or counter-clockwise. The turtle will visually change heading upon update.
   *
   * @param angle the rotation from the current heading
   */
  void rotate(double angle);

  /**
   * Sets the turtle's heading to a given angle.
   * <p>
   * This is 'absolute rotation', which sets the turtle's heading to a given angle. Angles are
   * measured from 'North', moving clockwise.
   * // TODO : Document this API change, from counter-clockwise to clockwise.
   *
   * @param angle the new heading
   */
  void setHeading(double angle);

  /**
   * Sets the angle of the turtle such that it is heading towards the specified location on the canvas
   *
   * @param x x-component of the point at which the turtle is supposed to head towards
   * @param y y-component of the point at which the turtle is supposed to head towards
   */
  void towards(double x, double y);

  /**
   * Picks up the turtle's 'pen', so that movement is no longer marked on the canvas.
   * <p>
   * If the pen is already up, there is no change.
   */
  void penUp();

  /**
   * Puts down the turtle's 'pen', so that movement is marked on the canvas.
   * <p>
   * If the pen is already down, there is no change.
   */
  void penDown();

  /**
   * Makes the turtle visible on the canvas.
   */
  void show();

  /**
   * Hides the turtle on the canvas.
   */
  void hide();

  /**
   * Clears all previous paths left by the turtle, leaving a blank canvas.
   */
  void clear();

  /**
   * Gets the current location of the turtle.
   *
   * @return the turtle's Location
   */
  Location getLocation();

  /**
   * returns the imageView object of the turtle to be displayed on the canvas.
   *
   * @return the imageView object of the turtle
   */
  ImageView getImage();

  /**
   * Sets the new location of the turtle.
   *
   * @return the turtle's Location
   */
  void setLocation(Location l);

  /**
   * Gets the current heading of the turtle.
   *
   * @return an int, the current heading
   */
  double getHeading();

  /**
   * Gets the pen's current position.
   *
   * @return true if the pen is down, false if up
   */
  boolean getPenDown();

  /**
   * Gets the turtle's visibility.
   *
   * @return true if the turtle is visible, false if not
   */
  boolean getShowing();

  /**
   * @return the id of the turtle
   */
  int getId();
}