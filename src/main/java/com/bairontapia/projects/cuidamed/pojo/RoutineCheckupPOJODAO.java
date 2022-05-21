package com.bairontapia.projects.cuidamed.pojo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IOneToManyDAO;
import com.bairontapia.projects.cuidamed.daotemplate.IReadAndWriteDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.bson.types.ObjectId;

public class RoutineCheckupPOJODAO
    implements IOneToManyDAO<RoutineCheckupPOJO, ObjectId>,
    IReadAndWriteDAO<RoutineCheckupPOJO, ObjectId> {

  @Override
  public void save(RoutineCheckupPOJO routineCheckupPOJO) {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("admin");
    var rcColl = database.getCollection("routine_checkup", RoutineCheckupPOJO.class);
    rcColl.insertOne(routineCheckupPOJO);
  }

  @Override
  public Optional<RoutineCheckupPOJO> find(ObjectId objectId) {
    return Optional.empty();
  }

  @Override
  public Collection<RoutineCheckupPOJO> findAll() {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("admin");
    var rcColl = database.getCollection("routine_checkup", RoutineCheckupPOJO.class);
    return rcColl.find().into(new ArrayList<>());
  }

  @Override
  public Collection<RoutineCheckupPOJO> findAll(ObjectId elderId) {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("admin");
    var rcColl = database.getCollection("routine_checkup", RoutineCheckupPOJO.class);
    return rcColl.find(eq("elderId", elderId)).sort(descending("checkupDate"))
        .into(new ArrayList<>());
  }
}
