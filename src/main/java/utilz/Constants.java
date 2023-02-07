package utilz;

/**
 * Static class that defines animation states and returns amount of frames.
 */
public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class EnemyConstants {

        public static final int ENEMYWALK = 1;

        public static int getSpriteAmount(int actionEnemy) {
            return 1;
        }

    }

    public static class PlayerConstants {
        public static final int HURT = 0;
        public static final int IDLE = 1;
        public static final int WALK = 2;
        public static final int JUMP = 3;


        public static int getSpriteAmount(int player_action) {
            switch (player_action) {
                case HURT:
                    return 3;
                case IDLE:
                    return 9;
                case WALK:
                    return 7;
                case JUMP:
                    return 12;
                default:
                    return 1;
            }
        }
    }
}