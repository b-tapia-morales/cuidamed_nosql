package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.connection.MongoConnectionSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IReadOnlyDAO;
import com.mongodb.Block;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lte;

public class ResponsiblePOJODAO implements IReadOnlyDAO<ResponsiblePOJO, String> {
  @Override
  public Optional<ResponsiblePOJO> find(String rut) {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var e = elderColl.find(eq("responsible.rut", rut)).first();
    return Optional.of(e.getResponsible());
  }

  @Override
  public Collection<ResponsiblePOJO> findAll() {
    var mongoConnection = MongoConnectionSingleton.getConnection();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elders = elderColl.find().into(new ArrayList<>());
    var responsibleList = new ArrayList<ResponsiblePOJO>();
    elders.forEach(i -> responsibleList.add(i.getResponsible()));
    return responsibleList;
  }
}
