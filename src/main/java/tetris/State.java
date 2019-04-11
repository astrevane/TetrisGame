package tetris;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;


class RecPos {
    public int x, y;
    RecPos(int x_, int y_) {
        x = x_;
        y = y_;
    }
}

public class State {
    public ArrayList<RecPos> recpos;
    public char type;
    public long currId;

    State(Thingy curr) {      
        type = curr.getName();   
        currId = curr.id;    
        recpos = new ArrayList<>();
        for (Rectangle r : curr.recs) {
            recpos.add(new RecPos(r.x, r.y));
        }
        
        
    }

    Thingy getThingy() {
        Thingy t = Thingy.getThingy(type);
        t.id = currId;
        t.recs.clear();
        for (RecPos rp : recpos) {
            t.recs.add(new Rectangle(rp.x, rp.y, Tetris.REC_W, Tetris.REC_W));
        }
        return t;
    }

}


