package com.bairontapia.projects.cuidamed;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.bairontapia.projects.cuidamed.connection.MongoConnectionSingleton;
import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.ElderPOJODAO;
import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPOJO;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.codecs.pojo.PojoCodecProvider;

public class CuidaMedApplication {

  public static void main(String... args) {
    var mongoLogger = Logger.getLogger("org.mongodb.driver");
    mongoLogger.setLevel(Level.WARNING);

    var mongoConnection = MongoConnectionSingleton.getConnection();

    //var elderColl = database.getCollection("elder", ElderPOJO.class);
    //var routineCheckupColl = database.getCollection("routine_checkup", RoutineCheckupPOJO.class);
    var routineCheckups = new ArrayList<RoutineCheckupPOJO>();
    var elderDao = new ElderPOJODAO();
    var e = elderDao.find("3988832-7");
    e.orElseThrow().setFirstName("el pepe");
    elderDao.update(e.get());
    System.out.println(elderDao.find("3988832-7"));
    //System.out.println(elderDao.findAll());
    /*
    var e = elderColl.find(eq("rut", "3988832-7")).first();
    var rc = routineCheckupColl.find(eq("elderId", e.getId())).into(routineCheckups);
    System.out.println(e);
    System.out.println(e.getResponsible().toString());
     */

    //System.out.println(routineCheckups);
    /*
    var database = mongoClient.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    elderColl.drop();
    var routineCheckupColl = database.getCollection("routine_checkup", RoutineCheckupPOJO.class);
    routineCheckupColl.drop();
    var administrationColl = database.getCollection("medication_administration",
        MedicationAdministrationPOJO.class);
    administrationColl.drop();
    for (var elder : ElderDAO.getInstance().findAll()) {
      var elderId = new ObjectId();
      var diagnostics = DiagnosticDAO.getInstance().findAll(elder.rut());
      var diagnosticPOJOS = new ArrayList<DiagnosticPOJO>();
      for (var diagnostic : diagnostics) {
        var disease = DiseaseDAO.getInstance().find(diagnostic.diseaseName()).orElseThrow();
        var prescriptions =
            MedicationPrescriptionDAO.getInstance().findByRutAndDiseaseName(elder.rut(),
                disease.name());
        var prescriptionPOJOS = new ArrayList<MedicationPrescriptionPOJO>();
        for (var prescription : prescriptions) {
          var medication =
              MedicationDAO.getInstance().find(prescription.medicationName()).orElseThrow();
          var administrations =
              MedicationAdministrationDAO.getInstance().findByRutAndMedicationName(elder.rut(),
                  medication.name());
          var administrationPOJOS = administrations.stream()
              .map(MedicationAdministrationPOJO::new).toList();
          administrationColl.insertMany(administrationPOJOS);
          var administrationIds =
              administrationPOJOS.stream().map(MedicationAdministrationPOJO::getId).toList();
          prescriptionPOJOS.add(
              new MedicationPrescriptionPOJO(prescription, medication, administrationIds));
        }
        diagnosticPOJOS.add(new DiagnosticPOJO(diagnostic, disease, prescriptionPOJOS));
      }
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
      routineCheckupColl.createIndex(Indexes.hashed("elderId"));
      elderColl.createIndex(Indexes.hashed("{diagnostics.medicationPrescriptions"
          + ".administrationIds:1}"));
    }
     */

  }
}
