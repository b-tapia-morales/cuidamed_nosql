package com.bairontapia.projects.cuidamed.pojo;

import static com.mongodb.client.model.Filters.eq;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.daotemplate.IReadOnlyDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.bson.types.ObjectId;

public class ResponsiblePOJODAO implements IReadOnlyDAO<ResponsiblePOJO, ObjectId> {

  @Override
  public Optional<ResponsiblePOJO> find(ObjectId elderId) {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var e = elderColl.find(eq("_id", elderId)).first();
    return Optional.of(e.getResponsible());
  }

  @Override
  public Collection<ResponsiblePOJO> findAll() {
    var mongoConnection = MongoClientSingleton.getInstance();
    var database = mongoConnection.getDatabase("admin");
    var elderColl = database.getCollection("elder", ElderPOJO.class);
    var elders = elderColl.find().into(new ArrayList<>());
    var responsibleList = new ArrayList<ResponsiblePOJO>();
    elders.forEach(i -> responsibleList.add(i.getResponsible()));
    return responsibleList;
  }
}
