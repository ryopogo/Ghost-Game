import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseMenu {
    private static final Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
    static private JButton exit;
    public PauseMenu(JPanel p){
        exit = makeButton(p,"Exit", 4);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    static public void state(){
        exit.setVisible(!exit.isVisible());
    }
    static private JButton makeButton(JPanel p, String name, int position){
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
        int yPosition = (3 * screenHeight / position) - (buttonHeight / 2);

        button.setLocation(xPosition, yPosition);
        p.add(button);
        button.setVisible(false);
        return button;
    }
}
