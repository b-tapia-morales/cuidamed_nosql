package com.bairontapia.projects.cuidamed.mvc;

import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoutineCheckupDialog {

  private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

  @FXML
  private DatePicker checkupDatePicker;
  @FXML
  private TextField height;
  @FXML
  private TextField weight;
  @FXML
  private TextField heartRate;
  @FXML
  private TextField diastolicPressure;
  @FXML
  private TextField systolicPressure;
  @FXML
  private TextField bodyTemperature;
  @FXML
  private Button add;
  @FXML
  private Button cancel;

  @FXML
  public void addRoutineCheckup(ActionEvent event) {
  }

  @FXML
  public void cancelRoutineCheckup(ActionEvent event) throws IOException {
    var node = (Node) event.getSource();
    var stage = (Stage) node.getScene().getWindow();
    stage.close();
    var loader = new FXMLLoader(
        Objects.requireNonNull(CLASS_LOADER.getResource("fxml/elder_view.fxml")));
    var root = loader.<Parent>load();
    // var controller = loader.<ElderView>getController();
    var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
