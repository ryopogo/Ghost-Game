import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    static final private ArrayList<Ghost> ghosts = new ArrayList<>();
    static final private FlashLight flashlight = new FlashLight();

    public MyPanel() {
        setOpaque(false); // Make the panel transparent
        for(int i = 0; i<2;i++)
            ghosts.add(new Ghost());
        for(Ghost ghost: ghosts){
            add(ghost);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        flashlight.draw(g);
        for(Ghost ghost: ghosts){
            ghost.move();
            ghost.brightness();
            if(ghost.checkKill()) {
                remove(ghost);
                ghosts.remove(ghost);
            }
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        repaint();
    }
}
