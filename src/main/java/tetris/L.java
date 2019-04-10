package tetris;

import java.awt.Rectangle;
import java.util.ArrayList;



public class L extends FourStateThingy {

    @Override
    public char getName() {
        return 'L';
    }
    
    public void generate(ArrayList<Rectangle> ra, Rectangle r) {
        if (r == null) {
            r = new Rectangle(Tetris.W / 2, 0, Tetris.REC_W, Tetris.REC_W);
        }
        ra.add(r);
        r = new Rectangle(r.x + Tetris.REC_W, r.y, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x - 2 * Tetris.REC_W, r.y, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x, r.y + Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
    }
    
    public void generate1(ArrayList<Rectangle> ra, Rectangle r) {
        ra.add(r);
        r = new Rectangle(r.x, r.y - Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x, r.y + 2 * Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x + Tetris.REC_W, r.y, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
    }
    
    public void generate2(ArrayList<Rectangle> ra, Rectangle r) {
        ra.add(r);
        r = new Rectangle(r.x - Tetris.REC_W, r.y, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x + 2 * Tetris.REC_W, r.y, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x, r.y - Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
    }
    
    public void generate3(ArrayList<Rectangle> ra, Rectangle r) {
        ra.add(r);
        r = new Rectangle(r.x, r.y + Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x, r.y - 2 * Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
        r = new Rectangle(r.x - Tetris.REC_W, r.y, Tetris.REC_W, Tetris.REC_W);
        ra.add(r);
    }
    
}
