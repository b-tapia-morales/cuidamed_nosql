package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.daotemplate.ICrudDAO;
import org.bson.types.ObjectId;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.ICrudDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class ElderPOJODAO implements ICrudDAO<ElderPOJO, ObjectId> {
  @Override
  public void update(ElderPOJO elderPOJO) {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);

    elderColl.updateOne(
        eq("_id", elderPOJO.getId()),
        combine(
            set("rut", elderPOJO.getRut()),
            set("firstName", elderPOJO.getFirstName()),
            set("lastName", elderPOJO.getLastName()),
            set("secondLastName", elderPOJO.getSecondLastName()),
            set("gender", elderPOJO.getGender()),
            set("isActive", elderPOJO.getIsActive()),
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
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("_id", elderId)).first();
    return Optional.of(elder);
  }

  // only for testing
  public Optional<ElderPOJO> findByRut(String rut) {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("rut", rut)).first();
    return Optional.of(elder);
  }

  @Override
  public Collection<ElderPOJO> findAll() {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elders = new ArrayList<ElderPOJO>();
    var findQuery = elderColl.find().into(elders);
    return elders;
  }
}
