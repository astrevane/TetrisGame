package tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class Tetris implements ActionListener{

    static public void o(String s) {
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
    static Mongo db;
    static public boolean replaying;
    static JButton play;
    static JButton highScore;
    static JButton replay;
    static final int BUTTON_W = 150;
    static final int BUTTON_H = 50;

    
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
        db = new Mongo();
        label = new JLabel("SCORE: " + score);
        label.setBounds(10, 10, 100, 30);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        window.add(label);
        play = new JButton();
        replay = new JButton();
        highScore = new JButton();
        play.setText("PLAY");
        replay.setText("REPLAY");
        highScore.setText("HIGH SCORE");
        play.setSize(BUTTON_W, BUTTON_H);
        replay.setSize(BUTTON_W, BUTTON_H);
        highScore.setSize(BUTTON_W, BUTTON_H);
        play.setLocation(W / 2 - BUTTON_W / 2, 100);
        replay.setLocation(W / 2 - BUTTON_W / 2, 300);
        highScore.setLocation(W / 2 - BUTTON_W / 2, 200);
        play.setBackground(Color.PINK);
        replay.setBackground(Color.PINK);
        highScore.setBackground(Color.PINK);
        play.addActionListener(this);
        replay.addActionListener(this);
        highScore.addActionListener(this);
        play.setActionCommand("play");
        replay.setActionCommand("replay");
        highScore.setActionCommand("highscore");
        window.add(play);
        window.add(replay);
        window.add(highScore);
        hideMenu();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action == null) {
            action = "";
        }
        if (action.equals("play")) {
            hideMenu();
            startGame(false);
            return;
        } else if (action.equals("replay")) {
            hideMenu();
            startGame(true);
            return;
        } else if (action.equals("highscore")) {
            return;
        }
        if (replaying) {
            Thingy newCurr = db.getThingy();
            if (newCurr == null) {
                stopGame();
            }
            if (curr.id != newCurr.id) {
                placed.add(curr);
            }
            spawn(newCurr);
        }
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
            if (dropAuto && !replaying) {                
                curr.drop();        
            }
            dropAuto = true;
        } 
        increaseSpeed();
        removeLevels();        
        if (!replaying) {
            db.addThingy(curr);
        }
        if (!curr.dropped) {
            placed.add(curr);
            spawn(null);
        }    
        window.repaint();
    }

    static void showMenu() {
        play.setEnabled(true);
        play.setVisible(true);
        if (db.storesGame()) {
            replay.setEnabled(true);
        }        
        replay.setVisible(true);
        highScore.setEnabled(true);
        highScore.setVisible(true);
        window.setBackground(Color.WHITE);
    }

    static void hideMenu() {
        play.setEnabled(false);
        play.setVisible(false);
        replay.setEnabled(false);
        replay.setVisible(false);
        highScore.setEnabled(false);
        highScore.setVisible(false);
        window.setBackground(Color.CYAN);
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

    void spawn(Thingy thingy) {        
        if (thingy == null) {
            curr = Generator.getThingy();  
        } else {
            curr = thingy;
        }
        for (Rectangle r : curr.recs) {
            for (Thingy t : placed) {
                if (t.collide(r)) {
                    stopGame();
                    return;
                }
            }
        }
    } 
    
    void startGame(boolean repl) {        
        hideMenu();
        Input.clear();
        placed.clear();
        score = 0;
        label.setText("SCORE: " + score);
        curr = shadow = null;
        delay = 1000;
        ticks = 0;
        tempRefreshSum = 0;
        dropAuto = true;
        score = 0;
        if (repl) {
            replay();
        } else {
            db.clear();
            spawn(null);
        }
        timer = new Timer(refreshTime, this);
        timer.start(); 
        o("start");
    }


    boolean ok = true;

    void stopGame() {
        o("stop");
        replaying = false;
        timer.stop();
        db.saveGame();        
        showMenu();
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
    
    void replay() {
        replaying = true;
        curr = db.getThingy();
    }

    public static void main(String[] args) {
        game = new Tetris();
        showMenu();
    }
}
