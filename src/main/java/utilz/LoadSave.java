package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * A sprite loader
 */
public class LoadSave {

    public static final String PLAYER_ATLAS = "Graphics/Spritesheets/Player/Sheets/allAnims1.png";
    public static final String ENEMY_ATLAS = "Graphics/Spritesheets/Enemies/Octi/Idle_Movement16x16.png";
    public static BufferedImage GetSprteAltlas(String filname){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filname);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
