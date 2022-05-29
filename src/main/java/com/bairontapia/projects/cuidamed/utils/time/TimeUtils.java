package com.bairontapia.projects.cuidamed.utils.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.apache.commons.lang3.tuple.Pair;

public class TimeUtils {

  private static final ZoneId DEFAULT_ZONE;
  private static final DateTimeFormatter DATE_FORMATTER;
  private static final DateTimeFormatter DATE_TIME_FORMATTER;
  private static final DateTimeFormatter TIME_FORMATTER;

  static {
    DEFAULT_ZONE = ZoneId.of("UTC");
    DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
  }

  private TimeUtils() {
  }

  public static String format(final LocalDate localDate) {
    return localDate.format(DATE_FORMATTER);
  }

  public static String format(final LocalTime localTime) {
    return localTime.format(TIME_FORMATTER);
  }

  public static String format(final LocalDateTime localDateTime) {
    return localDateTime.format(DATE_TIME_FORMATTER);
  }

  public static LocalDate toLocalDate(final Date date) {
    return LocalDate.ofInstant(date.toInstant(), DEFAULT_ZONE);
  }

  public static LocalDateTime toLocalDateTime(final Date date) {
    return LocalDateTime.ofInstant(date.toInstant(), DEFAULT_ZONE);
  }

  public static LocalTime toLocalTime(final Date date) {
    return toLocalDateTime(date).toLocalTime();
  }

  public static Pair<LocalDateTime, LocalDateTime> createInterval(final LocalDateTime dateTime,
      final int from, final int to) {
    var roundedDateTime = dateTime.truncatedTo(ChronoUnit.SECONDS);
    if (from == 0 && to == 0) {
      return Pair.of(roundedDateTime.minusHours(12), roundedDateTime.plusHours(12));
    }
    if (from == 0) {
      return Pair.of(roundedDateTime, roundedDateTime.plusHours(to));
    }
    if (to == 0) {
      return Pair.of(roundedDateTime.minusHours(from), roundedDateTime);
    }
    return Pair.of(roundedDateTime.minusHours(from), roundedDateTime.plusHours(to));
  }

}
