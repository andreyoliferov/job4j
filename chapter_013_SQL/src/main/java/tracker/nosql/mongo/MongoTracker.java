package tracker.nosql.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.ValueCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.jsr310.Jsr310CodecProvider;
import org.bson.codecs.pojo.PojoCodecProvider;
import tracker.ITracker;
import tracker.Item;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDB noSql
 * doc http://mongodb.github.io/mongo-java-driver/3.9/driver/tutorials/perform-read-operations/
 * @autor aoliferov
 * @since 01.09.2019
 */
public class MongoTracker implements ITracker, AutoCloseable {

    private MongoClient mongoClient;
    private MongoDatabase db;

    public MongoTracker() {
        //можно для подключения выбрать сразу несколько хостов
        //по умолчанию create() подключается к localhost:27017
        mongoClient = MongoClients.create();
        //если бд не существует, она автоматически создается
        db = mongoClient.getDatabase("trackerMongoDb").withCodecRegistry(
                CodecRegistries
                        .fromProviders(PojoCodecProvider.builder()
                                        .register(Item.class) //регистрируем класс для кодеков, сериализация на ходу
                                        .build(),
                                new Jsr310CodecProvider(),
                                new ValueCodecProvider())
        );
    }

    @Override
    public Item add(Item item) {
        //если коллекции не существует, она автоматически создается
        db.getCollection("items", Item.class).insertOne(item);
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        item.setId(id);
        return db.getCollection("items", Item.class).replaceOne(eq("_id", id), item).wasAcknowledged();
    }

    @Override
    public boolean delete(String id) {
        return db.getCollection("items", Item.class).deleteOne(eq("_id", id)).wasAcknowledged();
    }

    @Override
    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        db.getCollection("items", Item.class).find().into(list);
        return list;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> list = new ArrayList<>();
        db.getCollection("items", Item.class).find(eq("name", key)).into(list);
        return list;
    }

    @Override
    public Item findById(String id) {
        //см com.mongodb.client.model.Filters
        //при создании объекта, к имени поля id добавляется подчеркивание
        //подчеркивание - обозначение внутреннего специального поля, при сериализации кодеком id > _id
        //для выборок удобно использовать Criteria API
        return db.getCollection("items", Item.class).find(eq("_id", id)).first();
    }

    @Override
    public void close() {
        mongoClient.close();
    }
}
