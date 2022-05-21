package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.mappings.bloodtype.BloodType;
import com.bairontapia.projects.cuidamed.mappings.gender.Gender;
import com.bairontapia.projects.cuidamed.mappings.healthcaresystem.HealthCare;
import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicalRecordPOJO;
import com.bairontapia.projects.cuidamed.pojo.ResponsiblePOJO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ElderView {

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
  private MedicationPrescriptionView medicationPrescriptionController;


  @FXML
  private RoutineCheckupView routineCheckupView;

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
    fillFields(elder);
  }

  private void fillFields(ElderPOJO elder) {
    fillElderFields(elder);
    fillMedicalRecordFields(elder.getMedicalRecord());
    fillResponsibleFields(elder.getResponsible());
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
}
