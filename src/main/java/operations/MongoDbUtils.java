package operations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDbUtils {

    private static final String MONGO_CONNECTION_STRING = "mongodb://localhost:27017";

    public static MongoCollection<Document> getMongoClient(String database, String collectionName) {
        MongoClient mongoClient = MongoClients.create(MONGO_CONNECTION_STRING);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
        return mongoDatabase.getCollection(collectionName);
    }
}