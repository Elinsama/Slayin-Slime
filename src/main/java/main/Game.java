package main;

import entities.Player;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable{

    private GameScreen gamescreen;
    private GamePanel gamePanel;
    private Thread gameThreat;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private Player player;
    private LevelManager levelManager;


    public final static int TILES_DEFAULT_SIZE = 30;
    public final static float SCALE =  4.0f;
    public final static int TILES_WIDTH = 16;
    public final static int TILES_HEIGHT = 16;
    public final static int TILE_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILE_SIZE * TILES_WIDTH;
    public final static int GAME_HEIGHT = TILE_SIZE * TILES_HEIGHT;
    public Game(){
        initclasses();
        gamePanel = new GamePanel(this);
        gamescreen = new GameScreen(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initclasses(){
        player = new Player(200, 200, (int) (32 * SCALE),(int)(32 *SCALE));
        levelManager = new LevelManager(this);
    }
    private void startGameLoop(){
        gameThreat = new Thread(this);
        gameThreat.start();
    }

    public void update(){
        player.update();
        levelManager.update();
    }
    protected void render(Graphics g){
        levelManager.draw(g);
        player.render(g);
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

        while (true){
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

    public Player getPlayer(){
        return player;
    }

}
