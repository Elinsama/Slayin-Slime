package gamestates;

public enum GameStates {

    PLAYING, MENU, GAME_OVER;

    public  static GameStates state = MENU;

    public static boolean isDebug = false;
}
