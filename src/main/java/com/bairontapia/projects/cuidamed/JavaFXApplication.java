package com.bairontapia.projects.cuidamed;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

public class JavaFXApplication extends Application {
  
  public static void main(final String... args) {
    launch();
  }

  @Override
  public void start(final Stage stage) throws IOException {
    final Parent root = FXMLLoader.load(
        Objects.requireNonNull(getClass().getResource("/fxml/main_window.fxml")));
    final var scene = new Scene(root);
    stage.setTitle("CuidaMed");
    stage.setScene(scene);
    stage.getIcons().addAll(generateIcons());
    stage.setResizable(false);
    stage.show();
  }

  private static List<Image> generateIcons() {
    var sizes = List.of(16, 24, 32, 64);
    var fileNames = sizes.stream()
        .map(e -> StringUtils.join("/icons/nursing-", e.toString(), ".png")).toList();
    return fileNames.stream().map(Image::new).toList();
  }

}
