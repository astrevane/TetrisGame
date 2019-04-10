package tetris;

import java.awt.Rectangle;


public class O extends Thingy {

    @Override
    public void rotate() {
       
    }

    @Override
    public char getName() {
        return 'O';
    }
    
    @Override
    public Thingy copy() {
        O t = new O();        
        t.recs = copy(recs);
        return t;
    }
    
    O() {
        Rectangle r = new Rectangle(Tetris.W / 2, 0, Tetris.REC_W, Tetris.REC_W);
        recs.add(r);
        r = new Rectangle(r.x - Tetris.REC_W, 0, Tetris.REC_W, Tetris.REC_W);
        recs.add(r);
        r = new Rectangle(r.x, -Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        recs.add(r);
        r = new Rectangle(r.x + Tetris.REC_W, -Tetris.REC_W, Tetris.REC_W, Tetris.REC_W);
        recs.add(r);
    }
    
}
