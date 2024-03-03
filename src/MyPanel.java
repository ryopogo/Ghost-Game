import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    static final private ArrayList<Ghost> GHOSTS = new ArrayList<>();
    static final private ArrayList<RadarGhost> LANG_GHOSTS = new ArrayList<>();

    public MyPanel() {
        setOpaque(false); // Make the panel transparent
        for(int i = 0; i<0;i++)
            GHOSTS.add(new Ghost());
        for(int i = 0; i<4;i++)
            LANG_GHOSTS.add(new RadarGhost());
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Radar.draw(g);
        FlashLight.draw(g);

        for(int i = 0; i < GHOSTS.size(); i++){
            Ghost ghost = GHOSTS.get(i);
            ghost.move(g);
            ghost.checkHide(g);
            if(ghost.checkKill()) {
                GHOSTS.remove(ghost);
            }
        }
        for(int i = 0; i < LANG_GHOSTS.size(); i++){
            Ghost ghost = LANG_GHOSTS.get(i);
            ghost.move(g);
            ghost.checkHide(g);
            if(ghost.checkKill()) {
                LANG_GHOSTS.remove(ghost);
            }
        }

        try {
            Thread.sleep(16);//62.5 fps
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        repaint();
    }
}
