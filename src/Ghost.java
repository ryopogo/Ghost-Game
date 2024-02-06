import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Ghost extends JLabel {
    private final ImageIcon icon;
    private final ImageIcon iconDie;
    private int textShownDuration = Integer.MAX_VALUE;
    private int dx; // Change in x-coordinate
    private int dy; // Change in y-coordinate
    private int burn = 0;
    static public final int SIZE = 100;
    private String[] mockOptions =
            {"Cant find meeeee",
            "Over hereeee",
            "womp womp",
            "Wouldn't you like to know weather boy",
            "STOP LOOKING AT ME SWAN"};
    private String curentMock = newMock();

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
            return burn > 100;
        }
        else
            return false;
    }

    public void brightness(Graphics g){
        int distance = 200;
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        if(Math.abs(x-getX()-getWidth()/2)<distance && Math.abs(y-getY()-getHeight()/2)<distance) {
            setIcon(icon);
        } else {
            setIcon(iconDie);
            mock(g);
        }
    }

    private void mock(Graphics g){
        if(textShownDuration < 80) {
            textShownDuration++;
            g.setColor(Color.BLACK);
            g.drawString(curentMock, getX() + getWidth(), getY());
        } else if(Math.random() < .01){
            curentMock = newMock();
            textShownDuration = 0;
        }
    }

    private String newMock(){
        return mockOptions[(int)(Math.random()*mockOptions.length)];
    }
}
