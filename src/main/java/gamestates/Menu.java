package gamestates;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import main.Game;

import javax.imageio.ImageIO;

import static main.Game.SCALE;

/**
 * Menu renders options that the player can choose before start.
 */
public class Menu extends State implements Statemethods {

    private final BufferedImage img;
    private final BufferedImage start;
    private final BufferedImage exit;

    private static final Rectangle2D.Float startButton = new Rectangle2D.Float(310 * SCALE, 250 * SCALE, 64 * 4 * SCALE, 32 * 4 * SCALE);
    private static final Rectangle2D.Float exitButton = new Rectangle2D.Float(75 * SCALE, 246 * SCALE, 64 * 4 * SCALE, 32 * 4 * SCALE);

    public Menu(Game game) {
        super(game);
        try {
            InputStream is = getClass().getResourceAsStream("/Graphics/UI/Menu/Screens/Start/_Logo/SlayinSlimeLogo_lg.png");
            InputStream startIS = getClass().getResourceAsStream("/Graphics/UI/Menu/Screens/Start/Start_Btn.png");
            InputStream exitIS = getClass().getResourceAsStream("/Graphics/UI/Menu/Screens/Start/Exit_Btn.png");
            img = ImageIO.read(is);
            start = ImageIO.read(startIS);
            exit = ImageIO.read(exitIS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, 50, -100, null);
        g.drawImage(start, (int) startButton.x, (int) startButton.y, (int) startButton.width, (int) startButton.height, null);
        g.drawImage(exit, (int) exitButton.x, (int) exitButton.y, (int) exitButton.width, (int) exitButton.height, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (startButton.contains(e.getPoint())) {
            GameStates.state = GameStates.PLAYING;
        }
        if (exitButton.contains(e.getPoint())) {
            System.exit(0);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            GameStates.state = GameStates.PLAYING;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}