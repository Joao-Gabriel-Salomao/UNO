package players;

import cards.Card;

/*
Tipo: Subclasse de Player
Função: Representar um jogador controlado pelo computador (IA).

✔ O que essa classe faz?
- Analisa a mão automaticamente.
- Verifica cor, valor ou se é coringa.
- Joga a primeira carta válida encontrada.
*/
public class PlayerIA extends Player {

    public PlayerIA(String nome) {
        super(nome);
    }

    @Override
    public int escolherCarta(Card topoDescarte) {

        String descTopo = topoDescarte.getDescription();
        String corTopo = topoDescarte.getColor();

        for (int i = 0; i < mao.size(); i++) {
            Card c = mao.get(i);

            // Sempre pode jogar coringa
            if (c instanceof cards.WildCard) {
                return i;
            }

            // Cor igual → pode jogar
            if (c.getColor().equals(corTopo)) {
                return i;
            }

            // Descrição igual (5 com 5, Skip com Skip, etc.)
            if (c.getDescription().equals(descTopo)) {
                return i;
            }
        }

        return -1; // Nenhuma carta válida
    }

}
