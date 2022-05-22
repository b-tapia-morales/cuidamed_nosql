package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.relational.disease.medication.Medication;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

public class MedicationPOJO {

  @Getter
  private ObjectId id;
  @Getter
  @Setter
  private String name;
  @Getter
  @Setter
  private String administrationRoute;
  @Getter
  @Setter
  private String measureUnit;
  @Getter
  @Setter
  private String pharmaceuticalForm;

  public MedicationPOJO() {
  }

  public MedicationPOJO(final Medication medication) {
    this.id = new ObjectId();
    this.name = medication.name();
    this.administrationRoute = medication.administrationRoute().toString();
    this.measureUnit = medication.measureUnit().toString();
    this.pharmaceuticalForm = medication.pharmaceuticalForm().toString();
  }

  @Override
  public String toString() {
    return "MedicationPOJO{" +
        "name='" + name + '\'' +
        ", administrationRoute='" + administrationRoute + '\'' +
        ", measureUnit='" + measureUnit + '\'' +
        ", pharmaceuticalForm='" + pharmaceuticalForm + '\'' +
        '}';
  }
}
