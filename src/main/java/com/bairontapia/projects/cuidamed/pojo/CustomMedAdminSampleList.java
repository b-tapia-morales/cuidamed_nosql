package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;

public class CustomMedAdminSampleList {
  @Getter private final List<CustomMedicationAdministrationSample> medicationAdministrations;
  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<Document> COLLECTION;

  static {
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("elder");
  }

  public CustomMedAdminSampleList() {
    this.medicationAdministrations = this.generateListOfAdministrations();
  }

  public Pair<LocalDateTime, LocalDateTime> findInterval(LocalDate date, int from, int to) {
    if (from == 0 && to == 0) {
      return Pair.of(date.atStartOfDay(), LocalDateTime.of(date, LocalTime.of(23,59)));
    }
    if (from == 0) {
      return Pair.of(
          LocalDateTime.of(date, LocalTime.now()),
          LocalDateTime.of(date, LocalTime.now()).plusHours(to));
    }
    if (to == 0) {
      return Pair.of(
          LocalDateTime.of(date, LocalTime.now()).minusHours(from),
          LocalDateTime.of(date, LocalTime.now()));
    }
    return Pair.of(
        LocalDateTime.of(date, LocalTime.of(from, 0).minusSeconds(1)), LocalDateTime.of(date, LocalTime.of(to, 59)));
  }

  public List<CustomMedicationAdministrationSample> findbyHourDifference(
      LocalDate date, int from, int to) {
    var pair = this.findInterval(date,from,to);
    var filteredList = new ArrayList<CustomMedicationAdministrationSample>();
    for (CustomMedicationAdministrationSample ma : this.getMedicationAdministrations()) {
      if ((ma.getIntakeDateTime().isAfter(pair.getLeft())
          && ma.getIntakeDateTime().isBefore(pair.getRight()))) {
        filteredList.add(ma);
      }
    }
    return filteredList;
  }

  public List<CustomMedicationAdministrationSample> generateListOfAdministrations() {
    var unwindedDocuments =
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
    var docList = new ArrayList<Document>();
    for (Document document : unwindedDocuments) {
      docList.add(document);
    }
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
              .atZone(ZoneId.of("UTC"))
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
