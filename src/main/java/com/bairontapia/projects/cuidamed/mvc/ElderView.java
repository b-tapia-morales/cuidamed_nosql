package com.bairontapia.projects.cuidamed.mvc;

import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ElderView {

  @FXML
  private void receiveData(MouseEvent event) {
    Node node = (Node) event.getSource();
    Stage stage = (Stage) node.getScene().getWindow();
    var elder = (ElderPOJO) stage.getUserData();
  }

}
