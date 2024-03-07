import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

class Ghost {
    private static final BufferedImage GHOST_IMAGE;
    static {
        try {
            GHOST_IMAGE = ImageIO.read(new File("src/ghost.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final double GHOST_RATIO = (double) GHOST_IMAGE.getHeight()/ GHOST_IMAGE.getWidth();
    private static final BufferedImage GHOST_DYING_IMAGE;
    static {
        try {
            GHOST_DYING_IMAGE = ImageIO.read(new File("src/ghost dying.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static int instances;
    protected static final Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
    private int textShownDuration = Integer.MAX_VALUE;
    protected int y;
    protected int x;
    protected int dx;
    protected int dy;
    protected int dChang = 2;
    protected int burn = 0;
    protected static final int GHOST_SCALED_W = 100;
    protected static final int GHOST_SCALED_H = (int)(GHOST_SCALED_W * GHOST_RATIO);
    protected boolean hiding = true;
    protected boolean dying = false;
    private static final String[] MOCK_OPTIONS =
            {"Cant find meeeee",
            "Over hereeee",
            "womp womp",
            "Wouldn't you like to know weather boy",
            "STOP LOOKING AT ME SWAN"};
    private String currentMock = newMock();
    protected String audioFilePath = "src/ghost burn.wav";

    Ghost(int instance) {
        y = (int) (instance * GHOST_SCALED_W / (screenDimensions.getWidth() - GHOST_SCALED_W))*GHOST_SCALED_H;
        x = (int) (instance * GHOST_SCALED_W % (screenDimensions.getWidth() - GHOST_SCALED_W));
        // Set random initial movement direction
        Random random = new Random();
        dx = random.nextInt(5)+1; // Random value between 1 and 6
        dy = random.nextInt(5)+1;
    }

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
        if(!hiding)
            g.drawImage((dying)?GHOST_DYING_IMAGE:GHOST_IMAGE,x,y,GHOST_SCALED_W,GHOST_SCALED_H,null);
    }
    public boolean checkKill(){
        Point location = MouseInfo.getPointerInfo().getLocation();
        if(!hiding && x < location.x && location.x < x+GHOST_SCALED_W && y < location.y && location.y < y+GHOST_SCALED_H){
            burn++;
            if(burn > 60) {
                if(!dying) {
                    dx = dx*dChang;
                    dy = dy*dChang;
                    playAudio(audioFilePath);
                }
                dying = true;
            }
            return burn > 100;
        }
        else
            return false;
    }

    public void checkHide(Graphics g){
        int distance = 200;
        int pointerX = MouseInfo.getPointerInfo().getLocation().x;
        int pointerY = MouseInfo.getPointerInfo().getLocation().y;
        if(Math.abs(pointerX-x-GHOST_SCALED_W/2)<distance && Math.abs(pointerY-y-GHOST_SCALED_H/2)<distance) {
            hiding = false;
        } else {
            hiding = true;
            mock(g);
        }
    }

    protected void mock(Graphics g){
        if(textShownDuration < 80) {
            textShownDuration++;
            g.setColor(Color.BLACK);
            g.drawString(currentMock, x, y);
        } else if(Math.random() < .01){
            currentMock = newMock();
            textShownDuration = 0;
        }
    }

    private String newMock(){
        return MOCK_OPTIONS[(int)(Math.random()* MOCK_OPTIONS.length)];
    }

    public static void playAudio(String filePath) {
        File audioFile = new File(filePath);

        Thread thread = new Thread(() -> {
            if (!audioFile.exists()) {
                System.out.println("Audio file not found: " + filePath);
                return;
            }
            AudioInputStream audioStream = null;
            try {
                audioStream = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = null;
                clip = AudioSystem.getClip();
                clip.open(audioStream);

                clip.start();

                // Wait for the audio to finish playing
                while (!clip.isRunning()) {
                    Thread.yield();
                }
                while (clip.isRunning()) {
                    Thread.yield();
                }

                clip.close();
                audioStream.close();
            } catch (Exception ignored){}
        });
        thread.start();
    }
}
