package com.bairontapia.projects.cuidamed.mappings.administrationroute;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum AdministrationRoute {
  ORAL("Vía Oral"),
  PARENTAL("Vía Parental");

  private static final AdministrationRoute[] VALUES = values();
  private static final Map<String, AdministrationRoute> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(AdministrationRoute::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String route;

  AdministrationRoute(final String route) {
    this.route = route;
  }

  public static AdministrationRoute[] getValues() {
    return VALUES;
  }

  public static AdministrationRoute getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static AdministrationRoute getValueFromName(final String name) {
    if (!MAP.containsKey(name)) {
      throw new IllegalArgumentException();
    }
    return MAP.get(name);
  }

  @Override
  public String toString() {
    return route;
  }

  public int getIndex() {
    return ordinal() + 1;
  }
}
