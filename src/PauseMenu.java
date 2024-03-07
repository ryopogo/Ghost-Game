import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PauseMenu {
    private static final Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
    private static final ArrayList<JComponent> components = new ArrayList<>();
    private static JLabel Score;
    private PauseMenu(){}
    public static void add(JPanel p){
        JButton button = makeButton(p,"Exit", 3,4);
        button.addActionListener(e -> System.exit(0));
        components.add(button);
        button = makeButton(p, "Main Menu", 2,4);
        button.addActionListener(e -> {
            JFrame frame = Main.getFrame();
            Game.restartGame();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new HomeScreen());
            frame.revalidate();
            frame.repaint();
        });
        components.add(button);
        Score = makeLabel(p,"Current Score: " + Game.getScore(), 1,4);
        components.add(Score);
    }
    static public void state(){
        components.forEach(e -> e.setVisible(!e.isVisible()));
        Score.setText("Current Score: " + Game.getScore());
    }
    static private JButton makeButton(JPanel p, String name, int positionNum, int positionDenom){
        JButton button = new JButton(name);
        // Set button size
        button.setSize(260, 60);
        Font originalFont = button.getFont();
        Font biggerFont = originalFont.deriveFont(Font.BOLD, 36); // You can adjust the size and style
        button.setFont(biggerFont);
        p.add(button);

        int screenWidth = screenDimensions.width;
        int buttonWidth = button.getWidth();
        int xPosition = (screenWidth - buttonWidth) / 2;

        int screenHeight = screenDimensions.height;
        int buttonHeight = button.getHeight();
        int yPosition = (positionNum * screenHeight / positionDenom) - (buttonHeight / 2);

        button.setLocation(xPosition, yPosition);
        p.add(button);
        button.setVisible(false);
        return button;
    }
    static private JLabel makeLabel(JPanel p, String name, int positionNum, int positionDenom){
        JLabel label = new JLabel(name);
        label.setForeground(Color.BLACK);
        Font originalFont = label.getFont();
        Font biggerFont = originalFont.deriveFont(Font.BOLD, 36); // You can adjust the size and style
        label.setFont(biggerFont);
        FontMetrics fontMetrics = label.getFontMetrics(biggerFont);
        label.setSize(fontMetrics.stringWidth(name), fontMetrics.getHeight());
        p.add(label);

        int screenWidth = screenDimensions.width;
        int buttonWidth = label.getWidth();
        int xPosition = (screenWidth - buttonWidth) / 2;

        int screenHeight = screenDimensions.height;
        int buttonHeight = label.getHeight();
        int yPosition = (positionNum * screenHeight / positionDenom) - (buttonHeight / 2);

        label.setLocation(xPosition, yPosition);
        p.add(label);
        label.setVisible(false);
        return label;
    }
}
