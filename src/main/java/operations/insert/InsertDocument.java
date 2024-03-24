package operations.insert;

import com.mongodb.client.MongoCollection;
import operations.MongoDbUtils;
import org.bson.Document;

import java.util.Arrays;
import java.util.Map;

public class InsertDocument {

    private static final String DATABASE = "CoronetDB";
    private static final String COLLECTION = "ActivityLog";

    public static void main(String[] args) {
        insertDocument();
    }

    public static void insertDocument() {
        MongoCollection<Document> collection = MongoDbUtils.getMongoClient(DATABASE, COLLECTION);
        collection.insertOne(new Document(
                Map.of(
                        "title", "Ski Bloopers",
                        "genres", Arrays.asList("Documentary", "Comedy")
                )
        ));
    }
}
