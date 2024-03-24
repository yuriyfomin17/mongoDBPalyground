package operations.updateMultiple;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import operations.MongoDbUtils;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public class UpdateMultiple {

    private static final String DATABASE = "CoronetDB";
    private static final String COLLECTION = "ActivityLog";

    public static void main(String[] args) {
        updateMultiple();
    }

    public static void updateMultiple(){
        MongoCollection<Document> collection = MongoDbUtils.getMongoClient(DATABASE, COLLECTION);
        Bson filter = Filters.eq("title", "Cat");
        Bson update  = Updates.combine(
                Updates.set("title", "My Favourite Cat"),
                Updates.addToSet("genre", "Sports"),
                Updates.currentTimestamp("lastUpdated")
        );
        UpdateOptions updateOptions = new UpdateOptions().upsert(true);

        UpdateResult updateResult = collection.updateOne(filter, update, updateOptions);
        System.out.println(updateResult);
    }
}
