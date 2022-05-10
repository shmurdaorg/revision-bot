package project.define;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import project.Bot;

import java.util.HashMap;

public class DefinitionHandler {

    public static MongoCollection<Document> collection;
    public static HashMap<String, String> map = new HashMap<>();

    public DefinitionHandler() {
        collection = Bot.getBot().getMongoDatabase().getCollection("definitions");

        refresh();
    }

    public void refresh() {
        map.clear();

        FindIterable<Document> iterable = collection.find();

        try (MongoCursor<Document> cursor = iterable.iterator()) {
            while (cursor.hasNext()) {
                Document next = cursor.next();

                map.put(next.getString("k"), next.getString("v"));
            }
        }
    }

    public void define(String k, String v) {
        map.put(k, v);

        Document document = new Document();
        document.put("k", k);
        document.put("v", v);

        collection.replaceOne(Filters.eq("k", k), document, new ReplaceOptions().upsert(true));
    }

    public String get(String k) {
        k = k.toLowerCase();

        if (map.containsKey(k)) {
            return map.get(k);
        } else {
            refresh();

            if (map.containsKey(k)) {
                return map.get(k);
            }
        }

        return null;
    }

}
