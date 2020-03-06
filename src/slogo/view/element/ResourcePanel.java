package slogo.view.element;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.w3c.dom.Element;
import slogo.utility.ResourceHandler;
import slogo.view.ExceptionFeedback;
import slogo.view.ExceptionFeedback.ExceptionType;
import slogo.view.utility.XMLBuilder;

public class ResourcePanel extends GuiElement {

  private static final String DEFAULT_RESOURCE_PATH = "data/resources";
  public static final int ICON_SIZE = 25;

  public ResourcePanel() {
    super();
    initializeLayout();
  }

  private void initializeLayout() {
    TabPane tabs = new TabPane();
    tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    tabs.getTabs().add(new ResourceTab("Colors", getResourceBundleFromPath("Colors"),
        r -> {
          Pane p = new Pane();
          p.setBackground(new Background(new BackgroundFill((Paint) Color.valueOf(r), null, null)));
          p.setMinSize(ICON_SIZE, ICON_SIZE);
          return p;
        }
    ));

    tabs.getTabs().add(new ResourceTab("Images", getResourceBundleFromPath("Images"),
        r -> {
          try {
            ImageView icon = new ImageView(new Image(new FileInputStream(r)));
            icon.setFitHeight(ICON_SIZE);
            icon.setFitWidth(ICON_SIZE);
            return icon;
          } catch (FileNotFoundException e) {
            ExceptionFeedback.throwException(ExceptionType.RESOURCE_EXCEPTION,
                "Failed to load resource specified in Images.properties.");
          }
          return new Pane();
        }
    ));

    setGrowPriorityAlways(tabs);
    this.add(tabs, 0, 0);
  }

  private ResourceBundle getResourceBundleFromPath(String path) {
    try {
      File file = new File(DEFAULT_RESOURCE_PATH);
      URL[] urls = {file.toURI().toURL()};
      ClassLoader loader = new URLClassLoader(urls);
      return ResourceBundle.getBundle(path, Locale.getDefault(), loader);
    } catch (Exception e) {
      ExceptionFeedback.throwException(ExceptionType.RESOURCE_EXCEPTION,
          "Failed to load resources for Resource Panel. Check your directory structure" +
          " and try loading resources again.");
    }
    return null;
  }

  private static class ResourceTab extends Tab {

    private static final int DEFAULT_ROWS = 3;

    ResourceTab(String title, ResourceBundle resources, ResourceHandler rh) {
      initializeTabLayout(resources, rh);
      this.setText(title);
    }

    private void initializeTabLayout(ResourceBundle resources, ResourceHandler rh) {
      ScrollPane scroll = new ScrollPane();
      scroll.setFitToWidth(true);
      scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
      setGrowPriorityAlways(scroll);

      GridPane layout = new GridPane();
      setGrowPriorityAlways(layout);
      setLayoutPadding(layout);

      int index = 0;
      for (String key : Collections.list(resources.getKeys())) {
        VBox icon = new VBox();
        icon.getChildren().add(new Label(key));
        icon.getChildren().add(rh.handleResource(resources.getString(key)));
        icon.setOnMouseClicked(e -> {
          StringSelection stringSelection = new StringSelection(resources.getString(key));
          Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
          clipboard.setContents(stringSelection, null);
        });
        setGrowPriorityAlways(icon);
        layout.add(icon, index / DEFAULT_ROWS, index % DEFAULT_ROWS);
        index += 1;
      }
      scroll.setContent(layout);
      this.setContent(scroll);
    }

    private void setLayoutPadding(GridPane layout) {
      layout.setPadding(new Insets(PADDING));
      layout.setHgap(PADDING);
      layout.setVgap(PADDING);
    }

    private void setGrowPriorityAlways(Node n) {
      GridPane.setHgrow(n, Priority.ALWAYS);
      GridPane.setVgrow(n, Priority.ALWAYS);
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
