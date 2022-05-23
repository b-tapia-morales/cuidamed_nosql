package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.mappings.bloodtype.BloodType;
import com.bairontapia.projects.cuidamed.mappings.gender.Gender;
import com.bairontapia.projects.cuidamed.mappings.healthcaresystem.HealthCare;
import com.bairontapia.projects.cuidamed.pojo.CustomAdministration;
import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicalRecordPOJO;
import com.bairontapia.projects.cuidamed.pojo.ResponsiblePOJO;
import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPojoDAO;
import java.io.IOException;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

public class ElderView {

  private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

  @Getter
  @Setter
  private ElderPOJO elder;

  @FXML
  private Button goBackButton;
  @FXML
  private AnchorPane pane;
  @FXML
  private TextField rut;
  @FXML
  private TextField name;
  @FXML
  private TextField lastName;
  @FXML
  private TextField secondLastName;
  @FXML
  private DatePicker birthDatePicker;
  @FXML
  private TextField age;
  @FXML
  private ComboBox<Gender> genderComboBox;
  @FXML
  private CheckBox isActiveCheckBox;
  @FXML
  private DatePicker admissionDatePicker;
  @FXML
  private ComboBox<BloodType> bloodTypeComboBox;
  @FXML
  private ComboBox<HealthCare> healthCareComboBox;
  @FXML
  private TextField responsibleRut;
  @FXML
  private TextField responsibleName;
  @FXML
  private TextField responsibleLastName;
  @FXML
  private TextField responsibleSecondLastName;
  @FXML
  private DatePicker responsibleBirthDatePicker;
  @FXML
  private ComboBox<Gender> responsibleGenderComboBox;
  @FXML
  private TextField responsibleAge;
  @FXML
  private TextField responsibleMobilePhone;
  @FXML
  private Button updateRegister;
  @FXML
  private TabPane tabPane;
  @FXML
  private Tab routineCheckupTab;
  @FXML
  private RoutineCheckupView routineCheckupController;
  @FXML
  private Tab prescriptionTab;
  @FXML
  private MedicationPrescriptionView prescriptionController;
  @FXML
  private Button addCheckupButton;
  @FXML
  private Button addPrescriptionButton;

  public void initialize() {
    genderComboBox.getItems().addAll(Gender.getValues());
    bloodTypeComboBox.getItems().addAll(BloodType.getValues());
    healthCareComboBox.getItems().addAll(HealthCare.getValues());
    responsibleGenderComboBox.getItems().addAll(Gender.getValues());
  }

  @FXML
  public void addColumn(ActionEvent event) {
  }

  public void receiveData(ElderPOJO elder) {
    setElder(elder);
    fillFields(elder);
  }

  private void fillFields(ElderPOJO elder) {
    fillElderFields(elder);
    fillMedicalRecordFields(elder.getMedicalRecord());
    fillResponsibleFields(elder.getResponsible());
    var routineCheckups = RoutineCheckupPojoDAO.getInstance().findAll(elder.getId());
    routineCheckupController.getCheckupTableView().getItems().clear();
    routineCheckupController.getCheckupTableView().getItems().addAll(routineCheckups);
    prescriptionController.getAdministrationTable().getItems().clear();
    prescriptionController.getAdministrationTable().getItems()
        .addAll(CustomAdministration.map(elder.getDiagnostics()));
  }

  private void fillElderFields(ElderPOJO elder) {
    rut.setText(elder.getRut());
    name.setText(elder.getFirstName());
    lastName.setText(elder.getLastName());
    secondLastName.setText(elder.getSecondLastName());
    birthDatePicker.setValue(elder.getBirthDate());
    age.setText(elder.getAge().toString());
    genderComboBox.setValue(Gender.getValueFromName(elder.getGender()));
    isActiveCheckBox.setSelected(elder.getIsActive());
    admissionDatePicker.setValue(elder.getAdmissionDate());
  }

  private void fillMedicalRecordFields(MedicalRecordPOJO medicalRecord) {
    bloodTypeComboBox.setValue(BloodType.getValueFromName(medicalRecord.getBloodType()));
    healthCareComboBox.setValue(HealthCare.getValueFromName(medicalRecord.getHealthCare()));
  }

  private void fillResponsibleFields(ResponsiblePOJO responsible) {
    responsibleRut.setText(responsible.getRut());
    responsibleName.setText(responsible.getFirstName());
    responsibleLastName.setText(responsible.getLastName());
    responsibleSecondLastName.setText(responsible.getSecondLastName());
    responsibleBirthDatePicker.setValue(responsible.getBirthDate());
    responsibleAge.setText(responsible.getAge().toString());
    responsibleGenderComboBox.setValue(Gender.getValueFromName(responsible.getGender()));
    responsibleMobilePhone.setText(responsible.getMobilePhone().toString());
  }

  @FXML
  public void onGoBackButtonClicked(MouseEvent event) throws IOException {
    var node = (Node) event.getSource();
    var stage = (Stage) node.getScene().getWindow();
    stage.close();
    var loader = new FXMLLoader(
        Objects.requireNonNull(CLASS_LOADER.getResource("fxml/elders_list_view.fxml")));
    var root = loader.<Parent>load();
    var controller = loader.<ElderListView>getController();
    controller.updateData();
    var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void onAddCheckupButtonClicked(MouseEvent event) throws IOException {
    var node = (Node) event.getSource();
    var stage = (Stage) node.getScene().getWindow();
    stage.close();
    var loader = new FXMLLoader(
        Objects.requireNonNull(CLASS_LOADER.getResource("fxml/routine_checkup_dialog.fxml")));
    var root = loader.<Parent>load();
    var controller = loader.<RoutineCheckupDialog>getController();
    controller.receiveData(elder);
    var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  public void onAddPrescriptionButtonClicked(MouseEvent event) throws IOException {
    var node = (Node) event.getSource();
    var stage = (Stage) node.getScene().getWindow();
    stage.close();
    var loader = new FXMLLoader(Objects.requireNonNull(
        CLASS_LOADER.getResource("fxml/medication_prescription_dialog.fxml")));
    var root = loader.<Parent>load();
    var controller = loader.<MedicationPrescriptionDialog>getController();
    controller.setElder(elder);
    var scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
