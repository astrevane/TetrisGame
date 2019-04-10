package tetris;

import java.awt.Rectangle;
import java.util.ArrayList;



public class Z extends TwoStateThingy {
    
    @Override
    public Thingy copy() {
        Z t = new Z();
        t.recs = copy(recs);
        t.lays = lays;
        return t;
    }
    
    @Override
    public void generate(ArrayList<Rectangle> ra, Rectangle r) {
        if (r == null) {
            r = new Rectangle(Tetris.W / 2, 0, Tetris.REC_W, Tetris.REC_W);
        }
        ra.add(r);
        r = new Rectangle(r.x - Tetris.REC_W, r.y - Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x + Tetris.REC_W, r.y, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x + Tetris.REC_W, r.y + Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
    }
    
    @Override
    public void generate1(ArrayList<Rectangle> ra, Rectangle r) {
        ra.add(r);
        r = new Rectangle(r.x, r.y - Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x + Tetris.REC_W, r.y, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x, r.y - Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
    }
    
}
