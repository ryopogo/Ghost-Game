import java.awt.*;
import java.awt.image.BufferedImage;

public class Radar {
    private static Robot bot;
    private static final int pixelation = 5;

    public Radar() {
        try {
            bot = new Robot();
        } catch(Exception e){
            System.out.println(e);
        }
    }

    private static BufferedImage getScreen() {
        BufferedImage send = null;
        Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try{
            send = bot.createScreenCapture(rec);
        } catch(Exception e) {
            System.out.println(e);
        }
        return send;
    }

    private BufferedImage pixalat(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixals = new int[width * height];
        image.getRGB(0,0,width,height,pixals,0,width);

        for(int y = 0; y < height;y+=pixelation){
            for(int x = 0; x < width;x+=pixelation){
                int pixal = pixals[]
            }
        }
        return null;
    }
}
