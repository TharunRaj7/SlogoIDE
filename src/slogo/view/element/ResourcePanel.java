package slogo.view.element;

import java.util.Collections;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.w3c.dom.Element;
import slogo.utility.ResourceHandler;
import slogo.view.utility.XMLBuilder;

public class ResourcePanel extends GuiElement {

  public ResourcePanel() {
    super();
    initializeLayout();
  }

  private void initializeLayout() {
    TabPane tabs = new TabPane();
    tabs.getTabs().add(new ResourceTab("Colors", "data/resources/Colors.properties",
        r -> {
          Pane p = new Pane();
          p.setBackground(new Background(new BackgroundFill((Paint) Color.valueOf(r), null, null)));
          return p;
        }
    ));

    tabs.getTabs().add(new ResourceTab("Images", "data/resources/Images.properties",
        r -> new ImageView(new Image(r))
    ));
    this.add(tabs, 0, 0);
  }

  private static class ResourceTab extends Tab {

    private ResourceBundle myResources;
    private static final int DEFAULT_ROWS = 3;

    ResourceTab(String title, String resourcePath, ResourceHandler rh) {
      initializeResources(resourcePath);
      initializeTabLayout(rh);
      this.setText(title);
    }

    private void initializeResources(String resourcePath) {
      myResources = ResourceBundle.getBundle(resourcePath);
    }

    private void initializeTabLayout(ResourceHandler rh) {
      ScrollPane scroll = new ScrollPane();
      GridPane layout = new GridPane();

      int index = 0;
      for (String key : Collections.list(myResources.getKeys())) {
        VBox icon = new VBox();
        icon.getChildren().add(new Label(key));
        icon.getChildren().add(rh.handleResource(myResources.getString(key)));
        layout.add(icon, index / DEFAULT_ROWS, index % DEFAULT_ROWS);
        index += 1;
      }
      scroll.setContent(layout);
    }

  }

  @Override
  public Element toXMLElement() {
    return XMLBuilder.newInstance().createElement(this.getClass().getSimpleName());
  }

  @Override
  public void setContentsFromXMLElement(Element element) {
    // do nothing
  }
}
