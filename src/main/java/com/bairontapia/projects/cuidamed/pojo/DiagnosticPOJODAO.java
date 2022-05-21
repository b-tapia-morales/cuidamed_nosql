package com.bairontapia.projects.cuidamed.pojo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IOneToManyDAO;
import com.bairontapia.projects.cuidamed.daotemplate.IReadAndWriteDAO;
import java.util.Collection;
import java.util.Optional;

public class DiagnosticPOJODAO
    implements IOneToManyDAO<DiagnosticPOJO, String>, IReadAndWriteDAO<DiagnosticPOJO, String> {

  @Override
  public void save(DiagnosticPOJO diagnosticPOJO) {
  }

  // using this one instead
  public void saveIntoElder(String elderRut, DiagnosticPOJO diagnosticPOJO) {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("rut", elderRut)).first();
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
  public Optional<DiagnosticPOJO> find(String elderRut) {
    return Optional.empty();
  }

  @Override
  public Collection<DiagnosticPOJO> findAll() {
    return null;
  }

  @Override
  public Collection<DiagnosticPOJO> findAll(String elderRut) {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("rut", elderRut)).first();
    return elder.getDiagnostics();
  }
}
