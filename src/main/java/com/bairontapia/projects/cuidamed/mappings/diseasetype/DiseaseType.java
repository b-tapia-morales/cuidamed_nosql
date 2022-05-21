package com.bairontapia.projects.cuidamed.mappings.diseasetype;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum DiseaseType {
  ONCOLOGICAL("Oncológica"),
  INFECTIOUS("Infecciosa"),
  BLOOD("De la Sangre"),
  INMUNE("Del Sistema Inmune"),
  ENDOCRINE("Endocrina"),
  MENTAL("Mental"),
  NERVOUS_SYSTEM("Del Sistema Nervioso"),
  OPHTHALMOLOGIST("Oftalmológica"),
  AUDITORY("Auditiva"),
  CARDIOVASCULAR("Cardiovascular"),
  RESPIRATORY("Respiratoria"),
  DIGESTIVE("Digestiva"),
  SKIN("De la piel"),
  GENITOURINARY_SYSTEM("Sistema Genitourinario"),
  CONGENITAL("Congénitas y Alteraciones Cromosómicas");

  private static final DiseaseType[] VALUES = values();
  private static final Map<String, DiseaseType> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(DiseaseType::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String name;

  DiseaseType(String name) {
    this.name = name;
  }

  public static DiseaseType[] getValues() {
    return VALUES;
  }

  public static DiseaseType getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static DiseaseType getValueFromName(final String name) {
    if (!MAP.containsKey(name)) {
      throw new IllegalArgumentException();
    }
    return MAP.get(name);
  }

  @Override
  public String toString() {
    return name;
  }

  public int getIndex() {
    return ordinal() + 1;
  }
}
