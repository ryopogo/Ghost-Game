import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Radar {
    private static final Robot BOT;

    static {
        try {
            BOT = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int PIXELATION = 5;
    private static boolean once = false;
    private static BufferedImage firstGreenImage = null;
    private static final BufferedImage RADAR_IMAGE = getRadarImage();
    private static final Double TOP_CROP_PERCENT = .68;
    private static final Double WIDTH_CROP_PERCENT =.95;
    private static final double BOTTOM_CROP_PERCENT = .04;
    private static final int x = 0;
    private static final int y = 0; // not capped because you might be able to move the rader later
    private static final int H = RADAR_IMAGE.getHeight();
    private static final int W = RADAR_IMAGE.getWidth();;
    private static final int BOTTOM_CROP = (int)(H * BOTTOM_CROP_PERCENT);
    private static final int xOffSet = (RADAR_IMAGE.getWidth() - (int) (RADAR_IMAGE.getWidth() * WIDTH_CROP_PERCENT)) / 2;
    private static final int yOffset = RADAR_IMAGE.getHeight() - (int) (RADAR_IMAGE.getHeight() * TOP_CROP_PERCENT);

    private Radar() {}

    private static BufferedImage getScreen() {
        BufferedImage send = null;
        Rectangle rec = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        send = BOT.createScreenCapture(rec);
        getRadarImage();
        int bottomCrop = (int)(H * BOTTOM_CROP_PERCENT);
        assert send != null;
        send = send.getSubimage(x + xOffSet, y + yOffset, (int) (W * WIDTH_CROP_PERCENT), (int) (H * TOP_CROP_PERCENT) - bottomCrop);
        return send;
    }

    static private BufferedImage pixalat(){
        BufferedImage image = getScreen();
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0,0,width,height,pixels,0,width);

        for(int y = 0; y < height;y+= PIXELATION){
            for(int x = 0; x < width;x+= PIXELATION){
                int pixel = pixels[y * width + x];
                for(int blockY = 0; blockY < PIXELATION; blockY++){
                    for(int blockX = 0; blockX < PIXELATION; blockX++){
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

    private static BufferedImage getRadarImage(){
        BufferedImage send = null;
        try {
            send = ImageIO.read(new File("src/rader.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return send;
    }

    public static void draw(Graphics g){
        if(!once) {
            firstGreenImage = greenShift();
            once = true;
        } else{
            g.drawImage(firstGreenImage, x+(W -(int)(W * WIDTH_CROP_PERCENT))/2,y+ H -(int)(H * TOP_CROP_PERCENT),(int)(W * WIDTH_CROP_PERCENT),(int)(H * TOP_CROP_PERCENT)- BOTTOM_CROP, null);
        }
        g.drawImage(RADAR_IMAGE, x,y,null);
    }
}
