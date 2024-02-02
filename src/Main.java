import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

            // Create a transparent JFrame
            JFrame frame = new JFrame("Transparent Frame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(true); // Remove window decorations (title bar, etc.)
            frame.setBackground(new Color(0, 0, 0, 0)); // Set transparent background

            // Set the frame size to match the screen resolution
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(screenSize.width, screenSize.height);
            // Add the label to the content pane
            frame.add(new MyPanel());

            // Make the frame visible
            frame.setVisible(true);
    }
}
