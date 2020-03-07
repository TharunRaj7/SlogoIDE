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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javax.swing.GroupLayout.Alignment;
import javax.xml.crypto.Data;
import org.w3c.dom.Element;
import slogo.controller.Turtle;
import slogo.controller.TurtleController;
import slogo.view.utility.XMLBuilder;

public class VariableExplorer extends GuiElement {

  private static final int MIN_WIDTH = 150;

  private TableView myVariableTable;
  private Map<String, Double> currentVariableMap;
  private TurtleController turtleController;
  private ResourceBundle myResources;

  private Label myTitle;
  private TableColumn myVariableNames;
  private TableColumn myVariableValues;


  public VariableExplorer(TurtleController turtleController, ResourceBundle resources) {
    myResources = resources;
    initializeTable();
    this.turtleController = turtleController;
    turtleController.giveVariableExplorer(this);
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

    initializeTableColumns();

    setGrowPriorityAlways(myVariableTable);

    myTitle = new Label(myResources.getString("variableExplorer"));
    GridPane.setHalignment(myTitle, HPos.CENTER);

    this.add(myTitle, 0, 0);
    this.add(myVariableTable, 0, 1);
    this.setMinWidth(MIN_WIDTH);
  }

  private void initializeTableColumns() {
    myVariableNames = new TableColumn(myResources.getString("name"));
    myVariableNames.setCellValueFactory(
            new PropertyValueFactory<DataModel,String>("variableName")
    );
    myVariableValues = new TableColumn(myResources.getString("value"));
    myVariableValues.setCellValueFactory(
            new PropertyValueFactory<DataModel,String>("variableValue")
    );
    myVariableValues.setCellFactory(TextFieldTableCell.forTableColumn());
    myVariableValues.setOnEditCommit(event -> {
      CellEditEvent<DataModel, Object> e = (CellEditEvent<DataModel, Object>) event;
      //System.out.println(e.getNewValue());
      DataModel data = e.getTableView().getItems().get(e.getTablePosition().getRow());
      myVariableTable.getItems().remove(data);
      currentVariableMap.put(":" + data.getVariableName(), Double.parseDouble((String)e.getNewValue()));
      myVariableTable.getItems().add(new DataModel(data.getVariableName(), Double.parseDouble((String)e.getNewValue())));
      //System.out.println(Double.parseDouble(data.getVariableValue()));
    });

    myVariableTable.getColumns().addAll(myVariableNames, myVariableValues);
    myVariableTable.setEditable(true);
  }

  public class DataModel{

    private SimpleStringProperty variableName;
    private SimpleStringProperty variableValue;

    DataModel(String variableName, double value){
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

  public void addTableData(Map<String, Double> variables){
    this.currentVariableMap = variables;
    final ObservableList<DataModel> data = FXCollections.observableArrayList();
    makeTable(data, variables);
  }

  private void makeTable(ObservableList<DataModel> data, Map<String, Double> variables) {
    for (Map.Entry<String,Double> entry : variables.entrySet()){
        data.add(new DataModel(entry.getKey().substring(1), entry.getValue().floatValue()));
    }
    myVariableTable.setItems(data);
  }

  /**
   * Updates the ResourceBundle being used.
   * @param resources the new ResourceBundle
   */
  @Override
  public void updateResources(ResourceBundle resources) {
    myResources = resources;
    myTitle.setText(myResources.getString("title"));
    myVariableNames.setText(myResources.getString("name"));
    myVariableValues.setText(myResources.getString("value"));
  }

  @Override
  public Element toXMLElement() {
    XMLBuilder xmlBuilder = XMLBuilder.newInstance();

    return xmlBuilder.createElement(this.getClass().getSimpleName());
  }

  @Override
  public void setContentsFromXMLElement(Element element) {
    // do nothing
  }

}
