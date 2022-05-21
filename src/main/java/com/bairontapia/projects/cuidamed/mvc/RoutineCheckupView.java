package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPOJO;
import java.time.LocalDate;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;

public class RoutineCheckupView {

  @FXML
  @Getter
  private TableView<RoutineCheckupPOJO> checkupTableView;
  @FXML
  @Getter
  private TableColumn<RoutineCheckupPOJO, LocalDate> checkupDate;
  @FXML
  @Getter
  private TableColumn<RoutineCheckupPOJO, Double> height;
  @FXML
  @Getter
  private TableColumn<RoutineCheckupPOJO, Double> weight;
  @FXML
  @Getter
  private TableColumn<RoutineCheckupPOJO, Double> bmi;
  @FXML
  @Getter
  private TableColumn<RoutineCheckupPOJO, Integer> heartRate;
  @FXML
  @Getter
  private TableColumn<RoutineCheckupPOJO, Integer> diastolicPressure;
  @FXML
  @Getter
  private TableColumn<RoutineCheckupPOJO, Integer> systolicPressure;
  @FXML
  @Getter
  private TableColumn<RoutineCheckupPOJO, Double> bodyTemperature;

  public void initialize() {
    checkupDate.setCellValueFactory(e -> new SimpleObjectProperty<>(e.getValue().getCheckupDate()));
    height.setCellValueFactory(e -> new SimpleDoubleProperty(e.getValue().getHeight()).asObject());
    weight.setCellValueFactory(e -> new SimpleDoubleProperty(e.getValue().getWeight()).asObject());
    bmi.setCellValueFactory(e -> new SimpleDoubleProperty(e.getValue().getBmi()).asObject());
    heartRate.setCellValueFactory(
        e -> new SimpleIntegerProperty(e.getValue().getHeartRate()).asObject());
    diastolicPressure.setCellValueFactory(
        e -> new SimpleIntegerProperty(e.getValue().getDiastolicPressure()).asObject());
    systolicPressure.setCellValueFactory(
        e -> new SimpleIntegerProperty(e.getValue().getSystolicPressure()).asObject());
    bodyTemperature.setCellValueFactory(
        e -> new SimpleDoubleProperty(e.getValue().getBodyTemperature()).asObject());
  }

}
