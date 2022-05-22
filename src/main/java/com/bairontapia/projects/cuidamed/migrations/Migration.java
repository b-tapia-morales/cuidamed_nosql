package com.bairontapia.projects.cuidamed.migrations;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.disease.DiseaseDAO;
import com.bairontapia.projects.cuidamed.disease.diagnostic.DiagnosticDAO;
import com.bairontapia.projects.cuidamed.disease.medication.MedicationDAO;
import com.bairontapia.projects.cuidamed.disease.medicationadministration.MedicationAdministrationDAO;
import com.bairontapia.projects.cuidamed.disease.medicationprescription.MedicationPrescriptionDAO;
import com.bairontapia.projects.cuidamed.medicalrecord.MedicalRecordDAO;
import com.bairontapia.projects.cuidamed.medicalrecord.routinecheckup.RoutineCheckupDAO;
import com.bairontapia.projects.cuidamed.person.elder.ElderDAO;
import com.bairontapia.projects.cuidamed.person.responsible.ResponsibleDAO;
import com.bairontapia.projects.cuidamed.pojo.DiagnosticPOJO;
import com.bairontapia.projects.cuidamed.pojo.DiseasePOJO;
import com.bairontapia.projects.cuidamed.pojo.DiseasePojoDAO;
import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicalRecordPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicationAdministrationPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicationPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicationPojoDAO;
import com.bairontapia.projects.cuidamed.pojo.MedicationPrescriptionPOJO;
import com.bairontapia.projects.cuidamed.pojo.ResponsiblePOJO;
import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPOJO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class Migration {

  private static final MongoClient MONGO_CLIENT;
  private static final MongoDatabase DATABASE;

  static {
    MONGO_CLIENT = MongoClientSingleton.getInstance();
    DATABASE = MONGO_CLIENT.getDatabase("admin");
  }

  private Migration() {
  }

  private static void createDiseaseCollection() throws SQLException, IOException {
    var diseaseColl = DATABASE.getCollection("disease", DiseasePOJO.class);
    diseaseColl.drop();
    var diseases = DiseaseDAO.getInstance().findAll().stream().map(DiseasePOJO::new).toList();
    diseaseColl.insertMany(diseases);
    diseaseColl.createIndex(Indexes.hashed("name"));
  }

  private static void createMedicationCollection() throws SQLException, IOException {
    var medicationColl = DATABASE.getCollection("medication", MedicationPOJO.class);
    medicationColl.drop();
    var medications = MedicationDAO.getInstance().findAll().stream().map(MedicationPOJO::new)
        .toList();
    medicationColl.insertMany(medications);
    medicationColl.createIndex(Indexes.hashed("name"));
  }

  private static List<DiagnosticPOJO> retrieveDiagnostics(final String rut)
      throws SQLException, IOException {
    var diagnostics = DiagnosticDAO.getInstance().findAll(rut);
    var diagnosticPOJOS = new ArrayList<DiagnosticPOJO>();
    for (var diagnostic : diagnostics) {
      var disease = DiseasePojoDAO.getInstance().find(diagnostic.diseaseName()).orElseThrow();
      var prescriptions =
          MedicationPrescriptionDAO.getInstance().findByRutAndDiseaseName(rut, disease.getName());
      var prescriptionPOJOS = new ArrayList<MedicationPrescriptionPOJO>();
      for (var prescription : prescriptions) {
        var medication =
            MedicationPojoDAO.getInstance().find(prescription.medicationName()).orElseThrow();
        var administrations =
            MedicationAdministrationDAO.getInstance()
                .findByRutAndMedicationName(rut, medication.getName());
        var administrationPOJOS = administrations.stream()
            .map(MedicationAdministrationPOJO::new).toList();
        prescriptionPOJOS.add(
            new MedicationPrescriptionPOJO(prescription, medication, administrationPOJOS));
      }
      diagnosticPOJOS.add(new DiagnosticPOJO(diagnostic, disease, prescriptionPOJOS));
    }
    return diagnosticPOJOS;
  }

  public static void performMigration() throws SQLException, IOException {
    var client = MongoClientSingleton.getInstance();
    var database = client.getDatabase("admin");
    createDiseaseCollection();
    createMedicationCollection();
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    elderColl.drop();
    var routineCheckupColl = database.getCollection("routine_checkup", RoutineCheckupPOJO.class);
    routineCheckupColl.drop();
    for (var elder : ElderDAO.getInstance().findAll()) {
      var elderId = new ObjectId();
      var diagnosticPOJOS = retrieveDiagnostics(elder.rut());
      var responsible = ResponsibleDAO.getInstance().find(elder.responsibleRut()).orElseThrow();
      var responsiblePOJO = new ResponsiblePOJO(responsible);
      var medicalRecord = MedicalRecordDAO.getInstance().find(elder.rut()).orElseThrow();
      var medicalRecordPOJO = new MedicalRecordPOJO(medicalRecord);
      var elderPOJO = new ElderPOJO(elderId, elder, responsiblePOJO, medicalRecordPOJO,
          diagnosticPOJOS);
      elderColl.insertOne(elderPOJO);
      var routineCheckupPOJOS =
          RoutineCheckupDAO.getInstance().findAll(medicalRecord.rut()).stream()
              .map(e -> new RoutineCheckupPOJO(e, elderId)).toList();
      routineCheckupColl.insertMany(routineCheckupPOJOS);
      elderColl.createIndex(Indexes.hashed("rut"));
      routineCheckupColl.createIndex(Indexes.hashed("elderId"));
      routineCheckupColl.createIndex(Indexes.descending("checkupDate"));
    }
  }

}
