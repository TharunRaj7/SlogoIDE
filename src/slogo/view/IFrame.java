package slogo.view;

import javafx.scene.layout.Pane;

interface IFrame {

  /**
   * Adds a new UI panel to this frame.
   *
   * Uses the GridPane layout pane to arrange new Panes inside the Frame.
   *
   * @param pane the Pane to add
   * @param columnIndex the column index at which to add the panel
   * @param rowIndex the row index at which to add the panel
   */
  void addPanel(Pane pane, int columnIndex, int rowIndex);

}
