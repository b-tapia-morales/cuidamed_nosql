package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.utils.validation.RutUtils;
import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class EldersView {

  @FXML
  private AnchorPane eldersListPane;
  @FXML
  private TableColumn<ElderPOJO, String> rutColumn;
  @FXML
  private TableColumn<ElderPOJO, String> fullNameColumn;
  @FXML
  private TableColumn<ElderPOJO, LocalDate> birthDateColumn;
  @FXML
  private TableColumn<ElderPOJO, Integer> ageColumn;
  @FXML
  private TableColumn<ElderPOJO, String> genderColumn;
  @FXML
  private TableColumn<ElderPOJO, Object> admissionColumn;
  @FXML
  private Button viewRegisterButton;
  @FXML
  private Button addRegisterButton;
  @FXML
  private TableView<ElderPOJO> tableView;

  public void initialize() {
    rutColumn
        .setCellValueFactory(e -> new SimpleStringProperty(RutUtils.format(e.getValue().getRut())));
    fullNameColumn
        .setCellValueFactory(e -> new SimpleStringProperty(String.join(" ",
            e.getValue().getFirstName(), e.getValue().getLastName(),
            e.getValue().getSecondLastName())));
    birthDateColumn.setCellValueFactory(
        e -> new SimpleObjectProperty<>(e.getValue().getBirthDate()));
    ageColumn.setCellValueFactory(e -> new SimpleIntegerProperty(e.getValue().getAge()).asObject());
    genderColumn.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getGender()));
    admissionColumn.setCellValueFactory(
        e -> new SimpleObjectProperty<>(e.getValue().getAdmissionDate()));
  }

  @FXML
  public void loadPanel(final MouseEvent event) {
    if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 2
        || tableView.getSelectionModel().isEmpty()) {
      return;
    }
  }

  public void onViewRegistryClicked(final MouseEvent mouseEvent) {
  }

  public void onAddRegistryClicked(final MouseEvent mouseEvent) {
  }
}
