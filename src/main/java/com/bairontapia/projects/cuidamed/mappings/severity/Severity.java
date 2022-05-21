package com.bairontapia.projects.cuidamed.mappings.severity;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum Severity {
  MILD("Leve"),
  NORMAL("Normal"),
  SEVERE("Severa");

  private static final Severity[] VALUES = values();
  private static final Map<String, Severity> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(Severity::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String value;

  Severity(final String value) {
    this.value = value;
  }

  public static Severity[] getValues() {
    return VALUES;
  }

  public static Severity getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static Severity getValueFromName(final String name) {
    if (!MAP.containsKey(name)) {
      throw new IllegalArgumentException();
    }
    return MAP.get(name);
  }

  @Override
  public String toString() {
    return value;
  }

  public int getIndex() {
    return ordinal() + 1;
  }
}
