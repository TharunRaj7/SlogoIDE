package slogo.view.element;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javax.swing.GroupLayout.Alignment;
import javax.xml.crypto.Data;
import org.w3c.dom.Element;
import slogo.view.utility.XMLBuilder;

public class VariableExplorer extends GuiElement {

  private static final int MIN_WIDTH = 150;
  private static VariableExplorer v;

  private TableView myVariableTable;


  public VariableExplorer() {
    initializeTable();
  }

  private void initializeTable() {
    v = this;
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
    variableNames.setCellValueFactory(
            new PropertyValueFactory<DataModel,String>("variableName")
    );
    TableColumn variableValues = new TableColumn("Value");
    variableValues.setCellValueFactory(
            new PropertyValueFactory<DataModel,String>("variableValue")
    );

    myVariableTable.getColumns().addAll(variableNames, variableValues);

    setGrowPriorityAlways(myVariableTable);

    Label title = new Label("Variable Explorer");
    GridPane.setHalignment(title, HPos.CENTER);

    this.add(title, 0, 0);
    this.add(myVariableTable, 0, 1);
    this.setMinWidth(MIN_WIDTH);
  }

  public class DataModel{

    private final SimpleStringProperty variableName;
    private final SimpleStringProperty variableValue;

     public DataModel (String variableName, double value){
       this.variableName = new SimpleStringProperty(variableName);
       this.variableValue = new SimpleStringProperty(Double.toString(value));
     }
    public String getVariableName() {
      return variableName.get();
    }

    public SimpleStringProperty variableNameProperty() {
      return variableName;
    }

    public String getVariableValue() {
      return variableValue.get();
    }

    public SimpleStringProperty variableValueProperty() {
      return variableValue;
    }
  }

  public static void addTableData(Map<String, Double> variables){
    final ObservableList<DataModel> data = FXCollections.observableArrayList();
    v.makeTable(data, variables);
  }

  private void makeTable(ObservableList<DataModel> data, Map<String, Double> variables) {
    for (Map.Entry<String,Double> entry : variables.entrySet()){
        data.add(new DataModel(entry.getKey().substring(1), entry.getValue().floatValue()));
    }
    myVariableTable.setItems(data);
  }


  @Override
  public void updateResources(ResourceBundle resources) {

  }

  @Override
  public Element toXMLElement() {
    XMLBuilder xmlBuilder = XMLBuilder.newInstance();
    Element root = xmlBuilder.createElement(this.getClass().getSimpleName());

    return root;
  }

}
