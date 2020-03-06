package slogo.utility;

import javafx.scene.Node;

@FunctionalInterface
public interface ResourceHandler {
  Node handleResource(String value);
}
