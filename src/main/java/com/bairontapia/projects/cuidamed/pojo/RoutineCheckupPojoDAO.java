package com.bairontapia.projects.cuidamed.pojo;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IOneToManyDAO;
import com.bairontapia.projects.cuidamed.daotemplate.IReadAndWriteDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.bson.types.ObjectId;

public class RoutineCheckupPojoDAO implements IOneToManyDAO<RoutineCheckupPOJO, ObjectId>,
    IReadAndWriteDAO<RoutineCheckupPOJO, ObjectId> {

  private static final RoutineCheckupPojoDAO INSTANCE;
  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<RoutineCheckupPOJO> COLLECTION;

  static {
    INSTANCE = new RoutineCheckupPojoDAO();
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("routine_checkup", RoutineCheckupPOJO.class);
  }

  public static RoutineCheckupPojoDAO getInstance() {
    return INSTANCE;
  }

  @Override
  public void save(RoutineCheckupPOJO routineCheckupPOJO) {
    COLLECTION.insertOne(routineCheckupPOJO);
  }

  @Override
  public Optional<RoutineCheckupPOJO> find(ObjectId objectId) {
    return Optional.empty();
  }

  @Override
  public Collection<RoutineCheckupPOJO> findAll() {
    return COLLECTION.find().into(new ArrayList<>());
  }

  @Override
  public Collection<RoutineCheckupPOJO> findAll(ObjectId elderId) {
    return COLLECTION.find(eq("elderId", elderId)).sort(descending("checkupDate"))
        .into(new ArrayList<>());
  }
}
