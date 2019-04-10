package tetris;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;



public class Mongo {
    MongoClient mongoClient;
    MongoDatabase db;
    MongoCollection<Document> games;
    ArrayList<State> states;
    int index;

    Mongo() {        
        // mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        // db = mongoClient.getDatabase("tetris");
        // games = db.getCollection("games");
        states = new ArrayList<>();
        index = 0;

    }

    Thingy getThingy() {
        if (index >= states.size()) {
            index = 0;
            return null;
        }
        return states.get(index++).getThingy();
    }

    public void addThingy(Thingy curr) {        
        states.add(new State(curr));
    }

    public void saveGame() {

    }

    public void clear() {
        states.clear();
    }

    public boolean storesGame() {
        return states.size() > 0 ? true : false;
    }

}
