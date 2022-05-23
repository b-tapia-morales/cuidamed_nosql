package com.bairontapia.projects.cuidamed.pojo;

import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;

public class AdministrationGeneration {

  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<Document> COLLECTION;
  private static final List<Administration> ADMINISTRATIONS;

  static {
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("elder");
    ADMINISTRATIONS = generate();
  }

  public static void update() {
    ADMINISTRATIONS.clear();
    ADMINISTRATIONS.addAll(generate());
  }

  public static List<Administration> getAdministrations() {
    return ADMINISTRATIONS;
  }

  public static List<Administration> filterByRut(final String rut) {
    return ADMINISTRATIONS.stream().filter(e -> StringUtils.equals(e.rut(), rut)).toList();
  }

  public static List<Administration> filterByHourDifference(int from, int to) {
    var pair = createInterval(from, to);
    var list = new ArrayList<Administration>();
    for (var administration : ADMINISTRATIONS) {
      if ((administration.intakeDateTime().isAfter(pair.getLeft())
          && administration.intakeDateTime().isBefore(pair.getRight()))) {
        list.add(administration);
      }
    }
    return list;
  }

  private static LocalDateTime roundToNearestHour(final LocalDateTime current) {
    return current.getMinute() >= 30
        ? current.truncatedTo(ChronoUnit.HOURS).plusHours(1)
        : current.truncatedTo(ChronoUnit.HOURS);
  }

  private static Pair<LocalDateTime, LocalDateTime> createInterval(int from, int to) {
    var currentDate = LocalDate.now();
    if (from == 0 && to == 0) {
      return Pair.of(LocalDateTime.of(currentDate, MIN), LocalDateTime.of(currentDate, MAX));
    }
    var currentDateTime = LocalDateTime.now();
    if (from == 0) {
      return Pair.of(currentDateTime, roundToNearestHour(currentDateTime.plusHours(to)));
    }
    if (to == 0) {
      return Pair.of(roundToNearestHour(currentDateTime.minusHours(from)), currentDateTime);
    }
    return Pair.of(
        roundToNearestHour(currentDateTime.minusHours(from)),
        roundToNearestHour(currentDateTime.plusHours(to)));
  }

  private static List<Administration> generate() {
    var iterable =
        COLLECTION.aggregate(
            Arrays.asList(
                new Document("$unwind", new Document("path", "$diagnostics")),
                new Document(
                    "$unwind", new Document("path", "$diagnostics.medicationPrescriptions")),
                new Document(
                    "$unwind",
                    new Document(
                        "path",
                        "$diagnostics.medicationPrescriptions.medicationAdministrations"))));
    var documents = iterable.into(new ArrayList<>());
    var list = new ArrayList<Administration>();
    for (Document document : documents) {
      var id = document.getObjectId("_id");
      var rut = document.getString("rut");
      var firstName = document.getString("firstName");
      var lastName = document.getString("lastName");
      var secondLastName = document.getString("secondLastName");
      var fullName = String.join(" ", firstName, lastName, secondLastName);
      var diseaseName =
          document
              .get("diagnostics", Document.class)
              .get("disease", Document.class)
              .getString("name");
      var diagnosticDate =
          document
              .get("diagnostics", Document.class)
              .getDate("date")
              .toInstant()
              .atZone(ZoneId.of("Chile/Continental"))
              .toLocalDate();
      var medicationName =
          document
              .get("diagnostics", Document.class)
              .get("medicationPrescriptions", Document.class)
              .get("medication", Document.class)
              .getString("name");
      var intakeDateTime =
          document
              .get("diagnostics", Document.class)
              .get("medicationPrescriptions", Document.class)
              .get("medicationAdministrations", Document.class)
              .getDate("estimatedDateTime");
      var intakeStatus =
          document
              .get("diagnostics", Document.class)
              .get("medicationPrescriptions", Document.class)
              .get("medicationAdministrations", Document.class)
              .getString("status");
      var administration =
          new Administration(rut, fullName, diseaseName, diagnosticDate, medicationName,
              LocalDateTime.ofInstant(intakeDateTime.toInstant(), ZoneId.of("UTC")),
              intakeStatus);
      list.add(administration);
    }
    return list;
  }
}
