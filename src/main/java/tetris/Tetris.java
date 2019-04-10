package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class Tetris implements ActionListener{

    static void o(String s) {
        System.out.println(s);
    }

    static public final int W = 400, H = 500, REC_W = 20;
    static public Tetris game;
    static JFrame frame;
    static Window window;
    static Timer timer;
    static int delay;    
    static final int refreshTime = 100;
    static int ticks;
    static int tempRefreshSum;
    static boolean dropAuto;
    static Thingy curr;    
    static Thingy shadow;
    static ArrayList<Thingy> placed;
    static Input input;
    static long score;
    static JLabel label;
    
    Tetris() {    
        input = new Input();
        frame = new JFrame();
        window = new Window(input);
        frame.add(window);
        frame.setSize(W + 7, H + 30);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); 
        placed = new ArrayList<>();
        
        label = new JLabel("SCORE: " + score);
        label.setBounds(10, 10, 100, 30);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        window.add(label);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ++ticks;
        if (input.right) {
            curr.right();
        } else if (input.left) {
            curr.left();
        }
        if (input.down) {
            curr.drop();
            dropAuto = false;
        }
        if (input.up) {
            curr.rotate();
        }
        if (input.space) {
            curr.release();
        }
        input.clear();
        tempRefreshSum += refreshTime;
        if (tempRefreshSum >= delay) {
            tempRefreshSum = 0;
            if (dropAuto) {                
                curr.drop();        
            }
            dropAuto = true;
        }     
        if (!curr.dropped) {
            placed.add(curr);
            spawn();
        }        
        increaseSpeed();
        removeLevels();
        window.repaint();
    }
    
    void removeLevel(int y) {
        for (int i = 0; i < placed.size(); ++i) {
            for (int j = 0; j < placed.get(i).recs.size(); ++j) {
                if (placed.get(i).recs.get(j).y == y) {
                    placed.get(i).recs.remove(j);
                    --j;
                }
            }
        }
        for (int i = 0; i < placed.size(); ++i) {
            for (int j = 0; j < placed.get(i).recs.size(); ++j) {
                if (placed.get(i).recs.get(j).y < y) {
                    placed.get(i).recs.get(j).y += REC_W;
                }
            }
        }
    } 
    
    void removeLevels() {        
        for (int y = 0; y < H; y += 20) {
            boolean lineFilled = true;
            for (int x = 0; x < W; x += 20) {
                boolean boxFilled = false;
                for (Thingy t : placed) {
                    if (t.collide(new Rectangle(x, y, REC_W, REC_W))) {
                        boxFilled = true;
                        break;
                    }
                }
                if (!boxFilled) {
                    lineFilled = false;
                    break;                    
                }
            }
            if (lineFilled) {
                removeLevel(y);
                ++score;
                label.setText("SCORE: " + score);
            }
        }
    }
    
    void spawn() {
        curr = Generator.getThingy();        
        for (Rectangle r : curr.recs) {
            for (Thingy t : placed) {
                if (t.collide(r)) {
                    stopGame();
                    return;
                }
            }
        }
    } 
    
    void startGame() {
        Input.clear();
        delay = 1000;
        ticks = 0;
        tempRefreshSum = 0;
        dropAuto = true;
        score = 0;
        spawn();
        timer = new Timer(refreshTime, this);
        timer.start(); 
        o("start");
    }
    
    void stopGame() {
        o("stop");
        timer.stop();
    }
    
    void increaseSpeed() {
        if (ticks > 99) {
            ticks = 0;
            if (delay <= refreshTime) {
                return;
            }
            delay -= 50;
        }
    }
    
    static void draw(Graphics g) {
        placed.forEach((t) -> {
            t.draw(g);
        });
        shadow = curr.copy();
        shadow.release();
        shadow.color = Color.LIGHT_GRAY;
        shadow.draw(g); 
        curr.draw(g);     
    }
    
    public static void main(String[] args) {
        game = new Tetris();
        game.startGame();
    }
}
