package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.disease.medicationadministration.MedicationAdministration;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

public class MedicationAdministrationPOJO {

  @Getter
  @Setter
  private LocalDateTime estimatedDateTime;
  @Getter
  @Setter
  private LocalDateTime realDatetime;
  @Getter
  @Setter
  private String status;

  public MedicationAdministrationPOJO() {
  }

  public MedicationAdministrationPOJO(final MedicationAdministration medicationAdministration) {
    this.estimatedDateTime = medicationAdministration.estimatedDateTime();
    this.realDatetime = medicationAdministration.realDatetime();
    this.status = medicationAdministration.status().toString();
  }

}
