package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.utils.validation.RutUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

public record CustomMedicationAdministrationSample(@Getter String rut,
                                                   @Getter String fullName,
                                                   @Getter String diseaseName,
                                                   @Getter LocalDate diagnosticDate,
                                                   @Getter String medicationName,
                                                   @Getter LocalDateTime intakeDateTime,
                                                   @Getter String state) {

  @Override
  public String toString() {
    return "CustomMedicationAdministrationSample{" +
        "rut='" + rut + '\'' +
        ", fullName='" + fullName + '\'' +
        ", diseaseName='" + diseaseName + '\'' +
        ", diagnosticDate=" + diagnosticDate +
        ", medicationName='" + medicationName + '\'' +
        ", intakeDateTime=" + intakeDateTime +
        ", state='" + state + '\'' +
        "}\n";
  }
}
