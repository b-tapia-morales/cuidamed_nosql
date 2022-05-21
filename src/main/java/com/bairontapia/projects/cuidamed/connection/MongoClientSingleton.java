package com.bairontapia.projects.cuidamed.connection;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoClientSingleton {

  private static final MongoClient MONGO_CLIENT;

  static {
    var codecRegistry =
        fromRegistries(MongoClient.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    MONGO_CLIENT = new MongoClient(
        "localhost:27017", MongoClientOptions.builder().codecRegistry(codecRegistry).build());
  }

  private MongoClientSingleton() {
  }

  public static MongoClient getInstance() {
    return MONGO_CLIENT;
  }
}
