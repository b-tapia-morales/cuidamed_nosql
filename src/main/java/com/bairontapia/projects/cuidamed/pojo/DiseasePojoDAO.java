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

public class DiseasePojoDAO implements IReadOnlyDAO<DiseasePOJO, String> {

  private static final DiseasePojoDAO INSTANCE;
  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<DiseasePOJO> COLLECTION;

  static {
    INSTANCE = new DiseasePojoDAO();
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("disease", DiseasePOJO.class);
  }

  public static DiseasePojoDAO getInstance() {
    return INSTANCE;
  }

  @Override
  public Optional<DiseasePOJO> find(String name) {
    var disease = COLLECTION.find(eq("name", name)).first();
    return Optional.ofNullable(disease);
  }

  @Override
  public Collection<DiseasePOJO> findAll() {
    return COLLECTION.find().into(new ArrayList<>());
  }
}
