package utils;

import javafx.scene.control.Alert;

public class AlertBox {

  // Confirmation alert box
  public static void confirm(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(message);

    alert.showAndWait();
  }

  // Warning alert box
  public static void warning(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText(message);

    alert.showAndWait();
  }
}