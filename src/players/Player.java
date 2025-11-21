package players;

import java.io.Serializable;
import java.util.*;
import cards.Card;
import cards.Deck;

public class Player implements Serializable {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() { return name; }

    public void clearHand() { hand.clear(); }

    public void draw(Deck deck, int count) {
        for (int i = 0; i < count; i++) {
            Card c = deck.draw();
            if (c != null) hand.add(c);
        }
    }

    public List<Card> getHand() { return hand; }

    public void showHand() {
        System.out.println("Mao de " + name + ":");
        if (hand.isEmpty()) {
            System.out.println(" (vazia)");
            return;
        }
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(" [" + i + "] " + hand.get(i));
        }
    }

    public Card playCard(int index) {
        if (index < 0 || index >= hand.size()) return null;
        return hand.remove(index);
    }

    public int getHandSize() { return hand.size(); }
}
