package entities;

import main.Game;
import org.mapeditor.core.ObjectGroup;
import utilz.LoadSave;

import static utilz.IsSolid.IsEntityOnFloor;
import static utilz.constants.PlayerConstants.*;
import static utilz.IsSolid.canMoveHere;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1f;
    private ObjectGroup levelBounds;
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -1.5f * Game.SCALE;
    private float collisionSpeedFall = 0.5f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = true;

    private float xDrawOffset = 8 * Game.SCALE;
    private float yDrawOffset = 5 * Game.SCALE;

    public Player(float x, float y, int width, int height, ObjectGroup levelBounds) {
        super(x, y,width,height);
        this.levelBounds = levelBounds;
        loadAnimations();
        initHitbox(x, y,16 * Game.SCALE,15 * Game.SCALE);
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        drawHitbox(g);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
            }

        }
    }


    private void setAnimation() {
        int startAni = playerAction;

        if (moving)
            playerAction = WALK;
        else
            playerAction = IDLE;

        if (startAni != playerAction)
            resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos() {
        moving = false;

        if (jump)
            jump();
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, levelBounds))
                inAir = true;

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelBounds)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;

    }

    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelBounds)) {
            hitbox.x += xSpeed;
        }
    }

    private void loadAnimations() {

            BufferedImage img = LoadSave.GetSprteAltlas(LoadSave.PLAYER_ATLAS);

            animations = new BufferedImage[4][12];
            for (int j = 0; j < animations.length-1; j++)
                for (int i = 0; i < animations[j].length-1; i++)
                    animations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);

    }

    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }
}