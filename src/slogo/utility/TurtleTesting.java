package slogo.utility;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import slogo.view.element.TurtleCanvas;

public class TurtleTesting {

  public static void testPathDrawing(TurtleCanvas tc) {
    Path p = new Path();
    PathElement line;
    line = new LineTo(-50, 50);
    p.getElements().add(line);
    line = new LineTo(50, 50);
    line.setAbsolute(false);
    p.getElements().add(line);
    line = new LineTo(50,50);
    line.setAbsolute(true);
    p.getElements().add(line);
    tc.drawPath(p);
  }

}
