package slogo.view.element;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public abstract class GuiElement extends GridPane {

  static final double PADDING = 5;
  static final double GAP = 2;

  GuiElement() {
    initializeLayout();
  }

  private void initializeLayout() {
    this.setPadding(new Insets(PADDING));
    this.setVgap(GAP);
    this.setHgap(GAP);

    setGrowPriorityAlways(this);
  }

  void setGrowPriorityAlways(Node node) {
    GridPane.setHgrow(node, Priority.ALWAYS);
    GridPane.setVgrow(node, Priority.ALWAYS);
  }

  public void updateResources(ResourceBundle resources) {
    // do nothing by default
  }

}
