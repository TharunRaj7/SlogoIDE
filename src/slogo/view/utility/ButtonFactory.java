package slogo.view.utility;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonFactory {

  /**
   * Creates a Button with the provided name and on-click action.
   *
   * @param nameProperty the button text
   * @param onClick EventHandler to call on click
   * @return new Button
   */
  public static Button button(String nameProperty, EventHandler<ActionEvent> onClick) {
    Button b = new Button();
    b.setText(nameProperty);
    b.setOnAction(onClick);
    return b;
  }

}
