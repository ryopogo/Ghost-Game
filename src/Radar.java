import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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

    public BufferedImage pixalat(){
        BufferedImage image = getScreen();
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0,0,width,height,pixels,0,width);

        for(int y = 0; y < height;y+=pixelation){
            for(int x = 0; x < width;x+=pixelation){
                int pixel = pixels[y * width + x];
                for(int blockY = 0; blockY < pixelation;blockY++){
                    for(int blockX = 0; blockX < pixelation;blockX++){
                        int currentY = y + blockY;
                        int currentX = x + blockX;
                        if(currentY < height && currentX < width){
                            pixels[currentY * width + currentX] = pixel;
                        }
                    }
                }
            }
        }

        BufferedImage pixelatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        pixelatedImage.setRGB(0,0,width,height,pixels,0,width);
        File outputfile = new File("image.jpg");
        try {
            ImageIO.write(pixelatedImage, "jpg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return pixelatedImage;
    }
}
