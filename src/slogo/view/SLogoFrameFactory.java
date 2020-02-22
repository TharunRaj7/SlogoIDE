package slogo.view;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SLogoFrameFactory {

  private static final int PADDING = 5;
  private static final int GAP = 2;

  static Pane newSLogoFramePane() {
    GridPane layoutPane = new GridPane();

    layoutPane.add(new TurtleCanvas(), 0, 0);
    layoutPane.setPadding(new Insets(PADDING));
    layoutPane.setHgap(GAP);
    layoutPane.setVgap(GAP);

    return layoutPane;
  }

}
