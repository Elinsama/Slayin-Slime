
package gamestates;

import main.Game;

/**
 * Base class that holds state for all game states.
 */
public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
