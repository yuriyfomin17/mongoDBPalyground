import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class MongoDbFindDocument {

    private static final String DATABASE = "CoronetDB";
    private static final String COLLECTION = "ActivityLog";


    public static void main(String[] args) {
        findOneDocument();
    }

    public static void findOneDocument() {
        Bson projectionsField = Projections.fields(Projections.include("workspaceId", "description", "date"), Projections.excludeId());
        MongoCollection<Document> mongoCollection = MongoDbUtils.getMongoClient(DATABASE, COLLECTION);
        Document first = mongoCollection.find(Filters.eq("_id", new ObjectId("65f437640b82da2c9942df55")))
                .projection(projectionsField)
                .sort(Sorts.descending("date"))
                .first();
        if (first != null){
            System.out.println(first.toJson());
        }
    }
}