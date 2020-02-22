package slogo.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SLogoFrame extends Application implements IFrame {

  private static final double WINDOW_WIDTH = 800;
  private static final double WINDOW_HEIGHT = 600;
  private GridPane myLayoutPane;

  public SLogoFrame() {
    super();
  }

  @Override
  public void start(Stage primaryStage) {
    initializeLayoutPane();
    initializeStage(primaryStage);
    primaryStage.show();
  }

  private void initializeLayoutPane() {
    myLayoutPane = new GridPane();
    myLayoutPane.setPadding(new Insets(5));
  }

  private void initializeStage(Stage primaryStage) {
    primaryStage.setTitle("SLogo Interpreter"); // TODO: pull from resources file
    Scene primaryScene = new Scene(myLayoutPane, WINDOW_WIDTH, WINDOW_HEIGHT);
    //String style = getClass().getResource("/resources/stylesheet.css").toExternalForm();
    //primaryScene.getStylesheets().add(style);
    primaryStage.setScene(primaryScene);
  }

  @Override
  public void addPanel(Pane pane, int columnIndex, int rowIndex) {
    myLayoutPane.add(pane, columnIndex, rowIndex);
  }

}
