package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

    static public boolean right, left, down, up, space;

    Input() {
        clear();
    }

    static void clear() {
        right = left = down = up = space = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Tetris.replaying) {
            return;
        }
        
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (key == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (key == KeyEvent.VK_UP) {
            up = true;
        }
        if (key == KeyEvent.VK_DOWN) {
            down = true;
        }        
        if (key == KeyEvent.VK_SPACE) {
            space = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}
