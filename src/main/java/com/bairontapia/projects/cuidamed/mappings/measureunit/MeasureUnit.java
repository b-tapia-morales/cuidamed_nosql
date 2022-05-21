package com.bairontapia.projects.cuidamed.mappings.measureunit;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum MeasureUnit {
  MG("Mg"),
  ML("Ml");

  private static final MeasureUnit[] VALUES = values();
  private static final Map<String, MeasureUnit> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(MeasureUnit::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String value;

  MeasureUnit(final String value) {
    this.value = value;
  }

  public static MeasureUnit[] getValues() {
    return VALUES;
  }

  public static MeasureUnit getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static MeasureUnit getValueFromName(final String name) {
    if (!MAP.containsKey(name)) {
      throw new IllegalArgumentException();
    }
    return MAP.get(name);
  }

  public int getIndex() {
    return ordinal() + 1;
  }
}
