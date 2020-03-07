package slogo.view.element;

import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.w3c.dom.Element;

public abstract class GuiElement extends GridPane {

  static final double PADDING = 5;
  static final double GAP = 2;

  GuiElement() {
    initializeLayout();
  }

  private void initializeLayout() {
    this.setPadding(new Insets(PADDING));
    this.setVgap(GAP);
    this.setHgap(GAP);

    setGrowPriorityAlways(this);
  }

  void setGrowPriorityAlways(Node node) {
    GridPane.setHgrow(node, Priority.ALWAYS);
    GridPane.setVgrow(node, Priority.ALWAYS);
  }

  /**
   * Updates the ResourceBundle being used by this GuiElement.
   *
   * By default, does nothing. GuiElements with resources to update should override this method.
   * @param resources the new ResourceBundle
   */
  public void updateResources(ResourceBundle resources) {
    // do nothing by default
  }

  /**
   * Creates an XML element representing this GuiElement.
   * @return new XML Element node
   */
  public abstract Element toXMLElement();

  /**
   * Sets the contents of this GuiElement from an XML element.
   * @param element XML Element node
   */
  public abstract void setContentsFromXMLElement(Element element);

  /**
   * Returns a string representing this class.
   * @return class name
   */
  @Override
  public String toString() {
    return this.getClass().getSimpleName();
  }
}
