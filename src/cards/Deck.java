/*5) Deck.java
Tipo: Classe de gerenciamento
Função: Criar e controlar o baralho completo do UNO (108 cartas). */



package cards;

import java.util.*;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
        createFullUnoDeck();
        shuffle();
    }

    private void createFullUnoDeck() {
        String[] colors = {"Vermelho", "Azul", "Verde", "Amarelo"};

        // NUMÉRICAS
        for (String c : colors) {
            cards.add(new NormalCard(c, 0));
            for (int i = 1; i <= 9; i++) {
                cards.add(new NormalCard(c, i));
                cards.add(new NormalCard(c, i)); 
            }
        }

        // CARTAS DE AÇÃO
        String[] actions = {"Skip", "Reverse", "+2"};
        for (String c : colors) {
            for (String a : actions) {
                cards.add(new ActionCard(c, a));
                cards.add(new ActionCard(c, a));
            }
        }

        // CORINGAS
        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard("Wild"));
            cards.add(new WildCard("+4"));
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() { 
        return cards.isEmpty() ? null : cards.pop(); 
    }

    public boolean isEmpty() { 
        return cards.isEmpty(); 
    }

    public int size() { 
        return cards.size(); 
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 192160e7516e5ea25a9620a3bffed3879585d63d
