import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    static final private ArrayList<Ghost> GHOSTS = new ArrayList<>();
    static final private ArrayList<RadarGhost> LANG_GHOSTS = new ArrayList<>();
    private static boolean pause = false;

    public MyPanel() {
        setOpaque(false); // Make the panel transparent
        for (int i = 0; i < 10; i++)
            GHOSTS.add(new Ghost());
        for (int i = 0; i < 10; i++)
            LANG_GHOSTS.add(new RadarGhost());
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) pause = !pause;
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
            for (int i = 0; i < LANG_GHOSTS.size(); i++) {
                Ghost ghost = LANG_GHOSTS.get(i);
                ghost.move(g);
                ghost.checkHide(g);
                if (ghost.checkKill()) {
                    LANG_GHOSTS.remove(ghost);
                }
            }

            for (int i = 0; i < GHOSTS.size(); i++) {
                Ghost ghost = GHOSTS.get(i);
                ghost.move(g);
                ghost.checkHide(g);
                if (ghost.checkKill()) {
                    GHOSTS.remove(ghost);
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
}
