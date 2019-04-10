package tetris;

import java.awt.Color;
import java.util.Random;


public class Generator {
    static Random r;
    
    static public Color getColor() {
        r = new Random();
        int res = r.nextInt(7);
        Color color;
        switch (res) {
            case 0:
                color = Color.BLUE;
                break;
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.YELLOW;
                break;
            case 3:
                color = Color.GREEN;
                break;
            case 4:
                color = Color.MAGENTA;
                break;
            case 5:
                color = Color.WHITE;
                break;
            default:
                color = Color.PINK;
        }
        return color;
    }
    
    static public Thingy getThingy() {
        Thingy t;
        r = new Random();
        int res = r.nextInt(7);
        switch (res) {
            case 0:
                t = new O();
                break;
            case 1:
                t = new I();
                break;
            case 2:
                t = new S();
                break;
            case 3:
                t = new Z();
                break;
            case 4:
                 t = new L();
                break;
            case 5:
                t = new J();
                break;
            case 6:
                t = new T();
                break;
            default:
                t = new O();              
        }
        return t;
    }
    
}
