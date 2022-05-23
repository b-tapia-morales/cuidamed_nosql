package com.bairontapia.projects.cuidamed.pojo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IOneToManyDAO;
import com.bairontapia.projects.cuidamed.daotemplate.IReadAndWriteDAO;
import java.util.Collection;
import java.util.Optional;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;

public class DiagnosticPOJODAO
    implements IOneToManyDAO<DiagnosticPOJO, ObjectId>, IReadAndWriteDAO<DiagnosticPOJO, ObjectId> {

  private static final DiagnosticPOJODAO INSTANCE;
  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;

  static {
    INSTANCE = new DiagnosticPOJODAO();
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
  }

  public static DiagnosticPOJODAO getInstance(){
    return INSTANCE;
  }
  @Override
  public void save(DiagnosticPOJO diagnosticPOJO) {
  }

  // using this one instead
  public void saveIntoElder(ObjectId elderId, DiagnosticPOJO diagnosticPOJO) {
    var elderColl = DATABASE.getCollection("elder", ElderPOJO.class);
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
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("admin");

    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("_id", elderId)).first();
    return elder.getDiagnostics();
  }
}
