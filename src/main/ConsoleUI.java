package main;

import java.util.*;
import game.Game;
import game.Game.GameException;
import players.Player;
import cards.Card;
import cards.WildCard;

public class ConsoleUI {
    private final Game gameManager;
    private final Scanner reader;

    public ConsoleUI(Game gameManager) {
        this.gameManager = gameManager;
        this.reader = new Scanner(System.in);
    }

    public void execute() {
        System.out.println("=========================================");
        System.out.println("  SISTEMA UNO - CONSOLE");
        System.out.println("=========================================");
        int choice;
        do {
            displayMenu();
            try {
                System.out.print("Escolha uma opcao: ");
                choice = Integer.parseInt(reader.nextLine().trim());
                handleChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Entrada invalida. Digite numero.");
                choice = -1;
            } catch (GameException e) {
                System.out.println("[ERRO DE JOGO] " + e.getMessage());
                choice = -1;
            } catch (Exception e) {
                System.out.println("[ERRO INTERNO] " + e.getMessage());
                choice = -1;
            }
        } while (choice != 0);
        System.out.println("Saindo. Ate mais!");
    }

    private void displayMenu() {
        System.out.println("\n--- MENU (Status: " + (gameManager.isMatchActive() ? "ATIVO" : "INATIVO") + ") ---");
        System.out.println("1. Cadastrar Jogador");
        System.out.println("2. Iniciar Partida");
        System.out.println("3. Encerrar Partida");
        System.out.println("4. Jogar (turno interativo)");
        System.out.println("5. Comprar carta (manual)");
        System.out.println("6. Ver status");
        System.out.println("7. Salvar/Carregar");
        System.out.println("0. Sair");
        System.out.println("-----------------------------------------");
    }

    private void handleChoice(int choice) throws GameException {
        switch (choice) {
            case 1:
                System.out.print("Nome do jogador: ");
                String n = reader.nextLine().trim();
                gameManager.registerPlayer(n);
                System.out.println("Jogador cadastrado: " + n);
                break;
            case 2:
                gameManager.startGame();
                System.out.println("Partida iniciada. Carta inicial: " + gameManager.getTopCard());
                break;
            case 3:
                gameManager.endGame();
                System.out.println("Partida encerrada.");
                break;
            case 4:
                if (!gameManager.isMatchActive()) { System.out.println("Nenhuma partida ativa."); break; }
                runMatchLoop();
                break;
            case 5:
                handleBuyCard();
                break;
            case 6:
                displayStatus();
                break;
            case 7:
                handlePersistence();
                break;
            case 0:
                break;
            default:
                System.out.println("Opcao invalida.");
        }
    }

    private void runMatchLoop() {
        while (gameManager.isMatchActive()) {
            Player current = gameManager.getCurrentPlayer();
            System.out.println("\n----- TURNO: " + current.getName() + " -----");
            System.out.println("Carta na mesa: " + (gameManager.getTopCard() == null ? "nenhuma" : gameManager.getTopCard()));
            current.showHand();
            System.out.println("Acoes: 1-Jogar carta  2-Comprar carta  3-Ver status  0-Sair partida");
            System.out.print("Escolha: ");
            String raw = reader.nextLine().trim();
            if (raw.equals("1")) {
                System.out.print("Indice da carta para jogar: ");
                String idxs = reader.nextLine().trim();
                int idx;
                try {
                    idx = Integer.parseInt(idxs);
                } catch (NumberFormatException e) {
                    System.out.println("Indice invalido.");
                    continue;
                }
                try {
                    // play
                    Card c = null;
                    try {
                        gameManager.playCard(current.getName(), idx);
                        c = gameManager.getTopCard();
                        // if it was a wild, ask for color
                        if (c instanceof WildCard) {
                            System.out.println("Escolha cor: 1-Vermelho 2-Azul 3-Verde 4-Amarelo");
                            System.out.print("Opcao: ");
                            String o = reader.nextLine().trim();
                            String col = "Vermelho";
                            if (o.equals("2")) col = "Azul";
                            else if (o.equals("3")) col = "Verde";
                            else if (o.equals("4")) col = "Amarelo";
                            gameManager.setTopCardColor(col);
                            System.out.println("Cor escolhida: " + col);
                        }
                        System.out.println(current.getName() + " jogou com sucesso.");
                    } catch (GameException ge) {
                        System.out.println("Jogada invalida: " + ge.getMessage());
                    }
                } catch (Exception e) {
                    System.out.println("Erro ao jogar: " + e.getMessage());
                }
            } else if (raw.equals("2")) {
                try {
                    gameManager.drawCard(current.getName());
                    System.out.println(current.getName() + " comprou carta.");
                } catch (GameException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else if (raw.equals("3")) {
                displayStatus();
            } else if (raw.equals("0")) {
                System.out.println("Saindo do modo partida (retornando ao menu principal).");
                break;
            } else {
                System.out.println("Opcao invalida.");
            }
        }
        if (!gameManager.isMatchActive()) {
            System.out.println("Partida finalizada. Voltando ao menu.");
        }
    }

    private void handleBuyCard() {
        System.out.println("Jogadores:");
        List<Player> pl = gameManager.getPlayers();
        pl.forEach(p -> System.out.println(" - " + p.getName()));
        System.out.print("Nome do jogador que vai comprar: ");
        String name = reader.nextLine().trim();
        try {
            gameManager.drawCard(name);
            System.out.println(name + " comprou carta.");
        } catch (GameException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void displayStatus() {
        System.out.println("Partida ativa: " + gameManager.isMatchActive());
        System.out.println("Carta topo: " + gameManager.getTopCard());
        System.out.println("Jogadores:");
        for (Player p : gameManager.getPlayers()) {
            System.out.println(" - " + p.getName() + " (" + p.getHandSize() + " cartas)");
        }
        System.out.println("Historico:");
        gameManager.getHistory().forEach(h -> System.out.println(" - " + h));
    }

    private void handlePersistence() {
        System.out.print("Arquivo (.dat): ");
        String f = reader.nextLine().trim();
        System.out.print("1-Salvar 2-Carregar: ");
        String o = reader.nextLine().trim();
        if (o.equals("1")) {
            gameManager.saveState(f);
            System.out.println("Salvo em " + f);
        } else if (o.equals("2")) {
            try {
                gameManager.loadState(f);
                System.out.println("Carregado de " + f);
            } catch (GameException e) {
                System.out.println("Erro ao carregar: " + e.getMessage());
            }
        } else System.out.println("Opcao invalida.");
    }
}
