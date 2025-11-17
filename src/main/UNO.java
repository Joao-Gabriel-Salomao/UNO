package main;

import game.Game;

public class UNO {
    public static void main(String[] args) {
        Game gameManager = new Game();
        ConsoleUI consoleUI = new ConsoleUI(gameManager);

        consoleUI.execute();
    }
}
