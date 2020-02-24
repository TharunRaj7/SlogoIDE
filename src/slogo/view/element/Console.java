package slogo.view.element;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import slogo.model.Parser;

public class Console extends GuiElement {

  private Parser myParser;
  private List<String> myHistory;
  private TextArea myHistoryArea;
  private int myHistoryPointer;

  private TextField myTextField;

  public Console(Parser parser) {
    myParser = parser;
    initializeHistory();
    initializeTextField();
    initializeLayout();
  }

  private void initializeLayout() {
    this.add(myHistoryArea, 0, 0);
    setGrowPriorityAlways(myHistoryArea);

    this.add(myTextField, 0, 1);
    setGrowPriorityAlways(myTextField);
    GridPane.setVgrow(myTextField, Priority.NEVER);

//    this.setBorder(new Border(new BorderStroke(Color.BLUE,
//        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

//    this.setGridLinesVisible(true);
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

}
