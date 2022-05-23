package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.Administration;
import com.bairontapia.projects.cuidamed.pojo.AdministrationGeneration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class IntakeListView {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy/MM/dd");
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(
      "yyyy/MM/dd HH:mm:ss");

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
  private TableColumn<Administration, String> rutColumn;
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
    var hours = List.of(1, 2, 3, 4, 6, 8, 12);
    lowerBoundComboBox.getItems().addAll(hours);
    upperBoundComboBox.getItems().addAll(hours);
    rutColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().rut()));
    fullNameColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().fullName()));
    diseaseColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().diseaseName()));
    diagnosticDateColumn.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().diagnosticDate().format(DATE_FORMATTER)));
    medicationColumn.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().medicationName()));
    intakeDateTimeColumn.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().intakeDateTime().format(DATE_TIME_FORMATTER)));
    intakeStatusColumn.setCellValueFactory(
        e -> new SimpleStringProperty(e.getValue().intakeStatus()));
  }

  @FXML
  public void onLowerBoundSelected(MouseEvent actionEvent) {
    var isDisabled = !lowerBoundCheckBox.isSelected();
    lowerBoundComboBox.setDisable(isDisabled);
    lowerBoundLabel.setDisable(isDisabled);
  }

  @FXML
  public void onUpperBoundSelected(MouseEvent actionEvent) {
    var isDisabled = !upperBoundCheckBox.isSelected();
    upperBoundComboBox.setDisable(isDisabled);
    upperBoundLabel.setDisable(isDisabled);
  }

  @FXML
  public void onRefreshViewClicked(MouseEvent event) {
    tableView.getItems().clear();
    var isLowerBoundDisabled =
        !lowerBoundCheckBox.isSelected() || lowerBoundComboBox.getSelectionModel().isEmpty();
    var isUpperBoundDisabled =
        !upperBoundCheckBox.isSelected() || upperBoundComboBox.getSelectionModel().isEmpty();
    List<Administration> administrations = null;
    if (isLowerBoundDisabled && isUpperBoundDisabled) {
      administrations = AdministrationGeneration.filterByHourDifference(0, 0);
    } else if (isLowerBoundDisabled) {
      administrations = AdministrationGeneration.filterByHourDifference(0,
          upperBoundComboBox.getValue());
    } else if (isUpperBoundDisabled) {
      administrations = AdministrationGeneration.filterByHourDifference(
          lowerBoundComboBox.getValue(), 0);
    } else {
      administrations =
          AdministrationGeneration.filterByHourDifference(lowerBoundComboBox.getValue(),
              upperBoundComboBox.getValue());
    }
    tableView.getItems().addAll(administrations);
  }
}
