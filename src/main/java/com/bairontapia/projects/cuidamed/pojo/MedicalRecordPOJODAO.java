package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.connection.MongoConnectionSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IReadOnlyDAO;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MedicalRecordPOJODAO implements IReadOnlyDAO<MedicalRecordPOJO, ObjectId> {
  @Override
  public Optional<MedicalRecordPOJO> find(ObjectId elderId) {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elder = elderColl.find(eq("_id", elderId)).first();
    return Optional.of(elder.getMedicalRecord());
  }

  @Override
  public Collection<MedicalRecordPOJO> findAll() {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elders = elderColl.find().into(new ArrayList<>());
    var medicalRecords = new ArrayList<MedicalRecordPOJO>();
    elders.forEach(i -> medicalRecords.add(i.getMedicalRecord()));
    return medicalRecords;
  }
}
