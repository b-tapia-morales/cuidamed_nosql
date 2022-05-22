package com.bairontapia.projects.cuidamed.pojo;

import static com.mongodb.client.model.Filters.eq;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IReadOnlyDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class MedicationPojoDAO implements IReadOnlyDAO<MedicationPOJO, String> {

  private static final MedicationPojoDAO INSTANCE;
  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<MedicationPOJO> COLLECTION;

  static {
    INSTANCE = new MedicationPojoDAO();
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("medication", MedicationPOJO.class);
  }

  public static MedicationPojoDAO getInstance() {
    return INSTANCE;
  }

  @Override
  public Optional<MedicationPOJO> find(String name) {
    var medication = COLLECTION.find(eq("name", name)).first();
    return Optional.ofNullable(medication);
  }

  @Override
  public Collection<MedicationPOJO> findAll() {
    return COLLECTION.find().into(new ArrayList<>());
  }
}
