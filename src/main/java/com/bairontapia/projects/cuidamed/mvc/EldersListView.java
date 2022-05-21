package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.ElderPojoDAO;
import com.bairontapia.projects.cuidamed.utils.validation.RutUtils;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EldersListView {

  private static final ClassLoader CLASS_LOADER = Thread.currentThread().getContextClassLoader();

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
    tableView.getItems().addAll(ElderPojoDAO.getInstance().findAll());
  }

  @FXML
  public void onTableRegistryDoubleClicked(final MouseEvent event) throws IOException {
    if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() != 2 ||
        tableView.getSelectionModel().isEmpty()) {
      return;
    }
    loadPanelAndData(event);
  }

  public void onViewRegistryButtonClicked(final MouseEvent event) throws IOException {
    if (tableView.getSelectionModel().isEmpty()) {
      return;
    }
    loadPanelAndData(event);
  }

  public void onAddRegistryButtonClicked(final MouseEvent event) {

  }

  private void loadPanelAndData(final MouseEvent event) throws IOException {
    var elder = tableView.getSelectionModel().getSelectedItem();
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
}
