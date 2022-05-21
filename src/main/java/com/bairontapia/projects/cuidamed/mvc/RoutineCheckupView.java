package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.medicalrecord.routinecheckup.RoutineCheckup;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RoutineCheckupView {

  @FXML
  private TableView<RoutineCheckup> checkupTableView;
  @FXML
  private TableColumn<RoutineCheckup, LocalDate> checkupDate;
  @FXML
  private TableColumn<RoutineCheckup, Double> height;
  @FXML
  private TableColumn<RoutineCheckup, Double> weight;
  @FXML
  private TableColumn<RoutineCheckup, Double> bmi;
  @FXML
  private TableColumn<RoutineCheckup, Integer> heartRate;
  @FXML
  private TableColumn<RoutineCheckup, Integer> diastolicPressure;
  @FXML
  private TableColumn<RoutineCheckup, Integer> systolicPressure;
  @FXML
  private TableColumn<RoutineCheckup, Integer> bodyTemperature;
}
