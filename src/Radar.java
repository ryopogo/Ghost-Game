import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Radar {
    private static Robot bot = null;
    private static final int pixelation = 5;
    private static boolean once = false;
    private static BufferedImage firstGreenImage = null;
    private static BufferedImage radar = null;
    private static final Double topCropPercent = .68;
    private static final Double widthCropPercent =.95;
    private static int bottomCropPercent = 0;
    private static final int x = 0;
    private static final int y = 0;
    private static int h = 0;
    private static int w = 0;

    private Radar() {}

    private static BufferedImage getScreen() {
        try {
            bot = new Robot();
        } catch(Exception e){
            System.out.println(e);
        }
        BufferedImage send = null;
        Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try{
            send = bot.createScreenCapture(rec);
        } catch(Exception e) {
            System.out.println(e);
        }
        radar = getRadar();
        h = radar.getHeight();
        w = radar.getWidth();
        bottomCropPercent = (int)(radar.getHeight()*.04);
        assert send != null;
        send = send.getSubimage(x + (radar.getWidth() - (int) (radar.getWidth() * widthCropPercent)) / 2, y + radar.getHeight() - (int) (radar.getHeight() * topCropPercent), (int) (w * widthCropPercent), (int) (h * topCropPercent) - bottomCropPercent);
        return send;
    }

    static private BufferedImage pixalat(){
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
        return pixelatedImage;
    }

    static private BufferedImage greenShift(){
        BufferedImage image = pixalat();
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0,0,width,height,pixels,0,width);

        for (int i = 0; i < pixels.length; i++) {
            // Extract the individual RGB components
            // did some research this works by truncating off the other colors so I can get the value of the color for the shader math >> is a bit shift
            int alpha = (pixels[i] >> 24) & 0xFF;
            int red = (pixels[i] >> 16) & 0xFF;
            int green = (pixels[i] >> 8) & 0xFF;
            int blue = pixels[i] & 0xFF;

            // Increase the green component
            green = Math.min(255, green + 50);

            // Combine the modified RGB components
            pixels[i] = (alpha << 24) | (red << 16) | (green << 8) | blue;
        }

        // Create a new BufferedImage with the modified pixel data
        BufferedImage greenShiftImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        greenShiftImage.setRGB(0, 0, width, height, pixels, 0, width);
        return greenShiftImage;
    }

    private static BufferedImage getRadar(){
        BufferedImage send = null;
        try {
            send = ImageIO.read(new File("src/rader.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        return send;
    }

    public static void draw(Graphics g){
        if(!once) {
            firstGreenImage = greenShift();
            once = true;
        } else{
            g.drawImage(firstGreenImage, x+(w-(int)(w * widthCropPercent))/2,y+h-(int)(h * topCropPercent),(int)(w * widthCropPercent),(int)(h*topCropPercent)- bottomCropPercent, null);
        }
        g.drawImage(radar, x,y,null);
    }

    public static int getX(){
        return x;
    }
    public static int getY(){
        return y;
    }
    public static int getH(){
        return h;
    }
    public static int getW(){
        return w;
    }
}
