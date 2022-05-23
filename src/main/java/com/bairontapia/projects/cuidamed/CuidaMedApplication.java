package com.bairontapia.projects.cuidamed;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuidaMedApplication {

  public static void main(String... args) throws SQLException, IOException {
    var mongoLogger = Logger.getLogger("org.mongodb.driver");
    mongoLogger.setLevel(Level.WARNING);
    //Migration.performMigration();
    JavaFXApplication.main(args);
  }
}
