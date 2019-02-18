package org.example.jdk11.javafx.starter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class FxApplication extends Application {

  private static volatile FxApplication instance;
  private final           Logger        logger = LoggerFactory.getLogger(getClass());
  private volatile        Stage         primaryStage;

  public static FxApplication getInstance() {
    return instance;
  }

  /**
   * Launches the JavaFX runtime in another thread and waits till it's launched.
   */
  public static void start() {
    ForkJoinTask<?> fxAppTask = ForkJoinTask.adapt(
        () -> FxApplication.launch(FxApplication.class)
    );
    ForkJoinPool.commonPool().execute(fxAppTask);

    int waitCount = 10;
    while (waitCount > 0
           &&
           (FxApplication.getInstance() == null
            || FxApplication.getInstance().getPrimaryStage() == null)
    ) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
      waitCount--;
    }
    if (waitCount <= 0) {
      throw new IllegalStateException("Time out to wait for UI to start.");
    }
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  @Override
  public void start(Stage primaryStage) {
    if (instance != null) {
      throw new IllegalStateException("FxApplication already started.");
    }
    instance = this;
    this.primaryStage = primaryStage;
    URL fxmlUrl = org.example.jdk11.javafx.starter.Application.class.getResource("/fxml/main.fxml");

    // Load the FXML document
    try {
      logger.trace("Starting");
      BorderPane root          = FXMLLoader.load(fxmlUrl);
      String     javaVersion   = System.getProperty("java.version");
      String     javafxVersion = System.getProperty("javafx.version");
      Label l =
          new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
      root.setCenter(l);
      Scene scene = new Scene(root, 640, 480);
      primaryStage.setScene(scene);
      primaryStage.setTitle("MyApp 1.0.0");
      primaryStage.show();
      logger.trace("Running");
    } catch (IOException e) {
      logger.error(e.getLocalizedMessage(), e);
    }
  }
}
