package com.bairontapia.projects.cuidamed.mappings.dosagestatus;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum DosageStatus {
  UNDEFINED("Sin definir"),
  PENDING("Pendiente"),
  UNFULFILLED("Incumplido"),
  FULFILLED("Cumplido");

  private static final DosageStatus[] VALUES = values();
  private static final Map<String, DosageStatus> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(DosageStatus::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String status;

  DosageStatus(final String status) {
    this.status = status;
  }

  public static DosageStatus[] getValues() {
    return VALUES;
  }

  public static DosageStatus getValueFromIndex(final int index) {
    if (index < 0 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index];
  }

  public static DosageStatus getValueFromName(final String name) {
    if (!MAP.containsKey(name)) {
      throw new IllegalArgumentException();
    }
    return MAP.get(name);
  }

  @Override
  public String toString() {
    return status;
  }

  public int getIndex() {
    return ordinal() + 1;
  }
}
