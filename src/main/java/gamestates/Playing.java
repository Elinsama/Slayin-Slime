package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import entities.Enemy;
import entities.Player;
import levels.LevelManager;
import main.Game;

import javax.imageio.ImageIO;

import static gamestates.GameStates.GAME_OVER;
import static gamestates.GameStates.MENU;
import static main.Game.*;

/**
 * Manages the state of the game when we are playing.
 */
public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private BufferedImage gameOver;
    private BufferedImage retry;
    private static final Rectangle2D.Float retryBounds = new Rectangle2D.Float(GAME_WIDTH / 2 - 128 / 2 * SCALE, GAME_HEIGHT / 2 + 75 * SCALE, 128 * SCALE, 64 * SCALE);

    private enum Level {
        LEVEL1("/Graphics/Tileset/SeasonalTilesets/Tropics/maptropic1.tmx"),
        LEVEL2("/Graphics/Tileset/SeasonalTilesets/Autumn Forest/forestmap.tmx");

        private final String resourcePath;

        Level(String resourcePath) {
            this.resourcePath = resourcePath;
        }
    }

    private Level currentLevel = Level.LEVEL1;

    public Playing(Game game) {
        super(game);
        initClasses(Level.LEVEL1);
    }

    private void initClasses(Level level) {
        currentLevel = level;
        try {
            gameOver = ImageIO.read(getClass().getResourceAsStream("/Graphics/UI/Menu/Screens/Gameover/GameOver.png"));
            retry = ImageIO.read(getClass().getResourceAsStream("/Graphics/UI/Menu/Screens/Gameover/Retry_Btn.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        levelManager = new LevelManager(level.resourcePath);
        final Point2D.Float spawnPoint = levelManager.getSpawnPoint();
        player = new Player(spawnPoint.x, spawnPoint.y, (int) (32 * SCALE), (int) (32 * SCALE), levelManager.getLevelBounds());
    }

    @Override
    public void update() {
        levelManager.update();
        player.update();
        for (Enemy enemy : levelManager.getEnemies()) {
            if (player.getHitbox().intersects(enemy.getHitbox())) {
                GameStates.state = GAME_OVER;
            }
        }
        if (player.getHitbox().contains(levelManager.getEndPoint())) {
            if (currentLevel == Level.LEVEL2) {
                initClasses(Level.LEVEL1);
                GameStates.state = MENU;
            } else {
                currentLevel = Level.values()[currentLevel.ordinal() + 1];
                initClasses(currentLevel);
            }

        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);
        if (GameStates.state == GAME_OVER) {
            g.drawImage(gameOver, (int) (GAME_WIDTH / 2 - 176 / 2 * SCALE), (int) (GAME_HEIGHT / 2 - 96 / 2 * SCALE), (int) (176 * SCALE), (int) (96 * SCALE), null);
            g.drawImage(retry, (int) retryBounds.x, (int) retryBounds.y, (int) retryBounds.width, (int) retryBounds.height, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (GameStates.state == GAME_OVER && retryBounds.contains(e.getPoint())) {
            initClasses(Level.LEVEL1);
            GameStates.state = MENU;
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

}