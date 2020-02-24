package slogo.view.element;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import slogo.model.Parser;

public class ScriptEditor extends GuiElement {

  private static final double MIN_WIDTH = 200;

  private Parser myParser;
  private TextArea input;
  private HBox myButtons;

  public ScriptEditor(Parser parser) {
    myParser = parser;
    initializeInput();
    initializeButtons();
    initializeLayout();
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

  private void initializeLayout() {
    this.add(input, 0, 1);
    setGrowPriorityAlways(input);

    this.setMinWidth(MIN_WIDTH);

    this.add(myButtons, 0, 0);
  }

}
