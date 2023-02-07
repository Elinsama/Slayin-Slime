package levels;

import entities.Enemy;
import gamestates.GameStates;
import org.mapeditor.core.*;
import org.mapeditor.io.TMXMapReader;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static main.Game.*;

/**
 * The LevelManager loads the Tiled map and draws it on screen.
 * we also use object layers in the map for collisions and enemy placement.
 */
public class LevelManager {

    private Map map;
    private List<Enemy> enemies = new ArrayList<>();
    private Point2D.Float spawnPoint;
    private Point2D.Float endPoint;

    public LevelManager(String mapName) {
        readMap(mapName);
    }

    private void readMap(String mapName) {
        try {
            TMXMapReader reader = new TMXMapReader();
            map = reader.readMap(getClass().getResource(mapName));
            final ObjectGroup assets = (ObjectGroup) map.getLayers().stream().filter((l) -> "assets".equals(l.getName())).findFirst().orElseThrow();
            for (MapObject asset : assets) {
                if ("enemy".equals(asset.getName())) {
                    enemies.add(new Enemy((float) asset.getX() * SCALE, (float) asset.getY() * SCALE, 16, 16, getLevelBounds()));
                }

                if ("start".equals(asset.getName())) {
                    spawnPoint = new Point2D.Float((float) asset.getX() * SCALE, (float) asset.getY() * SCALE);
                }

                if ("end".equals(asset.getName())) {
                    endPoint = new Point2D.Float((float) asset.getX() * SCALE, (float) asset.getY() * SCALE);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g) {
        List<MapLayer> layers = map.getLayers();
        for (MapLayer layer : layers) {
            if (layer instanceof TileLayer) {
                renderLayer(g, (TileLayer) layer);
            }
        }

        for (Enemy enemy : enemies) {
            enemy.render(g);
        }

        if (GameStates.isDebug) {
            debugPaintCollisionBox(g);
        }
    }

    private void debugPaintCollisionBox(Graphics g) {
        List<MapLayer> layers = map.getLayers();
        for (MapLayer layer : layers) {
            if (layer instanceof ObjectGroup) {
                for (MapObject obj : ((ObjectGroup) layer).getObjects()) {
                    g.drawRect((int) (obj.getX() * SCALE), (int) (obj.getY() * SCALE), (int) (obj.getWidth() * SCALE), (int) (obj.getHeight() * SCALE));
                }
            }
        }
    }

    private void renderLayer(Graphics g, TileLayer layer) {
        final Rectangle clip = g.getClipBounds();
        final int tileWidth = TILE_SIZE;
        final int tileHeight = TILE_SIZE;
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

                Point drawLoc = new Point(x * tileWidth, y * tileHeight);

                // Add offset from tile layer property
                drawLoc.x += layer.getOffsetX() != null ? layer.getOffsetX() : 0;
                drawLoc.y += layer.getOffsetY() != null ? layer.getOffsetY() : 0;

                // Add offset from tileset property
                drawLoc.x += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getX() : 0;
                drawLoc.y += tile.getTileSet().getTileoffset() != null ? tile.getTileSet().getTileoffset().getY() : 0;

                g.drawImage(image, drawLoc.x, drawLoc.y, TILE_SIZE, TILE_SIZE, null);
            }
        }

        g.translate(-bounds.x * tileWidth, -bounds.y * tileHeight);
    }

    public void update() {
        for (Enemy enemy : enemies) {
            enemy.update();
        }
    }

    public ObjectGroup getLevelBounds() {
        return (ObjectGroup) map.getLayer(map.getLayerCount() - 1);
    }

    public Point2D.Float getSpawnPoint() {
        return spawnPoint;
    }

    public Point2D.Float getEndPoint() {
        return endPoint;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}
