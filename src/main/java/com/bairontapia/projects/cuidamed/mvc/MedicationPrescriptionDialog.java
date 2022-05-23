package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.*;

import java.io.Console;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import com.bairontapia.projects.cuidamed.relational.disease.Disease;
import com.bairontapia.projects.cuidamed.relational.disease.diagnostic.Diagnostic;
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

import javax.swing.plaf.synth.SynthTextAreaUI;

public class MedicationPrescriptionDialog {

  private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

  @Getter
  @Setter
  private ElderPOJO elder;

  @FXML
  private ComboBox<String> diseaseComboBox;
  @FXML
  private DatePicker diagnosticDate;
  @FXML
  private DatePicker prescriptionDate;
  @FXML
  private ComboBox<Integer> quantityComboBox;
  @FXML
  private TextField prescriptionDuration;
  @FXML
  private Button add;
  @FXML
  private Button cancel;

  public void initialize() {
    diseaseComboBox.getItems().addAll(MedicationPojoDAO.getInstance().findAll().stream().map(
            MedicationPOJO::getName).toList());
    diagnosticDate.setValue(LocalDate.now());
    prescriptionDate.setValue(LocalDate.now());
    quantityComboBox.getItems().addAll(IntStream.rangeClosed(1, 5).boxed().toList());
  }

  @FXML
  public void onAddButtonClicked(MouseEvent event) {

    var diseasePojo = DiseasePojoDAO.getInstance().find("Bupivacaína").orElseThrow();
    var diagnostic = new Diagnostic("", "", prescriptionDate.getValue(), "");
    var diagnosticPojo = new DiagnosticPOJO(diagnostic, diseasePojo, listMedicationPrescription());
    //DiagnosticPOJODAO.getInstance().saveIntoElder(elder.getId(), diagnosticPojo);
  }

  @FXML
  public void onCancelButtonClicked(MouseEvent event) throws IOException {
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

  private List<MedicationPrescriptionPOJO> listMedicationPrescription() {
    ArrayList<MedicationAdministrationPOJO> listMedicationAdministration = new ArrayList<>();
    LocalDate date = prescriptionDate.getValue();
    int days = Integer.parseInt(prescriptionDuration.getText());
    var MedicationPjo = MedicationPojoDAO.getInstance().find(diseaseComboBox.getValue());
    LocalDate endDate = prescriptionDate.getValue().plusDays(days);
    for (int i = 0; i < days; i++) {
      date = date.plusDays(1);

      for (int j= 0; j < quantityComboBox.getValue();j++){
          LocalDateTime schedule = date.atStartOfDay();
          System.out.println(schedule);
      }
    }

    return null;
  }
}
