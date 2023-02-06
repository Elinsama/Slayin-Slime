package utilz;

public class constants {

        public static class Directions{
            public static final int LEFT = 0;
            public static final int UP = 1;
            public static final int RIGHT = 2;
            public static final int DOWN = 3;
        }

    public static class PlayerConstants{
        public static final int HURT = 0;
        public static final int IDLE = 1;
        public static final int WALK = 2;
        public static final int JUMP = 3;

        public static int GetSpriteAmount(int player_action){
            switch (player_action){
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
