package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.Administration;
import com.bairontapia.projects.cuidamed.pojo.AdministrationAggregation;
import com.bairontapia.projects.cuidamed.utils.time.TimeUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

public class IntakeListView implements ErrorChecking {

  private static final ZoneId LOCAL_ZONE = ZoneId.of("Chile/Continental");

  @Getter
  private final StringBuilder messageBuilder = new StringBuilder();

  @FXML
  private DatePicker datePicker;
  @FXML
  private TextField timeField;
  @FXML
  private CheckBox lowerBoundCheckBox;
  @FXML
  private ComboBox<Integer> lowerBoundComboBox;
  @FXML
  private CheckBox upperBoundCheckBox;
  @FXML
  private Label lowerBoundLabel;
  @FXML
  private ComboBox<Integer> upperBoundComboBox;
  @FXML
  private Label upperBoundLabel;
  @FXML
  private Button refreshView;
  @FXML
  private TableView<Administration> tableView;
  @FXML
  private TableColumn<Administration, String> fullNameColumn;
  @FXML
  private TableColumn<Administration, String> diseaseColumn;
  @FXML
  private TableColumn<Administration, String> diagnosticDateColumn;
  @FXML
  private TableColumn<Administration, String> medicationColumn;
  @FXML
  private TableColumn<Administration, String> intakeDateTimeColumn;
  @FXML
  private TableColumn<Administration, String> intakeStatusColumn;

  public void initialize() {
    var hours = List.of(1, 2, 3, 4, 6, 8, 12, 24);
    datePicker.setValue(LocalDate.now(LOCAL_ZONE));
    timeField.setText(TimeUtils.format(LocalTime.now(LOCAL_ZONE)));
    lowerBoundComboBox.getItems().addAll(hours);
    upperBoundComboBox.getItems().addAll(hours);
    fullNameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().fullName()));
    diseaseColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().diseaseName()));
    diagnosticDateColumn.setCellValueFactory(
        e -> new SimpleStringProperty(TimeUtils.format(e.getValue().diagnosticDate())));
    medicationColumn.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().medicationName()));
    intakeDateTimeColumn.setCellValueFactory(
        e -> new SimpleStringProperty(TimeUtils.format(e.getValue().intakeDateTime())));
    intakeStatusColumn.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().intakeStatus()));
  }

  @Override
  public void trimFields() {
    timeField.setText(StringUtils.trim(timeField.getText()));
  }

  @Override
  public boolean fieldsAreEmpty() {
    return datePicker.getValue() == null || StringUtils.isEmpty(timeField.getText());
  }

  @Override
  public boolean fieldsAreValid() {
    try {
      LocalTime.parse(timeField.getText());
    } catch (DateTimeParseException exception) {
      return false;
    }
    return true;
  }

  @Override
  public boolean fieldsAreCorrect() {
    return true;
  }

  @Override
  public void buildErrorMessage() {
    // Unneeded
  }

  @Override
  public void performAction(Event event) {
    var date = datePicker.getValue();
    var time = LocalTime.parse(timeField.getText());
    var dateTime = LocalDateTime.of(date, time);
    var isLowerBoundDisabled =
        !lowerBoundCheckBox.isSelected() || lowerBoundComboBox.getSelectionModel().isEmpty();
    var isUpperBoundDisabled =
        !upperBoundCheckBox.isSelected() || upperBoundComboBox.getSelectionModel().isEmpty();
    List<Administration> administrations;
    if (isLowerBoundDisabled && isUpperBoundDisabled) {
      administrations = AdministrationAggregation.filterByHourDifference(dateTime, 0, 0);
    } else if (isLowerBoundDisabled) {
      administrations = AdministrationAggregation.filterByHourDifference(dateTime, 0,
          upperBoundComboBox.getValue());
    } else if (isUpperBoundDisabled) {
      administrations = AdministrationAggregation.filterByHourDifference(dateTime,
          lowerBoundComboBox.getValue(), 0);
    } else {
      administrations =
          AdministrationAggregation.filterByHourDifference(dateTime, lowerBoundComboBox.getValue(),
              upperBoundComboBox.getValue());
    }
    tableView.getItems().clear();
    tableView.getItems().addAll(administrations);
    tableView.getItems().sort(Comparator.comparing(Administration::intakeDateTime));
  }

  @FXML
  public void onLowerBoundSelected() {
    var isDisabled = !lowerBoundCheckBox.isSelected();
    lowerBoundComboBox.setDisable(isDisabled);
    lowerBoundLabel.setDisable(isDisabled);
  }

  @FXML
  public void onUpperBoundSelected() {
    var isDisabled = !upperBoundCheckBox.isSelected();
    upperBoundComboBox.setDisable(isDisabled);
    upperBoundLabel.setDisable(isDisabled);
  }

  @FXML
  public void onRefreshViewClicked(MouseEvent event) throws IOException {
    check(event);
  }

}
