package operations.insert;

import com.mongodb.client.MongoCollection;
import operations.MongoDbUtils;
import org.bson.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class InsertMultipleDocuments {
    private static final String DATABASE = "CoronetDB";
    private static final String COLLECTION = "ActivityLog";

    public static void main(String[] args) {
        insertMultipleDocuments();
    }

    public static void insertMultipleDocuments() {
        MongoCollection<Document> collection = MongoDbUtils.getMongoClient(DATABASE, COLLECTION);
        collection.insertMany(List.of(
                new Document(
                        Map.of(
                                "title", "Ski Bloopers",
                                "genres", Arrays.asList("Documentary", "Comedy")
                        )
                ),
                new Document(
                        Map.of(
                                "title", "Short Circuit",
                                "genres", Arrays.asList("Engineering")
                        )
                )
        ));
    }
}