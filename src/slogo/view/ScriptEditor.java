package slogo.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import slogo.model.Parser;

public class ScriptEditor extends GridPane {

  private static final double PADDING = 5;
  private static final double GAP = 2;

  private Parser myParser;
  private TextArea input;
  private HBox myButtons;

  public ScriptEditor(Parser parser) {
    myParser = parser;
    initializeInput();
    initializeButtons();
    initializeLayoutPane();
  }

  private void initializeInput() {
    input = new TextArea();
  }

  private void initializeButtons() {
    myButtons = new HBox();
    myButtons.setSpacing(GAP);

    Button runButton = new Button();
    runButton.setText("Run");
    runButton.setOnAction(e -> myParser.parse(input.getText()));

    myButtons.getChildren().add(runButton);
  }

  private void initializeLayoutPane() {
    this.setPadding(new Insets(PADDING));
    this.setVgap(GAP);
    this.setHgap(GAP);

    this.add(input, 0, 1);
    setGrowPriority(input);

    this.add(myButtons, 0, 0);

    setGrowPriority(this);

    this.setBorder(new Border(new BorderStroke(Color.RED,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

  }

  private void setGrowPriority(Node node) {
    GridPane.setHgrow(node, Priority.ALWAYS);
    GridPane.setVgrow(node, Priority.ALWAYS);
  }

}
