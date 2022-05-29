package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.Administration;
import com.bairontapia.projects.cuidamed.utils.time.TimeUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;

public class MedicationPrescriptionView {

  @FXML
  @Getter
  private TableView<Administration> administrationTable;
  @FXML
  @Getter
  private TableColumn<Administration, String> diseaseName;
  @FXML
  @Getter
  private TableColumn<Administration, String> diagnosticDate;
  @FXML
  @Getter
  private TableColumn<Administration, String> medicationName;
  @FXML
  @Getter
  private TableColumn<Administration, String> intakeDateTime;
  @FXML
  @Getter
  private TableColumn<Administration, String> intakeStatus;

  public void initialize() {
    diseaseName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().diseaseName()));
    diagnosticDate.setCellValueFactory(
        e -> new SimpleStringProperty(TimeUtils.format(e.getValue().diagnosticDate())));
    medicationName.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().medicationName()));
    intakeDateTime.setCellValueFactory(
        e -> new SimpleStringProperty(TimeUtils.format(e.getValue().intakeDateTime())));
    intakeStatus.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().intakeStatus()));
  }
}
