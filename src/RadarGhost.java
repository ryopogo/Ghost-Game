import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RadarGhost extends Ghost{
    private static final BufferedImage GHOST_IMAGE;
    static {
        try {
            GHOST_IMAGE = ImageIO.read(new File("src/lang ghost.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final BufferedImage GHOST_DYING_IMAGE;
    static {
        try {
            GHOST_DYING_IMAGE = ImageIO.read(new File("src/lang ghost dying.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final BufferedImage GHOST_IMAGE_LEFT;
    static {
        try {
            GHOST_IMAGE_LEFT = ImageIO.read(new File("src/lang left ghost.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final BufferedImage GHOST_DYING_IMAGE_LEFT;
    static {
        try {
            GHOST_DYING_IMAGE_LEFT = ImageIO.read(new File("src/lang left ghost dying.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public RadarGhost(int instance){
        super(instance);
        super.audioFilePath = "";
        super.dChang = 1;
    }
    @Override
    protected void move(Graphics g) {
        // Check screen boundaries
        if (x < 0 || x + GHOST_SCALED_W > screenDimensions.getWidth()) {
            dx = -dx; // Reverse x-direction
        }
        if (y < 0 || y + GHOST_SCALED_H > screenDimensions.getHeight()) {
            dy = -dy; // Reverse y-direction
        }
        x += dx;
        y += dy;
        int xOff = Radar.getX() + Radar.getXOffSet();
        int yOff = Radar.getY() + Radar.getYOffSet();
        if(xOff < x && x + GHOST_SCALED_W < xOff + Radar.getWidth() && yOff < y && y + GHOST_SCALED_H < yOff + Radar.getHight()){
            if(dx > 0)
                g.drawImage((dying)?GHOST_DYING_IMAGE:GHOST_IMAGE,x,y,GHOST_SCALED_W,GHOST_SCALED_H,null);
            else
                g.drawImage((dying)?GHOST_DYING_IMAGE_LEFT:GHOST_IMAGE_LEFT,x,y,GHOST_SCALED_W,GHOST_SCALED_H,null);
        }
    }
    @Override
    protected void mock(Graphics g){}
}
