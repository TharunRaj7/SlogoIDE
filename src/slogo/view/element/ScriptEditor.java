package slogo.view.element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.model.Parser;
import slogo.view.utility.ButtonFactory;

public class ScriptEditor extends GuiElement {

  private static final double MIN_WIDTH = 200;

  private Parser myParser;
  private TextArea input;
  private HBox myButtons;
  private Button mySaveButton;

  private String myFilePath;

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
    addButton("Run", e -> myParser.parse(input.getText()));
    addButton("Open", e -> openFile());
    mySaveButton = ButtonFactory.button("Save", e -> saveFile(myFilePath));
    mySaveButton.setDisable(true);
    myButtons.getChildren().add(mySaveButton);
    addButton("Save As", e -> saveAsFile());
  }

  private void addButton(String nameProperty, EventHandler<ActionEvent> eh) {
    if (myButtons != null) {
      myButtons.getChildren().add(ButtonFactory.button(nameProperty, eh));
    }
  }

  private void initializeLayout() {
    this.add(input, 0, 1);
    setGrowPriorityAlways(input);

    this.setMinWidth(MIN_WIDTH);

    this.add(myButtons, 0, 0);
  }

  private void openFile() {
    FileChooser fc = new FileChooser();
    String dataPath = System.getProperty("user.dir") + "/data";
    File workingDirectory = new File(dataPath);
    fc.setInitialDirectory(workingDirectory);

    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Logo files (*.logo)",
        "*.logo");
    fc.getExtensionFilters().add(extFilter);

    File file = fc.showOpenDialog(new Stage());
    try {
      Scanner scan = new Scanner(file);
      input.clear();
      while (scan.hasNextLine()) {
        input.appendText(scan.nextLine() + "\n");
      }
      scan.close();
      input.requestFocus();
      myFilePath = file.getAbsolutePath();
      mySaveButton.setDisable(false);
    } catch (FileNotFoundException ignored) {}
  }

  private void saveFile(String fileName) {
    try {
      File file = new File(fileName);
      FileWriter fw = new FileWriter(file);

      fw.write(input.getText());
      fw.close();
    } catch (IOException e) {
      // TODO: REMOVE THIS
      e.printStackTrace();
    }
  }

  private void saveAsFile() {
    FileChooser fc = new FileChooser();
    String dataPath = System.getProperty("user.dir") + "/data";
    File workingDirectory = new File(dataPath);
    fc.setInitialDirectory(workingDirectory);

    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Logo files (*.logo)",
        "*.logo");
    fc.getExtensionFilters().add(extFilter);

    File file = fc.showSaveDialog(new Stage());

    if (file != null) {
      saveFile(file.getAbsolutePath());
      mySaveButton.setDisable(false);
    }
  }

}
