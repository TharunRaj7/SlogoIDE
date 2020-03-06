package slogo.utility;

import javafx.scene.Node;

@FunctionalInterface
public interface ResourceHandler {
  public Node handleResource(String value);
}
