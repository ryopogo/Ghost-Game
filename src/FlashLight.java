import java.awt.*;

public class FlashLight {
    static public final int SIZE = 10;
    private FlashLight(){}
    static public void draw(Graphics g){
        int x = MouseInfo.getPointerInfo().getLocation().x;
        int y = MouseInfo.getPointerInfo().getLocation().y;
        for(int i = 0; i < 255;i+=40) {
            g.setColor(new Color(255, 255, 255, 255-i));
            g.fillOval(x-(SIZE+i)/2,
                    y-(SIZE+i)/2,
                    SIZE+i, SIZE+i);
        }
    }
}
