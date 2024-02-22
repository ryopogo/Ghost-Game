import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    static final private ArrayList<Ghost> ghosts = new ArrayList<>();
    static final private FlashLight flashlight = new FlashLight();

    public MyPanel() {
        setOpaque(false); // Make the panel transparent
        for(int i = 0; i<10;i++)
            ghosts.add(new Ghost());
        for(Ghost ghost: ghosts){
            add(ghost);
        }
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        flashlight.draw(g);

        BufferedImage bf = new Radar().greenShift();
        g.drawImage(bf, 0, 0,null);

        for(int i = 0; i < ghosts.size(); i++){
            Ghost ghost = ghosts.get(i);
            ghost.move();
            ghost.brightness(g);
            if(ghost.checkKill()) {
                remove(ghost);
                ghosts.remove(ghost);
            }
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        repaint();
    }
}
