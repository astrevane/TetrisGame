package tetris;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;





public class Window extends JPanel {
    
    Window(Input input) {
        addKeyListener(input);
        setFocusable(true);
        setLayout(null);
        setBackground(Color.CYAN);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tetris.draw(g);
    }
    
    
}

