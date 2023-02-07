package levels;

import main.Game;
import org.mapeditor.core.*;
import org.mapeditor.io.TMXMapReader;
import static main.Game.SCALE;


import java.awt.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

public class LevelManager {

    private Map map;

    public LevelManager(String mapName){
        importWinterSrites(mapName);
    }

    private void importWinterSrites(String mapName) {
        try {
            TMXMapReader reader = new TMXMapReader();
            map = reader.readMap(getClass().getResource(mapName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g){
        List<MapLayer> layers = map.getLayers();
        for (MapLayer layer: layers) {
            if (layer instanceof TileLayer){
            renderLayer(g, (TileLayer)layer);
            }
            else {
                for (MapObject obj: ((ObjectGroup)layer).getObjects()){
                    g.drawRect((int) (obj.getX()*SCALE),(int) (obj.getY()*SCALE+45), (int) (obj.getWidth()*SCALE), (int)(obj.getHeight()*SCALE));
                }
            }
        }

    }

    private void renderLayer(Graphics g, TileLayer layer) {
        final Rectangle clip = g.getClipBounds();
        final int tileWidth = (int) (map.getTileWidth()*SCALE);
        final int tileHeight = (int) (map.getTileHeight()*SCALE);
        final Rectangle bounds = layer.getBounds();

        g.translate(bounds.x * tileWidth, bounds.y * tileHeight);
        clip.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);

        clip.height += map.getTileHeightMax();

        final int startX = Math.max(0, clip.x / tileWidth);
        final int startY = Math.max(0, clip.y / tileHeight);
        final int endX = Math.min(layer.getWidth(),
                (int) Math.ceil(clip.getMaxX() / tileWidth));
        final int endY = Math.min(layer.getHeight(),
                (int) Math.ceil(clip.getMaxY() / tileHeight));

        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                final Tile tile = layer.getTileAt(x, y);
                if (tile == null) {
                    continue;
                }
                final Image image = tile.getImage();
                if (image == null) {
                    continue;
                }

                Point drawLoc = new Point(x * tileWidth, (int) ((y + 1) * tileHeight - image.getHeight(null)));

                // Add offset from tile layer property
                drawLoc.x += layer.getOffsetX() != null ? layer.getOffsetX() : 0;
                drawLoc.y += layer.getOffsetY() != null ? layer.getOffsetY() : 0;

                // Add offset from tileset property
                drawLoc.x += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getX() : 0;
                drawLoc.y += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getY() : 0;

                g.drawImage(image, drawLoc.x, drawLoc.y, (int) (16*SCALE), (int) (16*SCALE),null);
            }
        }

        g.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);
    }

    public void update(){

    }
    public ObjectGroup getLevelBounds(){
        return (ObjectGroup) map.getLayer(map.getLayerCount() - 1);
    }
}
