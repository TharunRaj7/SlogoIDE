package slogo.view.element;

import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javax.swing.GroupLayout.Alignment;

public class VariableExplorer extends GuiElement {

  private static final int MIN_WIDTH = 150;

  private TableView myVariableTable;

  public VariableExplorer() {
    initializeTable();
  }

  private void initializeTable() {
    myVariableTable = new TableView() {
      @Override
      public void resize(double width, double height) {
        super.resize(width, height);
        float size = getColumns().size();
        for (Object tc : getColumns()) {
          if (tc instanceof TableColumn) {
            ((TableColumn) tc).setMinWidth(getLayoutBounds().getWidth() / size);
            ((TableColumn) tc).setMaxWidth(getLayoutBounds().getWidth() / size);
          }
        }
      }
    };

    TableColumn variableNames = new TableColumn("Name");
    TableColumn variableValues = new TableColumn("Value");

    myVariableTable.getColumns().addAll(variableNames, variableValues);

    setGrowPriorityAlways(myVariableTable);

    Label title = new Label("Variable Explorer");
    GridPane.setHalignment(title, HPos.CENTER);

    this.add(title, 0, 0);
    this.add(myVariableTable, 0, 1);
    this.setMinWidth(MIN_WIDTH);
  }

  @Override
  public void updateResources(ResourceBundle resources) {
    // TODO : add me
  }

}
