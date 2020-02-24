package slogo.view.element;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public interface IVisualize {

  /**
   * Updates the visualization with a provided path.
   *
   * Adds a new path to the canvas's drawing, representing a single turtle movement.
   * @param p the Path to draw
   */
  void drawPath(Path p);

  /**
   * Sets the color of the turtle's pen.
   *
   * Changes the color of future (not past) paths that are drawn by the turtle's movement.
   * @param c the new Color
   */
  void setPenColor(Color c);

  /**
   * Updates the turtle visual on the display to set the angle of the new heading of the turtle.
   * @param angle of the rotation
   */
  void setHeading(int angle);

  /**
   * Sets the thickness of the turtle's pen.
   *
   * Changes the thickness of future (not past) paths that are drawn by the turtle's movement.
   * @param thickness the new thickness, in pixels
   */
  void setPenThickness(int thickness);

  /**
   * Sets the background color of the visualization.
   *
   * @param c the new background Color
   */
  void setBackgroundColor(Color c);

  /**
   * Deletes all previously drawn paths, clearing the canvas.
   */
  void clear();

  /**
   * Gets the bounds of this visualization.
   */
  Bounds getBounds();

}
