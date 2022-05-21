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
import com.bairontapia.projects.cuidamed.pojo.ElderPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicalRecordPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicationAdministrationPOJO;
import com.bairontapia.projects.cuidamed.pojo.MedicationPrescriptionPOJO;
import com.bairontapia.projects.cuidamed.pojo.ResponsiblePOJO;
import com.bairontapia.projects.cuidamed.pojo.RoutineCheckupPOJO;
import com.mongodb.client.model.Indexes;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class Migration {

  private Migration() {
  }

  private static List<DiagnosticPOJO> retrieveDiagnostics(final String rut)
      throws SQLException, IOException {
    var diagnostics = DiagnosticDAO.getInstance().findAll(rut);
    var diagnosticPOJOS = new ArrayList<DiagnosticPOJO>();
    for (var diagnostic : diagnostics) {
      var disease = DiseaseDAO.getInstance().find(diagnostic.diseaseName()).orElseThrow();
      var prescriptions =
          MedicationPrescriptionDAO.getInstance().findByRutAndDiseaseName(rut, disease.name());
      var prescriptionPOJOS = new ArrayList<MedicationPrescriptionPOJO>();
      for (var prescription : prescriptions) {
        var medication =
            MedicationDAO.getInstance().find(prescription.medicationName()).orElseThrow();
        var administrations =
            MedicationAdministrationDAO.getInstance()
                .findByRutAndMedicationName(rut, medication.name());
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
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    elderColl.drop();
    var routineCheckupColl = database.getCollection("routine_checkup", RoutineCheckupPOJO.class);
    routineCheckupColl.drop();
    var administrationColl = database.getCollection("medication_administration",
        MedicationAdministrationPOJO.class);
    administrationColl.drop();
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
      routineCheckupColl.createIndex(Indexes.hashed("elderId"));
    }
  }

}
