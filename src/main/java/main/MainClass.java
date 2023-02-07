package main;

import gamestates.GameStates;

public class MainClass {

        public static void main(String[] args) {
            GameStates.isDebug = args.length >= 1 && "debug".equals(args[0]);
            new Game();
        }
}
