package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPOJO;
import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPojoDAO;
import com.bairontapia.projects.cuidamed.relational.medicalrecord.routinecheckup.RoutineCheckup;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.util.Precision;

public class RoutineCheckupDialog {

  private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

  @Getter
  @Setter
  private ElderPOJO elder;

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

  @FXML
  public void addRoutineCheckup(ActionEvent event) throws IOException {
    trimFields();
    if (checkIncorrectFields()) {
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

  private void trimFields() {
    height.setText(StringUtils.trim(height.getText()));
    weight.setText(StringUtils.trim(weight.getText()));
    heartRate.setText(StringUtils.trim(heartRate.getText()));
    diastolicPressure.setText(StringUtils.trim(diastolicPressure.getText()));
    systolicPressure.setText(StringUtils.trim(systolicPressure.getText()));
    bodyTemperature.setText(StringUtils.trim(bodyTemperature.getText()));
  }

  private boolean checkIncorrectFields() {
    return NumberUtils.isCreatable(height.getText()) && NumberUtils.isCreatable(weight.getText()) &&
        NumberUtils.isCreatable(heartRate.getText()) &&
        NumberUtils.isCreatable(diastolicPressure.getText()) &&
        NumberUtils.isCreatable(systolicPressure.getText()) &&
        NumberUtils.isCreatable(bodyTemperature.getText());
  }

}
