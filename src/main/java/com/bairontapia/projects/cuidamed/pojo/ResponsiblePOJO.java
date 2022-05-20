package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.person.responsible.Responsible;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

public class ResponsiblePOJO {

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
  private Integer mobilePhone;

  public ResponsiblePOJO() {
  }

  public ResponsiblePOJO(Responsible responsible) {
    rut = responsible.rut();
    firstName = responsible.firstName();
    lastName = responsible.lastName();
    secondLastName = responsible.secondLastName();
    birthDate = responsible.birthDate();
    gender = responsible.gender().toString();
    age = responsible.age();
    mobilePhone = responsible.mobilePhone();
  }

  @Override
  public String toString() {
    return "ResponsiblePOJO{" +
        "rut='" + rut + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", secondLastName='" + secondLastName + '\'' +
        ", birthDate=" + birthDate +
        ", age=" + age +
        ", gender='" + gender + '\'' +
        ", mobilePhone=" + mobilePhone +
        '}';
  }
}
