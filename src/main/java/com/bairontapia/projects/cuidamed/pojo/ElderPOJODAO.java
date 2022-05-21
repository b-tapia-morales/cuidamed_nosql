package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.connection.MongoConnectionSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.ICrudDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class ElderPOJODAO implements ICrudDAO<ElderPOJO, String> {
  @Override
  public void update(ElderPOJO elderPOJO) {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);

    elderColl.updateOne(
        eq("rut", elderPOJO.getRut()),
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
  public void save(ElderPOJO elderPOJO) {}

  @Override
  public Optional<ElderPOJO> find(String rut) {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("rut", rut)).first();
    return Optional.of(elder);
  }

  @Override
  public Collection<ElderPOJO> findAll() {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elders = new ArrayList<ElderPOJO>();
    var findQuery = elderColl.find().into(elders);
    return elders;
  }
}