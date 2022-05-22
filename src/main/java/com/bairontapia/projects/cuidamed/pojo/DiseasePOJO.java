package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.relational.disease.Disease;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

public class DiseasePOJO {

  @Getter
  private ObjectId id;
  @Getter
  @Setter
  private String name;
  @Getter
  @Setter
  private String diseaseType;
  @Getter
  @Setter
  private Boolean isChronic;

  public DiseasePOJO() {
  }

  public DiseasePOJO(final Disease disease) {
    this.id = new ObjectId();
    this.name = disease.name();
    this.diseaseType = disease.diseaseType().toString();
    this.isChronic = disease.isChronic();
  }
}
