package slogo.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class ExceptionFeedback {

  public static final int MESSAGE_LINE_LENGTH = 200;

  public enum ExceptionType {
    PARSER_EXCEPTION,
    LOGIC_EXCEPTION,
    XML_EXCEPTION,
    GUI_EXCEPTION,
    RESOURCE_EXCEPTION,
    INPUT_EXCEPTION
  }

  public static void throwException(ExceptionType e, String message) {
    Alert alert = createAlert(e.toString(), message);

    alert.show();
  }

  private static Alert createAlert(String header, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setHeaderText(header);

    StringBuilder sb = new StringBuilder(message);
    for (int i = 0; i < message.length(); i += MESSAGE_LINE_LENGTH) {
      sb.insert(i, "\n");
    }

    Label t = new Label(sb.toString());
    alert.getDialogPane().setContent(t);
    return alert;
  }

}
