package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_ATLAS = "Graphics/Spritesheets/Player/Sheets/allAnims1.png";
    public static final String LEVEL_ATLAS = "Graphics/Tileset/SeasonalTilesets/WinterWorld/Terrain16x16.png";
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
