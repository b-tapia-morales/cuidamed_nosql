package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.disease.Disease;
import com.bairontapia.projects.cuidamed.disease.diagnostic.Diagnostic;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class DiagnosticPOJO {

  @Getter
  @Setter
  private LocalDate date;
  @Getter
  @Setter
  private DiseasePOJO disease;
  @Getter
  @Setter
  private List<MedicationPrescriptionPOJO> medicationPrescriptions;

  public DiagnosticPOJO() {
  }

  public DiagnosticPOJO(final Diagnostic diagnostic, final Disease disease,
                        final List<MedicationPrescriptionPOJO> medicationPrescriptions) {
    this.date = diagnostic.prescriptionDate();
    this.disease = new DiseasePOJO(disease);
    this.medicationPrescriptions = medicationPrescriptions;
  }
}
