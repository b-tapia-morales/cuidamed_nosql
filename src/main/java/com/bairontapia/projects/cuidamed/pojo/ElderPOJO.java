package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.person.elder.Elder;
import com.bairontapia.projects.cuidamed.utils.validation.RutUtils;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

public class ElderPOJO {

  @Getter
  @Setter
  private ObjectId id;
  @Getter
  @Setter
  private String rut;
  @Getter
  @Setter
  private String firstName;
  @Getter
  @Setter
  private String lastName;
  @Getter
  @Setter
  private String secondLastName;
  @Getter
  @Setter
  private LocalDate birthDate;
  @Getter
  @Setter
  private Integer age;
  @Getter
  @Setter
  private String gender;
  @Getter
  @Setter
  private Boolean isActive;
  @Getter
  @Setter
  private LocalDate admissionDate;
  @Getter
  @Setter
  private ResponsiblePOJO responsible;
  @Getter
  @Setter
  private MedicalRecordPOJO medicalRecord;
  @Getter
  @Setter
  private List<DiagnosticPOJO> diagnostics;

  public ElderPOJO() {
  }

  public ElderPOJO(final ObjectId id, final Elder elder, final ResponsiblePOJO responsiblePOJO,
                   final MedicalRecordPOJO medicalRecordPOJO, final List<DiagnosticPOJO> diagnostics) {
    this.id = id;
    this.rut = elder.rut();
    this.firstName = elder.firstName();
    this.lastName = elder.lastName();
    this.secondLastName = elder.secondLastName();
    this.birthDate = elder.birthDate();
    this.gender = elder.gender().toString();
    this.age = elder.age();
    this.isActive = elder.isActive();
    this.admissionDate = elder.admissionDate();
    this.responsible = responsiblePOJO;
    this.medicalRecord = medicalRecordPOJO;
    this.diagnostics = diagnostics;
  }

  @Override
  public String toString() {
    return String.format("""
                        Rut:\t\t\t\t\t\t\t\t\t%s
                        Nombre completo:\t\t\t%s
                        Fecha de nacimiento:\t%s
                        Edad:\t\t\t\t\t\t\t\t\t%s
                        Sexo:\t\t\t\t\t\t\t\t\t%s
                        Activo:\t\t\t\t\t\t\t\t%s
                        Fecha de admisión:\t\t%s
                                                        
                        Ficha Médica:
                        %s
                                """, RutUtils.format(rut),
            StringUtils.joinWith(" ", firstName, lastName, secondLastName), birthDate, age, gender,
            isActive.equals(Boolean.TRUE) ? "Sí" : "No", admissionDate, medicalRecord);
  }
}