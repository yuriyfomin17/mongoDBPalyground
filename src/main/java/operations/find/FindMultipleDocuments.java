package operations.find;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import operations.MongoDbUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

public class FindMultipleDocuments {
    private static final String DATABASE = "CoronetDB";
    private static final String COLLECTION = "ActivityLog";

    public static void main(String[] args) {
        findMultipleDocs();
    }

    public static void findMultipleDocs() {
        MongoCollection<Document> mongoCollection = MongoDbUtils.getMongoClient(DATABASE, COLLECTION);
        Bson filterQuery = Filters.eq("type", "ACCOUNT");
        Bson projections = Projections.fields(Projections.include("fakeLog", "workspaceId", "date"), Projections.excludeId());
        Bson sortField = Sorts.descending("date");
        MongoCursor<Document> iterator = mongoCollection.find(filterQuery).projection(projections).sort(sortField).iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toJson());
        }
        iterator.close();
    }
}