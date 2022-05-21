package com.bairontapia.projects.cuidamed.mappings.healthcaresystem;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum HealthCare {
  PUBLIC("Fonasa"),
  PRIVATE("Isapre");

  private static final HealthCare[] VALUES = values();
  private static final Map<String, HealthCare> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(HealthCare::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String name;

  HealthCare(final String name) {
    this.name = name;
  }

  public static HealthCare[] getValues() {
    return VALUES;
  }

  public static HealthCare getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static HealthCare getValueFromName(final String name) {
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
