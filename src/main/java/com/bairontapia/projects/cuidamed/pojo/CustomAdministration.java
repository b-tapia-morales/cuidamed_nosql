package com.bairontapia.projects.cuidamed.pojo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class CustomAdministration {

  @Getter
  private final String diseaseName;
  @Getter
  private final LocalDate diagnosticDate;
  @Getter
  private final String medicationName;
  @Getter
  private final LocalDateTime intakeDateTime;
  @Getter
  private final String intakeStatus;

  public CustomAdministration(String diseaseName, LocalDate diagnosticDate, String medicationName,
      LocalDateTime intakeDateTime, String intakeStatus) {
    this.diseaseName = diseaseName;
    this.diagnosticDate = diagnosticDate;
    this.medicationName = medicationName;
    this.intakeDateTime = intakeDateTime;
    this.intakeStatus = intakeStatus;
  }

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
