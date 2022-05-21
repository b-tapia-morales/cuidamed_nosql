package com.bairontapia.projects.cuidamed.pojo;

import static com.mongodb.client.model.Filters.eq;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IReadOnlyDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class ResponsiblePOJODAO implements IReadOnlyDAO<ResponsiblePOJO, String> {

  @Override
  public Optional<ResponsiblePOJO> find(String elderRut) {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var e = elderColl.find(eq("rut", elderRut)).first();
    return Optional.of(e.getResponsible());
  }

  @Override
  public Collection<ResponsiblePOJO> findAll() {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("Cuidamed_DB");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elders = elderColl.find().into(new ArrayList<>());
    var responsibleList = new ArrayList<ResponsiblePOJO>();
    elders.forEach(i -> responsibleList.add(i.getResponsible()));
    return responsibleList;
  }
}
