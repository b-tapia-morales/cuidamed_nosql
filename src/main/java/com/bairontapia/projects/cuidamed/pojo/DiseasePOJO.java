package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.disease.Disease;
import lombok.Getter;
import lombok.Setter;

public class DiseasePOJO {

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
    this.name = disease.name();
    this.diseaseType = disease.diseaseType().toString();
    this.isChronic = disease.isChronic();
  }
}
