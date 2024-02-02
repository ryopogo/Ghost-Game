import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Random;

class Ghost extends JLabel {
    private ImageIcon icon;
    private ImageIcon iconDie;
    private int dx; // Change in x-coordinate
    private int dy; // Change in y-coordinate

    private int burn = 0;

    static public final int SIZE = 100;

    Ghost() {
        Image image = new ImageIcon(this.getClass().getResource("ghost.png")).getImage();
        Image image2 = new ImageIcon(this.getClass().getResource("ghost die.png")).getImage();
        icon = new ImageIcon(image.getScaledInstance(SIZE,SIZE,Image.SCALE_SMOOTH));
        iconDie = new ImageIcon(image2.getScaledInstance(SIZE,SIZE,Image.SCALE_SMOOTH));
        setIcon(icon);  // Set the image icon
        // Set random initial movement direction
        Random random = new Random();
        dx = random.nextInt(5)+1; // Random value between 1 and 6
        dy = random.nextInt(5)+1;
    }

    void move() {
        // Update label position
        int labelX = getX() + dx;
        int labelY = getY() + dy;

        // Check screen boundaries
        if (labelX < 0 || labelX + getWidth() > getParent().getWidth()) {
            dx = -dx; // Reverse x-direction
        }
        if (labelY < 0 || labelY + getHeight() > getParent().getHeight()) {
            dy = -dy; // Reverse y-direction
        }

        setLocation(labelX, labelY);
    }
    public boolean checkKill(){
        if(getMousePosition() != null){
            burn++;
            if(burn > 100)
                return true;
            else
                return false;
        }
        else
            return false;
    }

    public void brightness(){
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        if(Math.abs(x-getX()-getWidth()/2)<200 && Math.abs(y-getY()-getHeight()/2)<200)
            setIcon(iconDie);
        else
            setIcon(icon);
    }
}
