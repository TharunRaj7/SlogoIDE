package slogo.view.element;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import slogo.model.Parser;
import slogo.view.utility.ButtonFactory;

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

    Button runButton = ButtonFactory.button(
        "Run", e -> myParser.parse(input.getText())
    );

    Button openButton = ButtonFactory.button(
        "Open", e -> System.out.println("open")
    );

    myButtons.getChildren().add(runButton);
    myButtons.getChildren().add(openButton);
  }

  private void initializeLayout() {
    this.add(input, 0, 1);
    setGrowPriorityAlways(input);

    this.setMinWidth(MIN_WIDTH);

    this.add(myButtons, 0, 0);
  }

}
