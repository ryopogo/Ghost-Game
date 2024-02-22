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

        /*File outputfile = new File("src/image.png");
        try {
            ImageIO.write(greenShiftImage, "png", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        return greenShiftImage;
    }

    public static void draw(Graphics g){

        if(!once) {
            firstGreenImage = greenShift();
            g.drawImage(firstGreenImage, 0, 0, null);
            once = true;
        } else{
            g.drawImage(firstGreenImage, 0, 0, null);
        }

    }



}
