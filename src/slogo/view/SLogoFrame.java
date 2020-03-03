package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import slogo.view.element.GuiElement;
import slogo.view.utility.MenuFactory;

public class SLogoFrame extends Application implements IFrame {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 600;

  static final String DEFAULT_RESOURCES_PACKAGE = SLogoFrame.class.getPackageName() + ".resources.";
  static final String DEFAULT_LANGUAGE = "English";

  private Stage myPrimaryStage;
  private Pane myLayout;
  private ResourceBundle myResources;
  private TabPane myWorkspaces;

  public SLogoFrame() {
    super();
  }

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
      initializeResources(getResourceLanguage(myResources));
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

  public void addWorkspace() {
    myWorkspaces.getTabs().add(new Workspace(getResourceLanguage(myResources)));
    if (myWorkspaces.getTabs().size() > 1) {
      myWorkspaces.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
    }
  }

  private void initializeStage() {
    myPrimaryStage.setTitle(myResources.getString("title"));
    Scene primaryScene = new Scene(myLayout, WINDOW_WIDTH, WINDOW_HEIGHT);
    myPrimaryStage.setScene(primaryScene);
  }

  public void close() {
    myPrimaryStage.close();
  }

  public void setLanguage(String language) {
    initializeResources(language);
    for (Tab w : myWorkspaces.getTabs()) {
      if (w instanceof Workspace) {
        ((Workspace) w).setLanguage(getResourceLanguage(myResources));
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

  public ResourceBundle getResources() {
    return myResources;
  }

  static String getResourceLanguage(ResourceBundle resources) {
    return resources.getBaseBundleName().substring(
        resources.getBaseBundleName().lastIndexOf('.')+1
    );
  }
}
