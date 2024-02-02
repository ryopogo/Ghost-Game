import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel {
    private Ghost ghost;

    public MyPanel() {
        setLayout(null); // Use absolute positioning
        setOpaque(false); // Make the panel transparent

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\ryopo\\Downloads\\bun.png");

        ghost = new Ghost(imageIcon);
        ghost.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        add(ghost);
        add(new JLabel("boo"));

        // Use Timer to trigger periodic updates
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ghost.move();
                repaint();
            }
        });
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
