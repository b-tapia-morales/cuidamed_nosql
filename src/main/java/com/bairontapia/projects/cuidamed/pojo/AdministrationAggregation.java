package com.bairontapia.projects.cuidamed.pojo;

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

public class AdministrationAggregation {

  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<Document> COLLECTION;
  private static final String ACTION_KEY;
  private static final String PATH_KEY;
  private static final String DIAGNOSTIC_KEY;
  private static final String PRESCRIPTION_KEY;
  private static final String ADMINISTRATION_KEY;
  private static final String DIAGNOSTIC_PATH;
  private static final String PRESCRIPTION_PATH;
  private static final String ADMINISTRATION_PATH;
  private static final List<Administration> ADMINISTRATIONS;

  static {
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("elder");
    ACTION_KEY = "$unwind";
    PATH_KEY = "path";
    DIAGNOSTIC_KEY = "diagnostics";
    PRESCRIPTION_KEY = "medicationPrescriptions";
    ADMINISTRATION_KEY = "medicationAdministrations";
    DIAGNOSTIC_PATH = String.format("$%s", DIAGNOSTIC_KEY);
    PRESCRIPTION_PATH = String.format("$%s.%s", DIAGNOSTIC_KEY, PRESCRIPTION_KEY);
    ADMINISTRATION_PATH = String.format("$%s.%s.%s", DIAGNOSTIC_KEY, PRESCRIPTION_KEY,
        ADMINISTRATION_KEY);
    ADMINISTRATIONS = generateAdministrations();
  }

  private AdministrationAggregation() {
  }

  public static void update() {
    ADMINISTRATIONS.clear();
    ADMINISTRATIONS.addAll(generateAdministrations());
  }

  public static List<Administration> getAdministrations() {
    return ADMINISTRATIONS;
  }

  public static List<Administration> filterByRut(final String rut) {
    return ADMINISTRATIONS.stream().filter(e -> StringUtils.equals(e.rut(), rut)).toList();
  }

  public static List<Administration> filterByHourDifference(final LocalDateTime dateTime,
      final int from, final int to) {
    var pair = createInterval(dateTime, from, to);
    var list = new ArrayList<Administration>();
    for (var administration : ADMINISTRATIONS) {
      var intake = administration.intakeDateTime();
      if ((intake.isEqual(pair.getLeft()) || intake.isAfter(pair.getLeft())) &&
          (intake.isEqual(pair.getRight()) || intake.isBefore(pair.getRight()))) {
        list.add(administration);
      }
    }
    return list;
  }

  private static Pair<LocalDateTime, LocalDateTime> createInterval(final LocalDateTime dateTime,
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

  private static List<Document> generateDocuments() {
    var iterable =
        COLLECTION.aggregate(
            Arrays.asList(new Document(ACTION_KEY, new Document(PATH_KEY, DIAGNOSTIC_PATH)),
                new Document(ACTION_KEY, new Document(PATH_KEY, PRESCRIPTION_PATH)),
                new Document(ACTION_KEY, new Document(PATH_KEY, ADMINISTRATION_PATH))));
    return iterable.into(new ArrayList<>());
  }

  private static Document getFromDiagnostics(final Document document) {
    return document
        .get(DIAGNOSTIC_KEY, Document.class);
  }

  private static Document getFromPrescriptions(final Document document) {
    return document
        .get(DIAGNOSTIC_KEY, Document.class)
        .get(PRESCRIPTION_KEY, Document.class);
  }

  private static Document getFromAdministrations(final Document document) {
    return document
        .get(DIAGNOSTIC_KEY, Document.class)
        .get(PRESCRIPTION_KEY, Document.class)
        .get(ADMINISTRATION_KEY, Document.class);
  }

  private static Document getFromDisease(final Document document) {
    return getFromDiagnostics(document).get("disease", Document.class);
  }

  private static Document getFromMedication(final Document document) {
    return getFromPrescriptions(document).get("medication", Document.class);
  }

  private static List<Administration> generateAdministrations() {
    var documents = generateDocuments();
    var list = new ArrayList<Administration>();
    for (var document : documents) {
      var rut = document.getString("rut");
      var firstName = document.getString("firstName");
      var lastName = document.getString("lastName");
      var secondLastName = document.getString("secondLastName");
      var fullName = String.join(" ", firstName, lastName, secondLastName);
      var diseaseName = getFromDisease(document)
          .getString("name");
      var diagnosticDate = getFromDiagnostics(document)
          .getDate("date");
      var medicationName = getFromMedication(document)
          .getString("name");
      var intakeDateTime = getFromAdministrations(document)
          .getDate("estimatedDateTime");
      var intakeStatus = getFromAdministrations(document)
          .getString("status");
      var administration =
          new Administration(rut, fullName, diseaseName,
              LocalDate.ofInstant(diagnosticDate.toInstant(), ZoneId.of("UTC")), medicationName,
              LocalDateTime.ofInstant(intakeDateTime.toInstant(), ZoneId.of("UTC")), intakeStatus);
      list.add(administration);
    }
    return list;
  }
}
