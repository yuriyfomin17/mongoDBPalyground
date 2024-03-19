import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

import static com.mongodb.client.model.Filters.gte;

public class MongoDbConnector {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    public static void main(String[] args) {
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();

        executeCodeInTx(settings);
    }

    private static void updateManyField(MongoClientSettings settings){
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase siemConfigDB = mongoClient.getDatabase("SiemConfigDB");
                MongoCollection<Document> siemConfigCollection = siemConfigDB.getCollection("SiemConfig");
                Bson query = Filters.eq("balance", 1001L);
                Updates.combine(Updates.inc("balance", 1), Updates.set("account_status", "active"));
                Bson updates = Updates.inc("balance", 1);
                siemConfigCollection.updateMany(query, updates);
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
    private static void updateOneField(MongoClientSettings settings) {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase siemConfigDB = mongoClient.getDatabase("SiemConfigDB");
                MongoCollection<Document> siemConfigCollection = siemConfigDB.getCollection("SiemConfig");
                Bson query = Filters.eq("_id", new ObjectId("65f8753f82f0881df102fe26"));
                Bson updates = Updates.inc("balance", 1);
                siemConfigCollection.updateOne(query, updates);
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertMany(MongoClientSettings settings) {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                mongoClient.listDatabaseNames().forEach(System.out::println);
                MongoDatabase siemConfigDB = mongoClient.getDatabase("SiemConfigDB");
                MongoCollection<Document> siemConfigCollection = siemConfigDB.getCollection("SiemConfig");
                siemConfigCollection.insertMany(List.of(new Document().append("newField", "someTestValue2"), new Document().append("newField", "someTestValue3")));
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    private static void insertOne(MongoClientSettings settings) {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                mongoClient.listDatabaseNames().forEach(System.out::println);
                MongoDatabase siemConfigDB = mongoClient.getDatabase("SiemConfigDB");
                MongoCollection<Document> siemConfigCollection = siemConfigDB.getCollection("SiemConfig");
                siemConfigCollection.insertOne(new Document().append("balance", 1000));
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    private static void find(MongoClientSettings settings) {
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase eventsProcessingDB = mongoClient.getDatabase("EventsProcessingDB");
                MongoCollection<Document> eventsCollection = eventsProcessingDB.getCollection("Events");
                try (MongoCursor<Document> cursor = eventsCollection.find(gte("firstEventTime", 1710506814005L)).iterator()) {
                    while (cursor.hasNext()) {
                        System.out.printf(cursor.next().toJson());
                    }
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    private static void deleteOne(MongoClientSettings settings){
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase eventsProcessingDB = mongoClient.getDatabase("EventsProcessingDB");
                MongoCollection<Document> eventsCollection = eventsProcessingDB.getCollection("Events");
                eventsCollection.deleteOne(Filters.eq("eventId", "70VC-54"));
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    private static void executeCodeInTx(MongoClientSettings settings){
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase siemConfigDB = mongoClient.getDatabase("SiemConfigDB");
                MongoCollection<Document> siemConfigDBCollection = siemConfigDB.getCollection("SiemConfig");
                TransactionBody<UpdateResult> updateResultTransactionBody = executeTx(siemConfigDBCollection);
                updateResultTransactionBody.execute();
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }

    private static TransactionBody<UpdateResult> executeTx(MongoCollection<Document> collection){
        return () -> {
            Bson filterQuery = Filters.eq("_id", new ObjectId("65f8753f82f0881df102fe26"));
            Bson updateQuery = Updates.inc("balance", 1000);

            return collection.updateOne(filterQuery, updateQuery);
        };
    }
}
