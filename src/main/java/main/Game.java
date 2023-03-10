package main;

import gamestates.GameStates;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.Graphics;

/**
 * The runnable game that holds all states.
 */
public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThreat;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;


    public final static int TILES_DEFAULT_SIZE = 16;
    public final static float SCALE = 2.0f;
    public final static int TILES_WIDTH = 40;
    public final static int TILES_HEIGHT = 25;
    public final static int TILE_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * TILES_WIDTH;
    public final static int GAME_HEIGHT = TILE_SIZE * TILES_HEIGHT;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        new GameScreen(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    private void startGameLoop() {
        gameThreat = new Thread(this);
        gameThreat.start();
    }

    public void update() {
        switch (GameStates.state) {
            case PLAYING:
                playing.update();
                break;
            case MENU:
                menu.update();
                break;
            default:
                break;
        }
    }

    protected void render(Graphics g) {
        switch (GameStates.state) {
            case PLAYING:
            case GAME_OVER:
                playing.draw(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        double deltaU = 0;
        double deltaF = 0;

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
                updates = 0;
            }
        }

    }

    public void windowFocusLost() {
        if (GameStates.state == GameStates.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

}
