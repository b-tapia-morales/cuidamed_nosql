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
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;

public class CustomMedAdminSampleList {

  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<Document> COLLECTION;
  private static final List<CustomMedicationAdministrationSample> ADMINISTRATIONS;

  static {
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("elder");
    ADMINISTRATIONS = new ArrayList<>();
  }

  public static void update() {
    ADMINISTRATIONS.clear();
    ADMINISTRATIONS.addAll(generateListOfAdministrations());
  }

  public static List<CustomMedicationAdministrationSample> getAdministrations() {
    return ADMINISTRATIONS;
  }

  public static LocalDateTime roundToNearestHour(final LocalDateTime current) {
    return current.getMinute() >= 30 ? current.truncatedTo(ChronoUnit.HOURS).plusHours(1) :
        current.truncatedTo(ChronoUnit.HOURS);
  }

  public static Pair<LocalDateTime, LocalDateTime> createInterval(int from, int to) {
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
    return Pair.of(roundToNearestHour(currentDateTime.minusHours(from)),
        roundToNearestHour(currentDateTime.plusHours(to)));
  }

  public static List<CustomMedicationAdministrationSample> findByHourDifference(int from, int to) {
    var pair = createInterval(from, to);
    var list = new ArrayList<CustomMedicationAdministrationSample>();
    for (var administration : ADMINISTRATIONS) {
      if ((administration.intakeDateTime().isAfter(pair.getLeft()) &&
          administration.intakeDateTime().isBefore(pair.getRight()))) {
        list.add(administration);
      }
    }
    return list;
  }

  public static List<CustomMedicationAdministrationSample> generateListOfAdministrations() {
    var unwindedDocuments =
        COLLECTION.aggregate(Arrays.asList(
            new Document("$unwind", new Document("path", "$diagnostics")),
            new Document("$unwind", new Document("path", "$diagnostics.medicationPrescriptions")),
            new Document("$unwind", new Document("path",
                "$diagnostics.medicationPrescriptions.medicationAdministrations"))));
    var docList = unwindedDocuments.into(new ArrayList<>());
    var list = new ArrayList<CustomMedicationAdministrationSample>();
    for (Document document : docList) {
      var elderRut = document.getString("rut");
      var firstName = document.getString("firstName");
      var lastName = document.getString("lastName");
      var secondLastName = document.getString("secondLastName");
      var fullName = String.join(" ", firstName, lastName, secondLastName);
      var diagnosticDate =
          document
              .get("diagnostics", Document.class)
              .getDate("date")
              .toInstant()
              .atZone(ZoneId.of("Chile/Continental"))
              .toLocalDate();
      var medication =
          document
              .get("diagnostics", Document.class)
              .get("medicationPrescriptions", Document.class)
              .get("medication", Document.class)
              .getString("name");
      var medAdmin =
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
      var ma =
          new CustomMedicationAdministrationSample(
              elderRut,
              fullName,
              diagnosticDate,
              medication,
              LocalDateTime.ofInstant(medAdmin.toInstant(), ZoneId.of("UTC")),
              intakeStatus);
      list.add(ma);
    }
    return list;
  }
}
