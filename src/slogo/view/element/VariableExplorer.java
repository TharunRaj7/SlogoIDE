package slogo.view.element;

import java.util.ResourceBundle;
import javafx.scene.control.TableView;

public class VariableExplorer extends GuiElement {

  private TableView myVariableTable;

  public VariableExplorer() {
    initializeLayout();
  }

  private void initializeLayout() {
    myVariableTable = new TableView();

    this.add(myVariableTable, 0, 0);
  }

  @Override
  public void updateResources(ResourceBundle resources) {
    // TODO : add me
  }

}
