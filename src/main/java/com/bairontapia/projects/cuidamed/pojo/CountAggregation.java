package com.bairontapia.projects.cuidamed.pojo;

import com.bairontapia.projects.cuidamed.connection.MongoClientSingleton;
import com.bairontapia.projects.cuidamed.pojo.AdministrationAggregation.DocumentChoice;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bson.Document;

public class CountAggregation {

  private static final MongoClient CLIENT;
  private static final MongoDatabase DATABASE;
  private static final MongoCollection<Document> COLLECTION;

  static {
    CLIENT = MongoClientSingleton.getInstance();
    DATABASE = CLIENT.getDatabase("admin");
    COLLECTION = DATABASE.getCollection("elder");
  }

  public static Map<String, Long> aggregateByCount(final DocumentChoice choice, final String key) {
    var field = String.format("%s.%s", AdministrationAggregation.getDocumentPath(choice), key);
    var documents = AdministrationAggregation.getUnwoundDocuments(choice);
    var group = List.of(
        new Document("$group",
            new Document("_id", field).append("count", new Document("$sum", 1L))),
        new Document("$sort", new Document("count", -1L)));
    var list = Stream.of(documents, group).flatMap(Collection::stream).toList();
    return COLLECTION
        .aggregate(list)
        .into(new ArrayList<>())
        .stream()
        .collect(
            Collectors.toMap(e -> (String) e.get("_id"), e -> (long) e.get("count"), (a, b) -> a,
                LinkedHashMap::new))
        ;
  }

}
