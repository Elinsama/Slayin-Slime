package main;

import javax.swing.*;

/**
 * A holder for the JFrame.
 */
public class GameScreen {
    private JFrame jframe;
    public GameScreen(GamePanel gamePanel){

        jframe = new JFrame();


        jframe.setSize(400, 400);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
    }
}
