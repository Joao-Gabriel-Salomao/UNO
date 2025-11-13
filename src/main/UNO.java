package main;

import game.Game;
import cards.*;
import players.*;

public class UNO {
    public static void main(String[] args) {
        System.out.println("=== UNO Java ===");
        Game game = new Game();
        game.start();
    }
}
