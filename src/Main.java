import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Main {
    private static JFrame frame;
    public static void main(String[] args) {
        // Create a transparent JFrame
        frame = new JFrame("BOO BUSTERS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true); // Remove window decorations (title bar, etc.)
        frame.setBackground(new Color(0, 0, 0, 0)); // Set transparent background

        // Set the frame size to match the screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        frame.add(new HomeScreen());

        String imagePath = "src/ghost.png";
        Image ghost = null;
        try {
            ghost = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        frame.setIconImage(ghost);

        // Make the frame visible
        frame.setVisible(true);
    }
    public static JFrame getFrame(){
        return frame;
    }
}
