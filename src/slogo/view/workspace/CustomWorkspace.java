package slogo.view.workspace;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import slogo.model.Parser;
import slogo.view.element.GuiElement;

public class CustomWorkspace extends Workspace {

  CustomWorkspace(String language) {
    super(language);
    this.setText("Workspace");
  }

  @Override
  protected void initializeLayoutPane() {
    // do nothing by default
    // layout must be set using setLayout after initialization
  }

  public void setParser(Parser p) {
    myParser = p;
  }

  void setLayout(List<List<GuiElement>> elements,
      List<Double> verticalDividers, List<List<Double>> horizontalDividers) {

    checkLayoutErrors(elements, verticalDividers, horizontalDividers);
    myGuiElements = new ArrayList<>();

    SplitPane layout = new SplitPane();
    layout.setOrientation(Orientation.VERTICAL);

    for (int i = 0; i < elements.size(); i++) {
      SplitPane row = new SplitPane();
      row.getItems().addAll(elements.get(i));
      myGuiElements.addAll(elements.get(i));

      for (int d = 0; d < horizontalDividers.get(i).size(); d++) {
        row.setDividerPosition(d, horizontalDividers.get(i).get(d));
      }

      layout.getItems().add(row);
    }

    for (int d = 0; d < verticalDividers.size(); d++) {
      layout.setDividerPosition(d, verticalDividers.get(d));
    }

    myLayout = layout;
    this.setContent(myLayout);
  }

  private void checkLayoutErrors(List<List<GuiElement>> elements,
      List<Double> verticalDividers, List<List<Double>> horizontalDividers) {

    if (elements.size() != horizontalDividers.size() ||
        elements.size()-1 != verticalDividers.size()) {
      throwLayoutError();
      return;
    }
    for (int i = 0; i < elements.size(); i++) {
      if (elements.get(i).size()-1 != horizontalDividers.get(i).size()) {
        throwLayoutError();
        return;
      }
    }
  }

  private void throwLayoutError() {
    System.out.println("ERROR: Layout error!");
    // TODO : throw layout error
  }

  ResourceBundle getResourceBundle() {
    return myResources;
  }

}
