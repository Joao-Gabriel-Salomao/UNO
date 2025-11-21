package cards;

import java.util.*;
import java.io.Serializable;

public class Deck implements Serializable {
    private Stack<Card> drawPile;
    private Stack<Card> discardPile;

    public Deck() {
        drawPile = new Stack<>();
        discardPile = new Stack<>();
        createFullUnoDeck();
        shuffle();
    }

    private void createFullUnoDeck() {
        String[] colors = {"Vermelho","Azul","Verde","Amarelo"};
        for (String c : colors) {
            drawPile.add(new NormalCard(c,0));
            for (int i = 1; i <= 9; i++) {
                drawPile.add(new NormalCard(c,i));
                drawPile.add(new NormalCard(c,i));
            }
        }
        String[] actions = {"Skip","Reverse","+2"};
        for (String c : colors) {
            for (String a : actions) {
                drawPile.add(new ActionCard(c,a));
                drawPile.add(new ActionCard(c,a));
            }
        }
        for (int i = 0; i < 4; i++) {
            drawPile.add(new WildCard("Wild"));
            drawPile.add(new WildCard("+4"));
        }
    }

    public void shuffle() {
        Collections.shuffle(drawPile);
    }

    public Card draw() {
        if (drawPile.isEmpty()) refillFromDiscard();
        return drawPile.isEmpty() ? null : drawPile.pop();
    }

    public void discard(Card c) {
        if (c != null) discardPile.push(c);
    }

    public Card topDiscard() {
        return discardPile.isEmpty() ? null : discardPile.peek();
    }

    private void refillFromDiscard() {
        if (discardPile.isEmpty()) return;
        Card top = discardPile.pop();
        List<Card> rest = new ArrayList<>(discardPile);
        discardPile.clear();
        Collections.shuffle(rest);
        drawPile.addAll(rest);
        discardPile.push(top);
    }

    public int drawSize() { return drawPile.size(); }
    public int discardSize() { return discardPile.size(); }
}
