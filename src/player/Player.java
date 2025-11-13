package player;

import java.util.*;
import cards.Card;
import cards.Deck;

public class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public void draw(Deck deck, int count) {
        for (int i = 0; i < count; i++) {
            hand.add(deck.draw());
        }
    }

    public String getName() {
        return name;
    }

    public int getHandSize() {
        return hand.size();
    }

    @Override
    public String toString() {
        return name + " (" + hand.size() + " cartas)";
    }
}
