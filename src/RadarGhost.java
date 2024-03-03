import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RadarGhost extends Ghost{
    private static final BufferedImage GHOST_DYING_IMAGE;
    static {
        try {
            GHOST_DYING_IMAGE = ImageIO.read(new File("src/lang ghost dying.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public RadarGhost(){
        super();

    }

}
