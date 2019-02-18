package org.example.jdk11.javafx.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christoph Graupner <ch.graupner@workingdeveloper.net>
 */
public class Application {
  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) {
    FxApplication.launch(FxApplication.class);
    ;
  }
}
