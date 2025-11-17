package cards;

import java.util.*;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
        String[] colors = {"Vermelho", "Azul", "Verde", "Amarelo"};
        for (String color : colors) {
            for (int i = 0; i <= 9; i++) {
                cards.push(new Card(color, String.valueOf(i)));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        return cards.pop();
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 192160e7516e5ea25a9620a3bffed3879585d63d
