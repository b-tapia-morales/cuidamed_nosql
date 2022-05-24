package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPOJO;
import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPojoDAO;
import com.bairontapia.projects.cuidamed.relational.medicalrecord.routinecheckup.RoutineCheckup;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Precision;

public class RoutineCheckupDialog {

  private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

  private final StringBuilder errorMessageBuilder = new StringBuilder();

  @Getter
  @Setter
  private ElderPOJO elder;
  @Getter
  @Setter
  private RoutineCheckupPOJO lastRoutineCheckup;

  @FXML
  private DatePicker checkupDatePicker;
  @FXML
  private TextField height;
  @FXML
  private TextField weight;
  @FXML
  private TextField heartRate;
  @FXML
  private TextField diastolicPressure;
  @FXML
  private TextField systolicPressure;
  @FXML
  private TextField bodyTemperature;
  @FXML
  private Button add;
  @FXML
  private Button cancel;

  public void initialize() {
    checkupDatePicker.setValue(LocalDate.now());
  }

  public void receiveData(final ElderPOJO elder) {
    var routineCheckup = RoutineCheckupPojoDAO.getInstance().findMax(elder.getId()).orElseThrow();
    setElder(elder);
    setLastRoutineCheckup(routineCheckup);
    fillFields();
  }

  @FXML
  public void addRoutineCheckup(ActionEvent event) throws IOException {
    errorMessageBuilder.setLength(0);
    trimFields();
    if (fieldsAreEmpty()) {
      errorMessageBuilder.append("Existen campos sin selección o sin rellenar");
      createErrorAlert().show();
      return;
    }
    if (!fieldsAreValid()) {
      errorMessageBuilder.append("Hay campos con formatos incorrectos");
      createErrorAlert().show();
      return;
    }
    if (!fieldsAreCorrect()) {
      buildErrorMessage();
      createErrorAlert().show();
      return;
    }
    var checkupDate = checkupDatePicker.getValue();
    var heightValue = Precision.round(Double.parseDouble(height.getText()), 2);
    var weightValue = Precision.round(Double.parseDouble(weight.getText()), 1);
    var bmiValue = Precision.round(weightValue / Math.pow(heightValue, 2), 1);
    var heartRateValue = Integer.parseInt(heartRate.getText());
    var diastolicValue = Integer.parseInt(diastolicPressure.getText());
    var systolicValue = Integer.parseInt(systolicPressure.getText());
    var temperatureValue = Precision.round(Double.parseDouble(bodyTemperature.getText()), 1);
    var routineCheckup = new RoutineCheckup("", checkupDate, heightValue, weightValue, bmiValue,
        heartRateValue, diastolicValue, systolicValue, temperatureValue);
    var routineCheckupPOJO = new RoutineCheckupPOJO(routineCheckup, getElder().getId());
    RoutineCheckupPojoDAO.getInstance().save(routineCheckupPOJO);
    loadPreviousScene(event);
  }

  @FXML
  public void cancelRoutineCheckup(ActionEvent event) throws IOException {
    loadPreviousScene(event);
  }

  private void loadPreviousScene(ActionEvent event) throws IOException {
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

  private void fillFields() {
    height.setText(lastRoutineCheckup.getHeight().toString());
    weight.setText(lastRoutineCheckup.getWeight().toString());
    heartRate.setText(lastRoutineCheckup.getHeartRate().toString());
    diastolicPressure.setText(lastRoutineCheckup.getDiastolicPressure().toString());
    systolicPressure.setText(lastRoutineCheckup.getSystolicPressure().toString());
    bodyTemperature.setText(lastRoutineCheckup.getBodyTemperature().toString());
  }

  private Alert createErrorAlert() {
    final var alert = new Alert(AlertType.ERROR);
    alert.setHeaderText("Error en el llenado de campos");
    alert.setContentText(errorMessageBuilder.toString());
    return alert;
  }

  private void trimFields() {
    height.setText(StringUtils.trim(height.getText()));
    weight.setText(StringUtils.trim(weight.getText()));
    heartRate.setText(StringUtils.trim(heartRate.getText()));
    diastolicPressure.setText(StringUtils.trim(diastolicPressure.getText()));
    systolicPressure.setText(StringUtils.trim(systolicPressure.getText()));
    bodyTemperature.setText(StringUtils.trim(bodyTemperature.getText()));
  }

  private boolean fieldsAreEmpty() {
    return checkupDatePicker.getValue() == null || StringUtils.isEmpty(height.getText())
        || StringUtils.isEmpty(weight.getText()) || StringUtils.isEmpty(heartRate.getText())
        || StringUtils.isEmpty(diastolicPressure.getText()) || StringUtils.isEmpty(
        systolicPressure.getText()) || StringUtils.isEmpty(bodyTemperature.getText());
  }

  private boolean fieldsAreValid() {
    return Doubles.tryParse(height.getText()) != null && Doubles.tryParse(weight.getText()) != null
        && Ints.tryParse(heartRate.getText()) != null
        && Ints.tryParse(diastolicPressure.getText()) != null
        && Ints.tryParse(systolicPressure.getText()) != null
        && Doubles.tryParse(bodyTemperature.getText()) != null;
  }

  private boolean fieldsAreCorrect() {
    var date = checkupDatePicker.getValue();
    var dayDifference = ChronoUnit.DAYS.between(date, LocalDate.now());
    if (dayDifference < 0) {
      return false;
    }
    var heightValue = Precision.round(Double.parseDouble(height.getText()), 2);
    var weightValue = Precision.round(Double.parseDouble(weight.getText()), 1);
    var bmiValue = Precision.round(weightValue / Math.pow(heightValue, 2), 1);
    if (bmiValue < 16.5 || bmiValue > 35.5) {
      return false;
    }
    var heartValue = Integer.parseInt(heartRate.getText());
    if (heartValue < 50 || heartValue > 110) {
      return false;
    }
    var diastolicValue = Integer.parseInt(diastolicPressure.getText());
    if (diastolicValue < 70 || diastolicValue > 120) {
      return false;
    }
    var systolicValue = Integer.parseInt(systolicPressure.getText());
    if (systolicValue < 110 || systolicValue > 180) {
      return false;
    }
    var temperatureValue = Precision.round(Double.parseDouble(bodyTemperature.getText()), 1);
    return temperatureValue >= 35.0 && temperatureValue <= 41.0;
  }

  private void buildErrorMessage() {
    var date = checkupDatePicker.getValue();
    var dayDifference = ChronoUnit.DAYS.between(date, LocalDate.now());
    if (dayDifference < 0) {
      errorMessageBuilder.append(
          "La fecha del chequeo rutinario no puede ser posterior a la actual");
    }
    var heightValue = Precision.round(Double.parseDouble(height.getText()), 2);
    var weightValue = Precision.round(Double.parseDouble(weight.getText()), 1);
    var bmiValue = Precision.round(weightValue / Math.pow(heightValue, 2), 1);
    if (bmiValue < 16.5 || bmiValue > 35.5) {
      errorMessageBuilder.append(
          "No se aceptan valores de IMC que indiquen delgadez severa u obesidad severa");
    }
    var heartValue = Integer.parseInt(heartRate.getText());
    if (heartValue < 50 || heartValue > 110) {
      errorMessageBuilder.append(String.format(
          "Los valores mínimos y máximos de ritmo cardiaco son %d y %d respectivamente", 50, 110));
    }
    var diastolicValue = Integer.parseInt(diastolicPressure.getText());
    if (diastolicValue < 70 || diastolicValue > 120) {
      errorMessageBuilder.append(String.format(
          "Los valores mínimos y máximos de presión diastólica son %d y %d respectivamente", 70,
          120));
    }
    var systolicValue = Integer.parseInt(systolicPressure.getText());
    if (systolicValue < 110 || systolicValue > 180) {
      errorMessageBuilder.append(String.format(
          "Los valores mínimos y máximos de presión sistólica son %d y %d respectivamente", 110,
          180));
    }
    var temperatureValue = Double.parseDouble(bodyTemperature.getText());
    if (temperatureValue < 35.0 || temperatureValue > 41.0) {
      errorMessageBuilder.append(String.format(
          "Los valores mínimos y máximos de temperatura corporal son %.1f y %.1f respectivamente",
          35.0, 41.0));
    }
  }

}
