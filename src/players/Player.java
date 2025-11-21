package players;

<<<<<<< HEAD
import java.io.Serializable;
import java.util.*;
=======
>>>>>>> 64bc7e3b6f37c24ac68d79e46d5f778d6bf5266c
import cards.Card;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
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
=======
/*
Tipo: Classe abstrata
Função: Representar um jogador genérico (base para Humano e IA).

✔ O que essa classe faz?
- Armazena o nome do jogador.
- Mantém a mão de cartas.
- Fornece métodos para receber e jogar cartas.
- Obriga subclasses a implementar escolherCarta().
*/
public abstract class Player {

    protected final String nome;
    protected final List<Card> mao;

    public Player(String nome) {
        this.nome = nome;
        this.mao = new ArrayList<>();
    }

    public void receberCarta(Card card) {
        mao.add(card);
    }

    public Card jogarCarta(int index) {
        return mao.remove(index);
    }

    // Jogador escolhe a carta baseado no tipo (humano / IA).
    public abstract int escolherCarta(Card topoDescarte);

    public String getNome() {
        return nome;
    }

    public List<Card> getMao() {
        return mao;
    }
>>>>>>> 64bc7e3b6f37c24ac68d79e46d5f778d6bf5266c
}
