package tetris;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;
import com.mongodb.Block;

import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Aggregates.sort;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;

public class Mongo {
    MongoClient mongoClient;
    MongoDatabase db;
    MongoCollection<Document> games;
    ArrayList<State> states;
    int index;
    List<Integer> highScores;

    Mongo() {
        mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        db = mongoClient.getDatabase("tetris");
        games = db.getCollection("games");

        index = 0;
        states = new ArrayList<>();
    }

    public void loadHighScore() {
        highScores = new ArrayList<>();
        List<Document> docs = new ArrayList<>();
        games.find().sort(new Document("score", -1)).projection(new Document("score", 1)).limit(10).into(docs);
        for (Document d : docs) {
            highScores.add((Integer)d.get("score"));            
        }
    }

    public void loadGame(int highScoreIndex) {
        states.clear();
        index = 0;        
        Document doc = games.find().sort(new Document("score", -1)).skip(highScoreIndex).limit(1).projection(new Document("states", 1)).first();  
        List<Document> statesDB = (List<Document>) doc.get("states");        
        for (Document d : statesDB) {
            String type = (String) d.get("type");
            List<Document> xyPairs = (List<Document>) d.get("recs");
            Thingy t = Thingy.getThingy(type.charAt(0));
            t.id = (int) d.get("currId");
            ArrayList<Rectangle> recs = new ArrayList<>();
            for (Document xy : xyPairs) {
                recs.add(new Rectangle((int) xy.get("x"), (int) xy.get("y"), Tetris.REC_W, Tetris.REC_W));
            }
            t.recs = recs;
            states.add(new State(t));
        }
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
        List<Document> xyPairs = new ArrayList<>();
        List<Document> thingys = new ArrayList<>();
        for (State s : states) {
            for (RecPos rp : s.recpos) {
                xyPairs.add(new Document("x", rp.x).append("y", rp.y));
            }
            thingys.add(new Document("type", s.type).append("currId", s.currId).append("recs", xyPairs));
            xyPairs= new ArrayList<>();          
        }
        Document doc = new Document("score", Tetris.score).append("states", thingys);
        games.insertOne(doc);
    }

    public void clear() {
        states.clear();
    }

    public boolean storesGame() {
        return states.size() > 0 ? true : false;
    }

    public void printGame() {
        Tetris.o("----------------------");
        for (State s : states) {
            String str = new String();
            for (RecPos rp : s.recpos) {
                str += rp.x + " " + rp.y + " ";
            }
            Tetris.o(s.type + ", " + str);
        }
    }


}
