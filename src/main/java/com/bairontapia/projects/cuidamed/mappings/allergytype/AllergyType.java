package com.bairontapia.projects.cuidamed.mappings.allergytype;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum AllergyType {
  DRUGS("Medicamentos"),
  FOODS("Comidas"),
  INSECTS("Insectos"),
  LATEX("Látex"),
  MOLD("Moho"),
  PETS("Mascotas"),
  POLLEN("Polen");

  private static final AllergyType[] VALUES = values();
  private static final Map<String, AllergyType> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(AllergyType::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String name;

  AllergyType(String name) {
    this.name = name;
  }

  public static AllergyType[] getValues() {
    return VALUES;
  }

  public static AllergyType getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static AllergyType getValueFromName(final String name) {
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
