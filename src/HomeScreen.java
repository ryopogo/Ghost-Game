import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HomeScreen extends JPanel {
    private static final Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
    private static double time = 0;
    private static String score;
    public HomeScreen(JFrame frame) {
        setOpaque(false);
        setLayout(null);
        JButton button = new JButton("Start Game");
        // Set button size
        button.setSize(260, 60);
        Font originalFont = button.getFont();
        Font biggerFont = originalFont.deriveFont(Font.BOLD, 36); // You can adjust the size and style
        button.setFont(biggerFont);

        // Calculate the position for the button
        int screenWidth = screenDimensions.width;
        int buttonWidth = button.getWidth();
        int xPosition = (screenWidth - buttonWidth) / 2;

        // Set the position for the button at 3/4 to the bottom of the screen
        int screenHeight = screenDimensions.height;
        int buttonHeight = button.getHeight();
        int yPosition = (3 * screenHeight / 4) - (buttonHeight / 2);

        button.setLocation(xPosition, yPosition);
        add(button);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Clicked!");
                // Replace MyPanel with your panel class
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new Game());
                frame.revalidate();
                frame.repaint();
            }
        });

        button.setVisible(true);

        Scanner sc;
        try {
            sc = new Scanner(new File("src/highScore.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        score = "High Score: " + sc.nextLine();
        sc.close();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        time += .1;

        // Set a bigger font
        Font originalFont = g.getFont();
        Font biggerFont = originalFont.deriveFont(Font.BOLD, 36); // You can adjust the size and style
        g.setFont(biggerFont);
        // Draw the string in the center of the screen
        FontMetrics fontMetrics = g.getFontMetrics();
        int titleWidth = fontMetrics.stringWidth("BOO BUSTERS");
        int titleX = (int) (screenDimensions.getWidth() / 2 - titleWidth / 2);
        int titleY = (int) (screenDimensions.getHeight() / 3) + (int)(20*Math.sin(time));
        g.setColor(Color.WHITE);
        g.drawString("BOO BUSTERS", titleX, titleY);

        int scoreWidth = fontMetrics.stringWidth(score);
        int scoreX = (int) (screenDimensions.getWidth() - scoreWidth);
        int scoreY = (int) (screenDimensions.getHeight() - fontMetrics.getHeight());
        g.setColor(Color.WHITE);
        g.drawString(score, scoreX, scoreY);

        try {
            Thread.sleep(16);//62.5 fps
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repaint();
    }
}
