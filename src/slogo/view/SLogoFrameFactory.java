package slogo.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import slogo.model.Parser;

public class SLogoFrameFactory {

  private static final int PADDING = 5;
  private static final int GAP = 2;

  static SplitPane newSLogoFramePane() {
    SplitPane topRow = new SplitPane();
    SplitPane botRow = new SplitPane();

    TurtleCanvas tc = new TurtleCanvas();
    testPathDrawing(tc);

    topRow.getItems().add(tc);
    botRow.getItems().add(new Console(new Parser()));
    topRow.getItems().add(new ScriptEditor(new Parser()));

    SplitPane sp = new SplitPane();
    sp.setOrientation(Orientation.VERTICAL);
    sp.setPadding(new Insets(PADDING));
    sp.getItems().addAll(topRow, botRow);

    return sp;
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
