package org.example.jdk11.javafx.starter.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class MainController {
  @FXML
  void actionClose(final ActionEvent actionEvent) {
    Platform.exit();
  }
}
