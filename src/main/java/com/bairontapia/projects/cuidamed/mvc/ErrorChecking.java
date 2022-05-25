package com.bairontapia.projects.cuidamed.mvc;

import java.io.IOException;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public interface ErrorChecking {

  StringBuilder getMessageBuilder();

  void trimFields();

  boolean fieldsAreEmpty();

  boolean fieldsAreValid();

  boolean fieldsAreCorrect();

  void buildErrorMessage();

  void performAction(Event event) throws IOException;

  default Alert createErrorAlert() {
    final var alert = new Alert(AlertType.ERROR);
    alert.setHeaderText("Error en el llenado de campos");
    alert.setContentText(getMessageBuilder().toString());
    return alert;
  }

  default void check(Event event) throws IOException {
    getMessageBuilder().setLength(0);
    trimFields();
    if (fieldsAreEmpty()) {
      getMessageBuilder().append("Existen campos sin selección o sin rellenar");
      createErrorAlert().show();
      return;
    }
    if (!fieldsAreValid()) {
      getMessageBuilder().append("Hay campos con formatos incorrectos");
      createErrorAlert().show();
      return;
    }
    if (!fieldsAreCorrect()) {
      buildErrorMessage();
      createErrorAlert().show();
      return;
    }
    performAction(event);
  }

}
