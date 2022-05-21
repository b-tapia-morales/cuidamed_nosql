package com.bairontapia.projects.cuidamed.mappings.bloodtype;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum BloodType {
  A_MINUS("A-"),
  A_PLUS("A+"),
  B_MINUS("B-"),
  B_PLUS("B+"),
  AB_MINUS("AB-"),
  AB_PLUS("AB+"),
  O_MINUS("O-"),
  O_PLUS("O+");

  private static final BloodType[] VALUES = values();
  private static final Map<String, BloodType> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(BloodType::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String name;

  BloodType(final String name) {
    this.name = name;
  }

  public static BloodType[] getValues() {
    return VALUES;
  }

  public static BloodType getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static BloodType getValueFromName(final String name) {
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
