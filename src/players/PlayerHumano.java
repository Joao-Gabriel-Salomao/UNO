package players;

import cards.Card;
import java.util.Scanner;

/*
Tipo: Subclasse de Player
Função: Representar um jogador humano, que escolhe a carta via console.

✔ O que essa classe faz?
- Mostra as cartas no console.
- Pede ao usuário um número (índice da carta).
- Retorna o índice informado pelo usuário.
*/
public class PlayerHumano extends Player {

    private final Scanner scanner = new Scanner(System.in);

    public PlayerHumano(String nome) {
        super(nome);
    }

    @Override
    public int escolherCarta(Card topoDescarte) {
        System.out.println("\nCartas de " + nome + ":");

        for (int i = 0; i < mao.size(); i++) {
            System.out.println(i + " - " + mao.get(i));
        }

        System.out.println("Topo do descarte: " + topoDescarte);
        System.out.print("Escolha uma carta (ou -1 para passar): ");

        return scanner.nextInt();
    }
}
