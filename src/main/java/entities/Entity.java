package entities;

import gamestates.GameStates;
import main.Game;
import org.mapeditor.core.ObjectGroup;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utilz.IsSolid.canMoveHere;

/**
 * Base class that hold state that all entities need to navigate the map.
 */
public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected boolean inAir = true;
    protected float gravity = 0.03f * Game.SCALE;

    protected ObjectGroup levelBounds;


    public Entity(float x, float y, int width, int height, ObjectGroup levelBounds) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.levelBounds = levelBounds;
    }

    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    protected void updateXPos(float speed) {
        if (canMoveHere(hitbox.x + speed, hitbox.y, hitbox.width, hitbox.height, levelBounds)) {
            hitbox.x += speed;
        }
    }

    public abstract void render(Graphics g);

    protected void drawHitbox(Graphics g) {
        if (GameStates.isDebug){
            // For debugging the hitbox
            g.setColor(Color.PINK);
            g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
        }
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }
}

