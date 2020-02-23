package slogo.view;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import slogo.model.Parser;

public class Console extends Pane {

  private static final double DEFAULT_WIDTH = 300;
  private static final double DEFAULT_HEIGHT = 600;

  private static final double PADDING = 5;
  private static final double GAP = 2;

  private Parser myParser;
  private List<String> myHistory;
  private TextArea myHistoryArea;
  private int myHistoryPointer;

  private TextField myTextField;

  Console(Parser parser) {
    myParser = parser;
    initializeHistory();
    initializeTextField();
    initializeLayoutPane();
  }

  private void initializeLayoutPane() {
    GridPane myLayoutPane = new GridPane();

    myLayoutPane.setPadding(new Insets(PADDING));
    myLayoutPane.setVgap(GAP);
    myLayoutPane.setHgap(GAP);

    myLayoutPane.add(myHistoryArea, 0, 0);
    myLayoutPane.add(myTextField, 0, 1);

    this.getChildren().add(myLayoutPane);
  }

  private void initializeHistory() {
    myHistory = new ArrayList<>();
    myHistoryPointer = 0;

    myHistoryArea = new TextArea() {
      @Override
      public void requestFocus() {
        myTextField.requestFocus();
      }
    };
    myHistoryArea.setEditable(false);
  }

  private void initializeTextField() {
    myTextField = new TextField();
    myTextField.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
      if (e.getCode() == KeyCode.ENTER) {
        handleInput(myTextField.getText());
      }
      else if (e.getCode() == KeyCode.UP) {
        moveHistoryPointer(-1);
      }
      else if (e.getCode() == KeyCode.DOWN) {
        moveHistoryPointer(1);
      }
    });
  }

  /**
   * Moves the history pointer by 1 in the specified direction:
   * Negative direction === previous command executed
   * Positive direction === next command executed
   * @param direction the direction to move
   */
  private void moveHistoryPointer(int direction) {
    myHistoryPointer += Math.signum(direction);

    if (myHistoryPointer >= myHistory.size()) {
      myHistoryPointer = myHistory.size();
      myTextField.clear();
    } else {
      if (myHistoryPointer < 0) {
        myHistoryPointer = 0;
      }
      if (!myHistory.isEmpty()) {
        myTextField.setText(myHistory.get(myHistoryPointer));
      }
    }
  }

  private void handleInput(String text) {
    myTextField.clear();
    updateHistory(text);
    myParser.parse(text);
  }

  private void updateHistory(String text) {
    myHistory.add(text);
    myHistoryPointer = myHistory.size();

    myHistoryArea.appendText("> " + text + "\n");
  }

  @Override
  public void requestFocus() {
    super.requestFocus();
    this.myTextField.requestFocus();
  }
}
