package slogo.view.utility;

import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import slogo.view.SLogoFrame;

public class MenuFactory {

  private static final List<String> LANGUAGES = List.of(
      "English", "Spanish", "Chinese", "French", "German",
      "Italian", "Portuguese", "Russian", "Urdu"
  );

  public static MenuBar makeSlogoMenu(SLogoFrame frame) {
    MenuBar menuBar = new MenuBar();

    menuBar.getMenus().add(makeFileMenu(frame));
    menuBar.getMenus().add(makeLanguageMenu(frame));

    return menuBar;
  }

  private static Menu makeFileMenu(SLogoFrame frame) {
    ResourceBundle resources = frame.getResources();

    Menu fileMenu = new Menu(resources.getString("file"));
    MenuItem close = new MenuItem(resources.getString("close"));
    close.setOnAction(e -> frame.close());
    fileMenu.getItems().add(close);

    return fileMenu;
  }

  private static Menu makeLanguageMenu(SLogoFrame frame) {
    ResourceBundle resources = frame.getResources();

    Menu languageMenu = new Menu(resources.getString("language"));
    ToggleGroup tg = new ToggleGroup();
    for (String language : LANGUAGES) {
      RadioMenuItem rmi = new RadioMenuItem(language);
      rmi.setOnAction(e -> frame.setLanguage(language));
      languageMenu.getItems().add(rmi);
      tg.getToggles().add(rmi);
      if (language.equals(
          resources.getBaseBundleName().substring(
              resources.getBaseBundleName().lastIndexOf('.')+1)
      )) {
        rmi.setSelected(true);
      }
    }

    return languageMenu;
  }
}
