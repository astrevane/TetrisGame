package tetris;

import java.awt.Rectangle;
import java.util.ArrayList;


public abstract class TwoStateThingy extends Thingy {
    
    boolean lays;
    
    TwoStateThingy() {
        lays = true;
        generate(recs, null);
    }

    @Override
    public Thingy copy() {
        TwoStateThingy t;
        switch (getName()) {
            case 'I':
                t = new I();
                break;
            case 'S':
                t = new S();
                break;
            case 'Z':
                t = new Z();
                break;   
            default:
                t = new I();  
        }
        t.recs = copy(recs);
        t.lays = lays;
        return t;
    }
    
    public abstract void generate(ArrayList<Rectangle> ra, Rectangle r);
    public abstract void generate1(ArrayList<Rectangle> ra, Rectangle r);
    
    @Override
    public void rotate() {
        ArrayList<Rectangle> nr = new ArrayList<>();
        if (lays) {
            generate1(nr, recs.get(0));            
        } else {
            generate(nr, recs.get(0));      
        }        
        moveX(nr);
        if(checkCollision(nr)) {
            return;
        }
        lays = lays ? false : true;
        recs = nr;
    }
}
