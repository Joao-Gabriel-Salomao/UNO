package players;

import cards.Card;
import java.util.ArrayList;
import java.util.List;

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
}
