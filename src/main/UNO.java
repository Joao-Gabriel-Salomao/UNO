package main;

import game.Game;

public class UNO {
    public static void main(String[] args) {
        Game game = new Game();
        ConsoleUI ui = new ConsoleUI(game);
        ui.execute();
    }
}
