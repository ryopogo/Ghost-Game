import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends JPanel {
    static private ArrayList<Ghost> ghosts;
    static private ArrayList<RadarGhost> langGhosts;
    private static boolean pause;
    private static Scanner sc;

    private static int oldScore;
    private static int score;
    private static Game THIS = new Game();

    private Game() {
        try {
            sc = new Scanner(new File("src/highScore.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PauseMenu.add(this);
        langGhosts = new ArrayList<>();
        ghosts = new ArrayList<>();
        score = 0;
        pause = false;
        oldScore = sc.nextInt();

        setOpaque(false); // Make the panel transparent
        setLayout(null);
        for (int i = 0; i < 10; i++)
            ghosts.add(new Ghost(i));
        for (int i = 0; i < 10; i++)
            langGhosts.add(new RadarGhost(i));
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    pause = !pause;
                    PauseMenu.state();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        addKeyListener(kl);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        requestFocus();

        Radar.drawScreen(g);
        FlashLight.draw(g);
        Radar.drawFrame(g);
        if (!pause) {
            for (int i = 0; i < langGhosts.size(); i++) {
                Ghost ghost = langGhosts.get(i);
                ghost.move(g);
                ghost.checkHide(g);
                if (ghost.checkKill()) {
                    langGhosts.remove(ghost);
                    addScore(4);
                }
            }

            for (int i = 0; i < ghosts.size(); i++) {
                Ghost ghost = ghosts.get(i);
                ghost.move(g);
                ghost.checkHide(g);
                if (ghost.checkKill()) {
                    ghosts.remove(ghost);
                    addScore(2);
                }
            }
        }


        try {
            Thread.sleep(16);//62.5 fps
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repaint();
    }
    private static void addScore(int add){
        score += add;
        if(oldScore < score)
            try {
                FileWriter fileWriter = new FileWriter("src/highScore.txt", false);
                fileWriter.write(Integer.toString(score));
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public static int getScore(){
        return score;
    }
    public static Game getInstance(){
        return THIS;
    }
    public static void restartGame(){
        THIS = new Game();
    }
}
