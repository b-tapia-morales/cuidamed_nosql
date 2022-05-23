package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.Administration;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;

public class MedicationPrescriptionView {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy/MM/dd");
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy/MM/dd HH:mm:ss");

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
    diseaseName.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().diseaseName()));
    diagnosticDate.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().diagnosticDate().format(DATE_FORMATTER)));
    medicationName.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().medicationName()));
    intakeDateTime.setCellValueFactory(
        e -> new SimpleStringProperty(
            e.getValue().intakeDateTime().format(DATE_TIME_FORMATTER)));
    intakeStatus.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().intakeStatus()));
  }
}
