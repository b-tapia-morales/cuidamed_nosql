package com.bairontapia.projects.cuidamed.mappings.gender;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum Gender {
  MALE("Hombre"),
  FEMALE("Mujer"),
  NOT_KNOWN("Desconocido"),
  NOT_APPLICABLE("No aplica");

  private static final Gender[] VALUES = values();
  private static final Map<String, Gender> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(Gender::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String name;

  Gender(final String name) {
    this.name = name;
  }

  public static Gender getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static Gender getValueFromName(final String name) {
    if (!MAP.containsKey(name)) {
      throw new IllegalArgumentException();
    }
    return MAP.get(name);
  }

  public static Gender[] getValues() {
    return VALUES;
  }

  @Override
  public String toString() {
    return name;
  }

  public int getIndex() {
    return ordinal() + 1;
  }
}
