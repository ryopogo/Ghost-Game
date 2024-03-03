import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MyPanel extends JPanel {
    static final private ArrayList<Ghost> ghosts = new ArrayList<>();

    public MyPanel() {
        setOpaque(false); // Make the panel transparent
        for(int i = 0; i<10;i++)
            ghosts.add(new Ghost());
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Radar.draw(g);
        FlashLight.draw(g);

        for(int i = 0; i < ghosts.size(); i++){
            Ghost ghost = ghosts.get(i);
            ghost.move(g);
            ghost.brightness(g);
            if(ghost.checkKill()) {
                ghosts.remove(ghost);
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
