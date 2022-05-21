package com.bairontapia.projects.cuidamed.pojo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.ICrudDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.bson.types.ObjectId;

public class ElderPojoDAO implements ICrudDAO<ElderPOJO, ObjectId> {

  private static final ElderPojoDAO INSTANCE;
  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<ElderPOJO> COLLECTION;

  static {
    INSTANCE = new ElderPojoDAO();
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("elder", ElderPOJO.class);
  }

  public static ElderPojoDAO getInstance() {
    return INSTANCE;
  }

  @Override
  public void update(ElderPOJO elderPOJO) {
    COLLECTION.updateOne(eq("_id", elderPOJO.getId()),
        combine(set("rut", elderPOJO.getRut()), set("firstName", elderPOJO.getFirstName()),
            set("lastName", elderPOJO.getLastName()),
            set("secondLastName", elderPOJO.getSecondLastName()),
            set("gender", elderPOJO.getGender()), set("isActive", elderPOJO.getIsActive()),
            set("birthDate", elderPOJO.getBirthDate()),
            set("responsible", elderPOJO.getResponsible()),
            set("medicalRecord", elderPOJO.getMedicalRecord()),
            set("diagnostics", elderPOJO.getDiagnostics())));
  }

  @Override
  public void save(ElderPOJO elderPOJO) {
  }

  @Override
  public Optional<ElderPOJO> find(ObjectId elderId) {
    var elder = COLLECTION.find(eq("_id", elderId)).first();
    return Optional.ofNullable(elder);
  }

  public Optional<ElderPOJO> findByRut(String rut) {
    var elder = COLLECTION.find(eq("rut", rut)).first();
    return Optional.ofNullable(elder);
  }

  @Override
  public Collection<ElderPOJO> findAll() {
    return COLLECTION.find().into(new ArrayList<>());
  }
}
