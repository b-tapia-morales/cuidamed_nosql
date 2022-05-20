package com.bairontapia.projects.cuidamed.connection;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoConnectionSingleton {
    private static MongoClient mongoClient;
    private static MongoConnectionSingleton mongoConnectionSingleton;

    private MongoConnectionSingleton(){
        var pojoCodecRegistry =
                fromRegistries(
                        MongoClient.getDefaultCodecRegistry(),
                        fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        mongoClient =
                new MongoClient(
                        "localhost:27017",
                        MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
    }

    public static MongoClient getConnection(){
        if(mongoConnectionSingleton == null){
            mongoConnectionSingleton = new MongoConnectionSingleton();
        }
        return mongoClient;
    }
}
