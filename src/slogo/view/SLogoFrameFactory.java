package slogo.view;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import slogo.controller.Turtle;
import slogo.model.Parser;

public class SLogoFrameFactory {

  private static final int PADDING = 5;
  private static final int GAP = 2;

  static Pane newSLogoFramePane() {
    GridPane layoutPane = new GridPane();

    TurtleCanvas tc = new TurtleCanvas();
    testPathDrawing(tc);

    layoutPane.add(tc, 0, 0);
    layoutPane.add(new Console(new Parser()), 1, 0);

    layoutPane.setPadding(new Insets(PADDING));
    layoutPane.setHgap(GAP);
    layoutPane.setVgap(GAP);

    return layoutPane;
  }

  private static void testPathDrawing(TurtleCanvas tc) {
    Path p = new Path();
    PathElement line;
    line = new LineTo(-50, 50);
    p.getElements().add(line);
    line = new LineTo(100, 50);
    line.setAbsolute(false);
    p.getElements().add(line);
    tc.drawPath(p);
  }

}
