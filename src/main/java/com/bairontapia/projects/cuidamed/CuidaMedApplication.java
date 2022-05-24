package com.bairontapia.projects.cuidamed;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CuidaMedApplication {

  public static void main(String... args) {
    var mongoLogger = Logger.getLogger("org.mongodb.driver");
    mongoLogger.setLevel(Level.WARNING);
    //Migration.performMigration();
    JavaFXApplication.main(args);
  }
}
