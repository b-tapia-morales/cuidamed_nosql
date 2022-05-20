package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.disease.medication.Medication;
import com.bairontapia.projects.cuidamed.disease.medicationprescription.MedicationPrescription;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class MedicationPrescriptionPOJO {

  @Getter
  @Setter
  private MedicationPOJO medication;
  @Getter
  @Setter
  private LocalDate startDate;
  @Getter
  @Setter
  private LocalDate endDate;
  @Getter
  @Setter
  private Short quantity;
  @Getter
  @Setter
  private List<MedicationAdministrationPOJO> medicationAdministrations;

  public MedicationPrescriptionPOJO() {
  }

  public MedicationPrescriptionPOJO(final MedicationPrescription medicationPrescription,
      final Medication medication,
      final List<MedicationAdministrationPOJO> medicationAdministrations) {
    this.medication = new MedicationPOJO(medication);
    this.startDate = medicationPrescription.startDate();
    this.endDate = medicationPrescription.endDate();
    this.quantity = medicationPrescription.quantity();
    this.medicationAdministrations = medicationAdministrations;
  }
}
