package entities;

import org.mapeditor.core.ObjectGroup;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.SCALE;
import static utilz.Constants.EnemyConstants.ENEMYWALK;
import static utilz.Constants.EnemyConstants.getSpriteAmount;
import static utilz.IsSolid.IsEntityOnFloor;
import static utilz.IsSolid.canMoveHere;

/**
 * An entity called enemy, that renders and roams the level.
 */
public class Enemy extends Entity {

    private BufferedImage[] animations;

    private int aniTick = 0, aniIndex = 0, aniSpeed = 60;
    private float speed = -2f;
    private float airSpeed = 0f;

    public Enemy(float x, float y, int width, int height, ObjectGroup levelBounds) {
        super(x, y, (int) (width * SCALE), (int) (height * SCALE), levelBounds);
        initHitbox(x, y, (int) (width * SCALE), (int) (height * SCALE));
        loadAnimations();
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSprteAltlas(LoadSave.ENEMY_ATLAS);

        animations = new BufferedImage[2];
        for (int i = 0; i <= getSpriteAmount(ENEMYWALK); i++) {
            animations[i] = img.getSubimage(i * 16, 0, 16, 16);
        }
    }

    public void render(Graphics g) {
        g.drawImage(animations[aniIndex], (int) (hitbox.x), (int) (hitbox.y), width, height, null);
        drawHitbox(g);
    }

    public void update() {
        updatePos();
        updateAnimationTick();
    }

    private void updatePos() {
        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, levelBounds)) {
                inAir = true;
            }
        }

        if (!canMoveHere(hitbox.x + speed, hitbox.y, hitbox.width, hitbox.height, levelBounds)) {
            speed = -speed;
        }

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelBounds)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(speed);
            } else {
                if (airSpeed > 0) {
                    inAir = false;
                    airSpeed = 0;
                }
                updateXPos(speed);
            }
        } else {
            updateXPos(speed);
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex > getSpriteAmount(ENEMYWALK)) {
                aniIndex = 0;
            }
        }
    }
}