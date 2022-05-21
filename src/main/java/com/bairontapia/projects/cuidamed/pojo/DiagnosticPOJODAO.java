package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.connection.MongoConnectionSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IOneToManyDAO;
import com.bairontapia.projects.cuidamed.daotemplate.IReadAndWriteDAO;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class DiagnosticPOJODAO
    implements IOneToManyDAO<DiagnosticPOJO, ObjectId>, IReadAndWriteDAO<DiagnosticPOJO, ObjectId> {
  @Override
  public void save(DiagnosticPOJO diagnosticPOJO) {}

  // using this one instead
  public void saveIntoElder(ObjectId elderId, DiagnosticPOJO diagnosticPOJO) {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("_id", elderId)).first();
    var diagnostics = elder.getDiagnostics();
    diagnostics.add(diagnosticPOJO);

    elderColl.updateOne(
        eq("rut", elder.getRut()),
        combine(
            set("rut", elder.getRut()),
            set("firstName", elder.getFirstName()),
            set("lastName", elder.getLastName()),
            set("secondLastName", elder.getSecondLastName()),
            set("gender", elder.getGender()),
            set("isActive", elder.getIsActive()),
            set("birthDate", elder.getBirthDate()),
            set("responsible", elder.getResponsible()),
            set("medicalRecord", elder.getMedicalRecord()),
            set("diagnostics", diagnostics)));
  }

  @Override
  public Optional<DiagnosticPOJO> find(ObjectId elderId) {
    return Optional.empty();
  }

  @Override
  public Collection<DiagnosticPOJO> findAll() {
    return null;
  }

  @Override
  public Collection<DiagnosticPOJO> findAll(ObjectId elderId) {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("_id", elderId)).first();
    return elder.getDiagnostics();
  }
}
