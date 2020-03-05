package slogo.view.workspace;

import java.util.List;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import slogo.model.Parser;
import slogo.view.element.GuiElement;

public class CustomWorkspace extends Workspace {

  public CustomWorkspace(String language) {
    super(language);
  }

  @Override
  protected void initializeLayoutPane() {
    // do nothing by default
    // layout must be set using setLayout after initialization
  }

  public void setLayout(List<List<GuiElement>> elements,
      List<Double> verticalDividers, List<List<Double>> horizontalDividers) {

    checkLayoutErrors(elements, verticalDividers, horizontalDividers);

    SplitPane layout = new SplitPane();

    for (int i = 0; i < elements.size(); i++) {
      SplitPane row = new SplitPane();
      row.getItems().addAll(elements.get(i));

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
    // TODO : throw error
  }

  public Parser getParserInstance() {
    return myParser;
  }

}
