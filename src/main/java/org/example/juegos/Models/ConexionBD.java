package org.example.juegos.Models;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class ConexionBD {
    private static String host = "mongodb://localhost:27017";

    public ConexionBD() {

    }

    public static MongoDatabase conexion() {
        MongoDatabase database = null;
        try {
            MongoClient mongoClient = MongoClients.create(host);
            database = mongoClient.getDatabase("adrian");
            System.out.println("Estás conectado");

        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        return database;
    }
}
