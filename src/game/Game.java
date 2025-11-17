package game;

import java.util.*;
import players.Player;
import cards.Deck;

public class Game{
    private List<Player> players;
    private Deck deck;

    public Game() {
        players = new ArrayList<>();
        deck = new Deck();
    }

    public void start() {
        System.out.println("Iniciando o jogo UNO...");
        deck.shuffle();

        // Criar jogadores
        players.add(new Player("Jogador 1"));
        players.add(new Player("Jogador 2"));

        // Distribuir cartas
        for (Player p : players) {
            p.draw(deck, 7);
        }

        System.out.println("Cartas distribuídas! Boa sorte!");
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 192160e7516e5ea25a9620a3bffed3879585d63d
