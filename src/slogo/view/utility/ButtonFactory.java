package slogo.view.utility;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonFactory {

  public static Button button(String nameProperty, EventHandler<ActionEvent> onClick) {
    Button b = new Button();
    b.setText(nameProperty);
    b.setOnAction(onClick);
    return b;
  }

}
