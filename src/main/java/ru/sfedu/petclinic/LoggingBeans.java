package ru.sfedu.petclinic;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import jakarta.xml.bind.JAXB;
import org.bson.Document;
import ru.sfedu.petclinic.model.HistoryContent;
import ru.sfedu.petclinic.util.ConfigUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.time.ZonedDateTime;

import static ru.sfedu.petclinic.Constans.*;

public class LoggingBeans {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private HistoryContent content;
    private ConfigUtils config = new ConfigUtils();
    StringWriter sw = new StringWriter();

    public LoggingBeans() throws IOException {
        try {
            mongoClient = MongoClients.create(config.getConfigurationEntry(MONGO_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        database = mongoClient.getDatabase(config.getConfigurationEntry(MONGO_DB_NAME));
    }

    private HistoryContent createHistoryContent(Object obj, String method, String id) {
        HistoryContent newEntry = new HistoryContent();
        newEntry.setClassName(obj.getClass().getName());
        newEntry.setMethodName(method);
        newEntry.setObjectId(id);
        newEntry.setOperationDate(ZonedDateTime.now().toString());
        JAXB.marshal(obj, sw);
        newEntry.setObject(sw.toString());
        return newEntry;
    }

    private void writeChanges() throws IOException {
        MongoCollection<Document> collection = database.getCollection(config.getConfigurationEntry(MONGO_COLLECTION));
        Document document = new Document("ID", content.getObjectId())
                .append("class Name",content.getClassName())
                .append("date", content.getOperationDate())
                .append("method", content.getMethodName())
                .append("JSON string", content.getObject());

        collection.insertOne(document);
    }

    public void logObjectChange(Object obj, String method, String id) throws IOException {
        content = createHistoryContent(obj, method, id);
        writeChanges();
    }

}