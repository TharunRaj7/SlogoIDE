package slogo.view.element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import slogo.controller.TurtleController;
import slogo.model.Parser;
import slogo.view.utility.ButtonFactory;
import slogo.view.utility.XMLBuilder;

public class ScriptEditor extends GuiElement {

  private static final double MIN_WIDTH = 200;
  private static final int BUTTON_CLASS_GAP = 10;

  private Parser myParser;
  private TurtleController turtleController;
  private TextArea input;
  private HBox myButtons;
  private Button mySaveButton;

  private String myFilePath;


  public ScriptEditor(Parser parser, TurtleController turtleController, ResourceBundle resources) {
    myParser = parser;
    this.turtleController = turtleController;
    initializeInput();
    initializeButtons(resources);
    initializeLayout();
    mySaveButton.setDisable(true);
  }

  private void initializeInput() {
    input = new TextArea();
  }

  private void initializeButtons(ResourceBundle resources) {
    myButtons = new HBox();
    myButtons.setSpacing(BUTTON_CLASS_GAP);

    HBox runMenu = buildRunMenu(resources);

    HBox fileMenu = buildFileMenu(resources);

    myButtons.getChildren().add(runMenu);
    myButtons.getChildren().add(fileMenu);
  }

  private HBox buildRunMenu(ResourceBundle resources) {
    HBox runMenu = new HBox();
    runMenu.setSpacing(GAP);

    runMenu.getChildren().add(
        ButtonFactory.button(resources.getString("run"), e -> parseInput()));
    return runMenu;
  }

  private HBox buildFileMenu(ResourceBundle resources) {
    HBox fileMenu = new HBox();
    fileMenu.setSpacing(GAP);

    fileMenu.getChildren().add(
        ButtonFactory.button(resources.getString("open"), e -> openFile()));
    mySaveButton = ButtonFactory.button(resources.getString("save"), e -> saveFile());
    fileMenu.getChildren().add(mySaveButton);

    fileMenu.getChildren().add(
        ButtonFactory.button(resources.getString("saveas"), e -> saveAsFile()));
    return fileMenu;
  }

  private void initializeLayout() {
    this.getChildren().clear();
    this.add(input, 0, 1);
    setGrowPriorityAlways(input);

    this.setMinWidth(MIN_WIDTH);

    this.add(myButtons, 0, 0);
    GridPane.setVgrow(myButtons, Priority.NEVER);
  }

  private void parseInput() {
    turtleController.clear();
    turtleController.resetTurtles();
    myParser.parse(input.getText());
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
    } catch (FileNotFoundException | NullPointerException ignored) { }
  }

  private void saveFile() {
    try {
      File file = new File(myFilePath);
      FileWriter fw = new FileWriter(file);

      fw.write(input.getText());
      fw.close();
    } catch (IOException ignored) { }
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
      myFilePath = file.getAbsolutePath();
      if (!hasLogoExtension(myFilePath)) {
        myFilePath += ".logo";
      }
      saveFile();
      mySaveButton.setDisable(false);
    }
  }

  private boolean hasLogoExtension(String filename) {
    String extension = "";
    int i = filename.lastIndexOf('.');
    if (i > 0) {
      extension = filename.substring(i+1);
    }
    return extension.equals("logo");
  }

  /**
   * Updates the ResourceBundle being used.
   * @param resources the new ResourceBundle
   */
  @Override
  public void updateResources(ResourceBundle resources) {
    initializeButtons(resources);
    initializeLayout();
  }

  @Override
  public Element toXMLElement() {
    XMLBuilder xmlBuilder = XMLBuilder.newInstance();
    Element root = xmlBuilder.createElement(this.getClass().getSimpleName());

    Element text = xmlBuilder.createElement("text");
    text.appendChild(xmlBuilder.createTextNode(input.getText()));
    root.appendChild(text);

    return root;
  }

  @Override
  public void setContentsFromXMLElement(Element element) {
    for (int n = 0; n < element.getChildNodes().getLength(); n++) {
      Node node = element.getChildNodes().item(n);
      if (node.getNodeName().equals("text")) {
        input.setText(node.getTextContent());
      }
    }
  }
}
