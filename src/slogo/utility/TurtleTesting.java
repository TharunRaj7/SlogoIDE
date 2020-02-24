package slogo.utility;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import slogo.view.element.TurtleCanvas;

public class TurtleTesting {

  public static void testPathDrawing(TurtleCanvas tc) {
    Path p = new Path();
    PathElement line;

    p.getElements().add(new MoveTo(0, 0));
    line = new LineTo(-150, 50);
    line.setAbsolute(false);
    p.getElements().add(line);

    tc.drawPath(p);

    p = new Path();
    p.getElements().add(new MoveTo(0, 0));
    line = new LineTo(150, 50);
    line.setAbsolute(false);
    p.getElements().add(line);

    tc.drawPath(p);

    p = new Path();
    p.getElements().add(new MoveTo(0, 0));
    line = new LineTo(50, 150);
    line.setAbsolute(false);
    p.getElements().add(line);

    tc.drawPath(p);

    p = new Path();
    p.getElements().add(new MoveTo(0, 0));
    line = new LineTo(50, -150);
    line.setAbsolute(false);
    p.getElements().add(line);


    tc.drawPath(p);
  }

}
