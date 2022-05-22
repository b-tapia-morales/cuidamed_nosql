package com.bairontapia.projects.cuidamed.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public record CustomAdministration(@Getter String diseaseName,
                                   @Getter LocalDate diagnosticDate,
                                   @Getter String medicationName,
                                   @Getter LocalDateTime intakeDateTime,
                                   @Getter String intakeStatus) {

  public static List<CustomAdministration> map(List<DiagnosticPOJO> diagnostics) {
    var list = new ArrayList<CustomAdministration>();
    for (var diagnostic : diagnostics) {
      var diseaseName = diagnostic.getDisease().getName();
      var diagnosticDate = diagnostic.getDate();
      for (var prescription : diagnostic.getMedicationPrescriptions()) {
        var medicationName = prescription.getMedication().getName();
        for (var administration : prescription.getMedicationAdministrations()) {
          var intakeDateTime = administration.getEstimatedDateTime();
          var intakeStatus = administration.getStatus();
          list.add(new CustomAdministration(diseaseName, diagnosticDate, medicationName,
              intakeDateTime, intakeStatus));
        }
      }
    }
    return list;
  }

}
