import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Ghost extends JLabel {
    private int dx; // Change in x-coordinate
    private int dy; // Change in y-coordinate

    Ghost(ImageIcon imageIcon) {
        super();
        setIcon(imageIcon);  // Set the image icon
        // Set random initial movement direction
        Random random = new Random();
        dx = random.nextInt(5)+1; // Random value between -2 and 2
        dy = random.nextInt(5)+1; // Random value between -2 and 2
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
}
