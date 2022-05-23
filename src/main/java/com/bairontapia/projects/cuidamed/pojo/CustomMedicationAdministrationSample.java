package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.utils.validation.RutUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

public class CustomMedicationAdministrationSample {
  @Getter @Setter private String rut;
  @Getter @Setter private String fullName;
  @Getter @Setter private LocalDate diagnosticDate;
  @Getter @Setter private String medicationName;
  @Getter @Setter private LocalDateTime intakeDateTime;
  @Getter @Setter private String state;

  public CustomMedicationAdministrationSample(
      String rut,
      String fullName,
      LocalDate diagnosticDate,
      String medicationName,
      LocalDateTime intakeDateTime,
      String state) {
    this.rut = rut;
    this.fullName = fullName;
    this.diagnosticDate = diagnosticDate;
    this.medicationName = medicationName;
    this.intakeDateTime = intakeDateTime;
    this.state = state;
  }

  @Override
  public String toString() {
    return "CustomMedicationAdministrationSample{"
        + "rut='"
        + RutUtils.format(rut)
        + '\''
        + ", fullName='"
        + fullName
        + '\''
        + ", diagnosticDate="
        + diagnosticDate
        + ", medicationName='"
        + medicationName
        + '\''
        + ", intakeDateTime="
        + intakeDateTime
        + ", state='"
        + state
        + '\''
        + "}\n";
  }
}
