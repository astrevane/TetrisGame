package tetris;

import java.awt.Rectangle;
import java.util.ArrayList;


public abstract class FourStateThingy extends Thingy {
    
    int state;
    
    FourStateThingy() {
        state = 0;
        generate(recs, null);
    }
    
    public abstract void generate(ArrayList<Rectangle> ra, Rectangle r);
    public abstract void generate1(ArrayList<Rectangle> ra, Rectangle r);
    public abstract void generate2(ArrayList<Rectangle> ra, Rectangle r);
    public abstract void generate3(ArrayList<Rectangle> ra, Rectangle r);
    
    @Override
    public Thingy copy() {
        FourStateThingy t;
        switch (getName()) {
            case 'J':
                t = new J();
                break;
            case 'T':
                t = new T();
                break;
            case 'L':
                t = new L();
                break;   
            default:
                t = new J();  
        }
        t.recs = copy(recs);
        t.state = state;
        return t;
    }

    @Override
    public void rotate() {
        ArrayList<Rectangle> nr = new ArrayList<>();
        Rectangle r = recs.get(0);
        switch (state) {
            case 0:
                generate1(nr, r);
                break;
            case 1:
                generate2(nr, r);
                break;
            case 2:
                generate3(nr, r);
                break;
            case 3:
                generate(nr, r);
        }
        moveX(nr);
        if (checkCollision(nr)) {
            return;
        }
        recs = nr;
        if (++state > 3) {
            state = 0;
        }
    }

}
