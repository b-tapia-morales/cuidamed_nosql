package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.mappings.dosagestatus.DosageStatus;
import com.bairontapia.projects.cuidamed.pojo.DiagnosticPOJO;
import com.bairontapia.projects.cuidamed.pojo.DiseasePOJO;
import com.bairontapia.projects.cuidamed.pojo.DiseasePojoDAO;
import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.ElderPojoDAO;
import com.bairontapia.projects.cuidamed.pojo.MedicationAdministrationPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicationPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicationPojoDAO;
import com.bairontapia.projects.cuidamed.pojo.MedicationPrescriptionPOJO;
import com.bairontapia.projects.cuidamed.relational.disease.diagnostic.Diagnostic;
import com.bairontapia.projects.cuidamed.relational.disease.medicationadministration.MedicationAdministration;
import com.bairontapia.projects.cuidamed.relational.disease.medicationprescription.MedicationPrescription;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.IntStream;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public class MedicationPrescriptionDialog implements ErrorChecking {

  private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

  @Getter
  private final StringBuilder messageBuilder = new StringBuilder();

  @Getter
  @Setter
  private ElderPOJO elder;

  @FXML
  private ComboBox<String> diseaseComboBox;
  @FXML
  private DatePicker diagnosticDatePicker;
  @FXML
  private DatePicker prescriptionDatePicker;
  @FXML
  public ComboBox<String> medicationComboBox;
  @FXML
  private TextField prescriptionDuration;
  @FXML
  private ComboBox<Integer> quantityComboBox;
  @FXML
  private Button add;
  @FXML
  private Button cancel;

  public void initialize() {
    diseaseComboBox.getItems()
        .addAll(DiseasePojoDAO.getInstance().findAll().stream().map(DiseasePOJO::getName).toList());
    medicationComboBox.getItems().addAll(
        MedicationPojoDAO.getInstance().findAll().stream().map(MedicationPOJO::getName).toList());
    diagnosticDatePicker.setValue(LocalDate.now());
    prescriptionDatePicker.setValue(LocalDate.now());
    quantityComboBox.getItems().addAll(IntStream.rangeClosed(1, 5).boxed().toList());
  }

  @Override
  public void trimFields() {
    prescriptionDuration.setText(StringUtils.trim(prescriptionDuration.getText()));
  }

  @Override
  public boolean fieldsAreEmpty() {
    return diseaseComboBox.getSelectionModel().isEmpty() ||
        diagnosticDatePicker.getValue() == null ||
        prescriptionDatePicker.getValue() == null ||
        medicationComboBox.getSelectionModel().isEmpty() ||
        StringUtils.isEmpty(prescriptionDuration.getText()) ||
        quantityComboBox.getSelectionModel().isEmpty();
  }

  @Override
  public boolean fieldsAreValid() {
    return Ints.tryParse(prescriptionDuration.getText()) != null;
  }

  @Override
  public boolean fieldsAreCorrect() {
    var diagnosticDate = diagnosticDatePicker.getValue();
    var prescriptionDate = prescriptionDatePicker.getValue();
    if (ChronoUnit.DAYS.between(diagnosticDate, prescriptionDate) < 0) {
      return false;
    }
    var days = Integer.parseInt(prescriptionDuration.getText());
    return days >= 1 && days <= 90;
  }

  @Override
  public void buildErrorMessage() {
    var diagnosticDate = diagnosticDatePicker.getValue();
    var prescriptionDate = prescriptionDatePicker.getValue();
    if (ChronoUnit.DAYS.between(diagnosticDate, prescriptionDate) < 0) {
      messageBuilder.append(
          "La fecha de diagnóstico no puede ser posterior a la fecha de prescripción");
    }
    var days = Integer.parseInt(prescriptionDuration.getText());
    if (days < 0) {
      messageBuilder.append("La duración de la prescripción no puede ser negativa");
    }
    if (days > 90) {
      messageBuilder.append("La duración de la prescripción no puede superar los 90 días");
    }
  }

  @Override
  public void performAction(Event event) throws IOException {
    ElderPojoDAO.getInstance().updateDiagnostics(elder.getId(), generateDiagnostic());
    loadPreviousPanel((MouseEvent) event);
  }

  @FXML
  public void onAddButtonClicked(MouseEvent event) throws IOException {
    check(event);
  }

  @FXML
  public void onCancelButtonClicked(MouseEvent event) throws IOException {
    loadPreviousPanel(event);
  }

  private void loadPreviousPanel(MouseEvent event) throws IOException {
    var node = (Node) event.getSource();
    var stage = (Stage) node.getScene().getWindow();
    stage.close();
    var loader = new FXMLLoader(
        Objects.requireNonNull(CLASS_LOADER.getResource("fxml/elder_view.fxml")));
    var root = loader.<Parent>load();
    var controller = loader.<ElderView>getController();
    controller.receiveData(elder);
    var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  private DiagnosticPOJO generateDiagnostic() {
    var diagnosticDate = diagnosticDatePicker.getValue();

    var start = prescriptionDatePicker.getValue();
    var duration = Integer.parseInt(prescriptionDuration.getText());
    var end = prescriptionDatePicker.getValue().plusDays(duration);
    var days = (int) ChronoUnit.DAYS.between(start, end);
    var quantity = quantityComboBox.getValue();

    var diseaseName = diseaseComboBox.getValue();
    var disease = DiseasePojoDAO.getInstance().find(diseaseName).orElseThrow();
    var medicationName = medicationComboBox.getValue();
    var medication = MedicationPojoDAO.getInstance().find(medicationComboBox.getValue())
        .orElseThrow();

    var prescriptions = new ArrayList<MedicationPrescriptionPOJO>();
    var administrations = new ArrayList<MedicationAdministrationPOJO>();

    var diagnostic = new Diagnostic("", diseaseName, start, "");
    var prescription = new MedicationPrescription("", diseaseName, diagnosticDate, medicationName,
        start, end, quantity.shortValue());

    if (quantity == 1) {
      var intake = start.atStartOfDay().plusDays(1).plusHours(8);
      for (int i = 1; i <= days; i++) {
        var administration = new MedicationAdministration("", medicationName, intake, null,
            DosageStatus.UNDEFINED, "");
        administrations.add(new MedicationAdministrationPOJO(administration));
        intake = intake.truncatedTo(ChronoUnit.HOURS).plusDays(1).plusHours(8);
      }
      prescriptions.add(new MedicationPrescriptionPOJO(prescription, medication, administrations));
      return new DiagnosticPOJO(diagnostic, disease, prescriptions);
    }

    var intake = start.atStartOfDay().plusDays(1).plusHours(8);
    var interval = (20 - 8) / (quantity - 1);
    for (int i = 1; i <= days; i++) {
      for (int j = 1; j <= (quantity - 1); j++) {
        var administration = new MedicationAdministration("", medicationName, intake, null,
            DosageStatus.UNDEFINED, "");
        administrations.add(new MedicationAdministrationPOJO(administration));
        intake = intake.plusHours(interval);
      }
      var administration = new MedicationAdministration("", medicationName, intake, null,
          DosageStatus.UNDEFINED, "");
      administrations.add(new MedicationAdministrationPOJO(administration));
      intake = intake.plusHours(12);
    }
    prescriptions.add(new MedicationPrescriptionPOJO(prescription, medication, administrations));
    return new DiagnosticPOJO(diagnostic, disease, prescriptions);
  }

}
