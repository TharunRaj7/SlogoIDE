package slogo.view.workspace;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import slogo.controller.Turtle;
import slogo.controller.TurtleController;
import slogo.model.Parser;
import slogo.utility.Location;
import slogo.view.SLogoFrame;
import slogo.view.element.Console;
import slogo.view.element.GuiElement;
import slogo.view.element.ScriptEditor;
import slogo.view.element.TurtleCanvas;
import slogo.view.element.VariableExplorer;
import slogo.view.utility.XMLBuilder;

public class DefaultWorkspace extends Workspace {

  public DefaultWorkspace(String language) {
    super(language);
    this.setText("Workspace");
  }

  @Override
  protected void initializeLayoutPane() {
    SplitPane topRow = new SplitPane();
    SplitPane botRow = new SplitPane();
    myGuiElements = new ArrayList<>();

    TurtleController turtleController = new TurtleController();
    TurtleCanvas tc = new TurtleCanvas(turtleController, myResources);
    myGuiElements.add(tc);
    turtleController.giveTurtleCanvas(tc);
    tc.addAllTurtleImages(); //adds all the images of the turtle
    myParser = new Parser(turtleController, SLogoFrame.getResourceLanguage(myResources));

    topRow.getItems().add(tc);
    ScriptEditor se = new ScriptEditor(myParser, turtleController, myResources);
    topRow.getItems().add(se);
    myGuiElements.add(se);
    topRow.setDividerPositions(0.5f);

    Console console = new Console(myParser);
    botRow.getItems().add(console);
    myGuiElements.add(console);
    VariableExplorer ve = new VariableExplorer();
    botRow.getItems().add(ve);
    myGuiElements.add(ve);
    botRow.setDividerPositions(0.7f);

    SplitPane sp = new SplitPane();
    sp.setOrientation(Orientation.VERTICAL);
    sp.setPadding(new Insets(PADDING));
    sp.getItems().addAll(topRow, botRow);
    sp.setDividerPositions(0.7f);

    myLayout = sp;
  }

}
