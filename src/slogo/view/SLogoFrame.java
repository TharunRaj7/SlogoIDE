package slogo.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slogo.view.ExceptionFeedback.ExceptionType;
import slogo.view.utility.MenuFactory;
import slogo.view.utility.XMLBuilder;
import slogo.view.workspace.Workspace;
import slogo.view.workspace.WorkspaceFactory;

public class SLogoFrame extends Application implements IFrame {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 600;

  public static final String DEFAULT_RESOURCES_PACKAGE = SLogoFrame.class.getPackageName() + ".resources.";
  public static final String DEFAULT_LANGUAGE = "English";
  private static final String MISSING_LANGUAGE_PROPERTIES = "MissingLanguage";

  private Stage myPrimaryStage;
  private Pane myLayout;
  private ResourceBundle myResources;
  private TabPane myWorkspaces;

  public SLogoFrame() {
    super();
  }

  /**
   * Starts the application, opening the window.
   * @param primaryStage a stage, provided by Application.launch()
   */
  @Override
  public void start(Stage primaryStage) {
    myPrimaryStage = primaryStage;
    initializeResources(DEFAULT_LANGUAGE);
    initializeWorkspaces();
    initializeLayoutPane();
    initializeStage();
    myPrimaryStage.show();
  }

  private void initializeResources(String language) {
    try {
      myResources = ResourceBundle.getBundle(DEFAULT_RESOURCES_PACKAGE + language);
    } catch (Exception e) {
      initializeResources(MISSING_LANGUAGE_PROPERTIES);
    }
  }

  private void initializeLayoutPane() {
    VBox layout = new VBox();

    layout.getChildren().add(MenuFactory.makeSlogoMenu(this));
    layout.getChildren().add(myWorkspaces);

    myLayout = layout;
  }

  private void initializeWorkspaces() {
    myWorkspaces = new TabPane();
    myWorkspaces.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    addWorkspace();
  }

  /**
   * Adds a new workspace to the workspaces tab of this frame.
   */
  public void addWorkspace() {
    addWorkspace(WorkspaceFactory.defaultWorkspace(getResourceLanguage(myResources)));
  }

  private void addWorkspace(Workspace w) {
    myWorkspaces.getTabs().add(w);
    myWorkspaces.getSelectionModel().select(w);
    myWorkspaces.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
  }

  /**
   * Saves the current workspace to an XML file for exporting/importing.
   */
  public void saveCurrentWorkspace() {
    FileChooser fc = new FileChooser();
    String dataPath = System.getProperty("user.dir") + "/data";
    File workingDirectory = new File(dataPath);
    fc.setInitialDirectory(workingDirectory);

    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)",
        "*.xml");
    fc.getExtensionFilters().add(extFilter);

    File file = fc.showSaveDialog(new Stage());

    if (file != null) {
      String filepath = file.getAbsolutePath();
      Tab current = myWorkspaces.getSelectionModel().getSelectedItem();
      if (current instanceof Workspace) {
        Workspace workspace = (Workspace) current;
        workspace.sendToXML(filepath);
      }
    }
  }

  /**
   * Opens a workspace from an XML file.
   */
  public void openWorkspace() {
    FileChooser fc = new FileChooser();
    String dataPath = System.getProperty("user.dir") + "/data";
    File workingDirectory = new File(dataPath);
    fc.setInitialDirectory(workingDirectory);

    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)",
        "*.xml");
    fc.getExtensionFilters().add(extFilter);

    File file = fc.showOpenDialog(new Stage());
    addWorkspace(WorkspaceFactory.fromXML(file.getAbsolutePath()));
  }

  private void initializeStage() {
    myPrimaryStage.setTitle(myResources.getString("title"));
    Scene primaryScene = new Scene(myLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
    myPrimaryStage.setScene(primaryScene);
  }

  /**
   * Closes the window.
   */
  public void close() {
    myPrimaryStage.close();
  }

  /**
   * Sets the language of the window and all GUI components.
   * @param language the new language
   */
  public void setLanguage(String language) {
    initializeResources(language);
    for (Tab w : myWorkspaces.getTabs()) {
      if (w instanceof Workspace) {
        ((Workspace) w).setLanguage(getResourceLanguage(myResources));
        ((Workspace) w).setParserLanguage(language);
      }
    }
    for (Object n : myLayout.getChildren()) {
      if (n instanceof MenuBar) {
        myLayout.getChildren().remove(n);
        break;
      }
    }
    myLayout.getChildren().add(0, MenuFactory.makeSlogoMenu(this));
    myPrimaryStage.setTitle(myResources.getString("title"));
  }

  /**
   * Gets this window's language resources.
   * @return myResources
   */
  public ResourceBundle getResources() {
    return myResources;
  }

  /**
   * Gets the language that corresponds to this a language resource bundle.
   * @param resources ResourceBundle to check
   * @return String language
   */
  public static String getResourceLanguage(ResourceBundle resources) {
    return resources.getBaseBundleName().substring(
        resources.getBaseBundleName().lastIndexOf('.')+1
    );
  }
}
