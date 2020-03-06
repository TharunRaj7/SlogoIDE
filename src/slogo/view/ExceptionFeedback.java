package slogo.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class ExceptionFeedback {

  public enum ExceptionType {
    PARSER_EXCEPTION,
    LOGIC_EXCEPTION,
    XML_EXCEPTION,
    GUI_EXCEPTION,
    RESOURCE_EXCEPTION
  }

  public static void throwException(ExceptionType e, String message) {
    Alert alert = createAlert(AlertType.ERROR, e.toString(), message);

    alert.show();
  }

  private static Alert createAlert(Alert.AlertType type, String header, String message) {
    Alert alert = new Alert(type);
    alert.setHeaderText(header);

    StringBuilder sb = new StringBuilder(message);
    for (int i = 0; i < message.length(); i += 200) {
      sb.insert(i, "\n");
    }

    Label t = new Label(sb.toString());
    alert.getDialogPane().setContent(t);
    return alert;
  }

}
