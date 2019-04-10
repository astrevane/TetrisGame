package tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;



public abstract class Thingy {
    public ArrayList<Rectangle> recs;
    public Color color;
    public boolean dropped;
    
    Thingy() {
        dropped = true;
        recs = new ArrayList<>();
        color = Generator.getColor();
    }
    
    public abstract Thingy copy();    
    
    public abstract void rotate();
    
    public static ArrayList<Rectangle> copy(ArrayList<Rectangle> src) {
        ArrayList<Rectangle> ret = new ArrayList<>();
        for (Rectangle r : src) {
            ret.add(new Rectangle(r.x, r.y, r.width, r.height));
        }
        return ret;
    }
    
    public void moveX(ArrayList<Rectangle> ra) {
        int o = offset(ra);
        if (o != 0) {
            for (Rectangle rec : ra) {
                rec.x += o;
            }
        }
    }
    
    private int offset(ArrayList<Rectangle> ra) {
        int x1 = 1000000;
        int x2 = 0;
        for (Rectangle r : ra) {
            if (r.x < x1) {
                x1 = r.x;
            } else if (r.x > x2) {
                x2 = r.x;
            }
        }
        if (x1 < 0) {
            return -x1;
        } 
        if (x2 + Tetris.REC_W >= Tetris.W) {
            return Tetris.W - (x2 + Tetris.REC_W);
        } 
        return 0;
    }
    
    public boolean checkCollision(ArrayList<Rectangle> recs) {
        for (Rectangle r : recs) {
            for (Thingy t : Tetris.placed) {
                if (t.collide(r)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean collide(Rectangle rec) {
        for (Rectangle r : recs) {
            if (r.x == rec.x && r.y == rec.y) {
                return true;
            }
        }
        return false;
    }
    
    public void left() {
        for (Rectangle r : recs) {
            if (r.x - Tetris.REC_W < 0) {
                return;
            }
        }
        for (Rectangle r : recs) {
            r.x -= Tetris.REC_W;
        }
        for (Rectangle r : recs) {
            for (Thingy t : Tetris.placed) {
                if (t.collide(r)) {
                    for (Rectangle r2 : recs) {
                        r2.x += Tetris.REC_W;
                    }
                    return;
                }
            }
        }
    }
    
    public void right() {
        for (Rectangle r : recs) {
            if (r.x + Tetris.REC_W >= Tetris.W - 1) {
                return;
            }
        }
        for (Rectangle r : recs) {
            r.x += Tetris.REC_W;
        }
        for (Rectangle r : recs) {
            for (Thingy t : Tetris.placed) {
                if (t.collide(r)) {
                    for (Rectangle r2 : recs) {
                        r2.x -= Tetris.REC_W;
                    }
                    return;
                }
            }
        }
    }
    
    public void release() {
        do {
            drop();
        } while (dropped);
    }
    
    public void drop() {
        for (Rectangle r : recs) {
            if (r.y + Tetris.REC_W >= Tetris.H - 1) {
                dropped = false;
                return;
            }
        }
        for (Rectangle r : recs) {
            r.y += Tetris.REC_W;
        }
        for (Rectangle r : recs) {
            for (Thingy t : Tetris.placed) {
                if (t.collide(r)) {
                    for (Rectangle r2 : recs) {
                        r2.y -= Tetris.REC_W;
                    }
                    dropped = false;
                    return;
                }
            }
        }
        dropped = true;
    }
    
    public void draw(Graphics g) {  
        for (Rectangle r : recs) {
            g.setColor(color);
            g.fillRect(r.x, r.y, r.width, r.height); 
            g.setColor(Color.BLACK);
            g.drawRect(r.x, r.y, r.width, r.height);
        }
    }
    
}




