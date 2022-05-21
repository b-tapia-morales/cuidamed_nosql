package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.mappings.bloodtype.BloodType;
import com.bairontapia.projects.cuidamed.mappings.gender.Gender;
import com.bairontapia.projects.cuidamed.mappings.healthcaresystem.HealthCare;
import com.bairontapia.projects.cuidamed.person.elder.Elder;
import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

  public void receiveData(ElderPOJO elder) {
    fillFields(elder);
  }

  @FXML
  public void addColumn(ActionEvent actionEvent) {
  }

  private void fillFields(ElderPOJO elder) {
    rut.setText(elder.getRut());
    name.setText(elder.getFirstName());
    lastName.setText(elder.getLastName());
    secondLastName.setText(elder.getSecondLastName());
    age.setText(elder.getAge().toString());
    birthDatePicker.setValue(elder.getBirthDate());
    isActiveCheckBox.setSelected(elder.getIsActive());
    admissionDatePicker.setValue(elder.getAdmissionDate());
  }
}
