package com.bairontapia.projects.cuidamed.mappings.pharmaceuticalform;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public enum PharmaceuticalForm {
  CAPSULES("Cápsulas"),
  TABLETS("Comprimidos"),
  CHEWING_GUMS("Gomas de mascar"),
  SUSPENSIONS("Suspensiones"),
  SYRINGES("Jeringas");

  private static final PharmaceuticalForm[] VALUES = values();
  private static final Map<String, PharmaceuticalForm> MAP = Arrays.stream(VALUES).collect(
      Collectors.toMap(PharmaceuticalForm::toString, Function.identity(), (v1, v2) -> v1,
          LinkedHashMap::new));

  private final String form;

  PharmaceuticalForm(final String form) {
    this.form = form;
  }

  public static PharmaceuticalForm[] getValues() {
    return VALUES;
  }

  public static PharmaceuticalForm getValueFromIndex(final int index) {
    if (index < 1 || index > VALUES.length) {
      throw new IllegalArgumentException();
    }
    return VALUES[index - 1];
  }

  public static PharmaceuticalForm getValueFromName(final String name) {
    if (!MAP.containsKey(name)) {
      throw new IllegalArgumentException();
    }
    return MAP.get(name);
  }

  @Override
  public String toString() {
    return form;
  }

  public int getIndex() {
    return ordinal() + 1;
  }
}
