package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.DiagnosticPOJO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MedicationPrescriptionView {

  @FXML
  private TableView<DiagnosticPOJO> administrationTable;
  @FXML
  private TableColumn<DiagnosticPOJO, String> diseaseName;
  @FXML
  private TableColumn<DiagnosticPOJO, LocalDate> diagnosticDate;
  @FXML
  private TableColumn<DiagnosticPOJO, String> medicationName;
  @FXML
  private TableColumn<DiagnosticPOJO, LocalDateTime> intakeDateTime;
  @FXML
  private TableColumn<DiagnosticPOJO, String> intakeStatus;
}
