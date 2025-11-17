package main;

import java.util.*;
import game.Game;
import game.Game.GameException; 
import players.Player;

public class ConsoleUI {
    private final Game gameManager;
    private final Scanner reader;

    public ConsoleUI(Game gameManager) {
        this.gameManager = gameManager;
        this.reader = new Scanner(System.in);
    }

    public void execute() {
        System.out.println("=========================================");
        System.out.println("  Sistema de Gerenciamento do Jogo UNO");
        System.out.println("=========================================");
        
        int choice;
        do {
            displayMenu();
            try {
                System.out.print("Escolha uma opção: ");
                choice = reader.nextInt();
                reader.nextLine(); 
                
                handleChoice(choice);
                
            } catch (InputMismatchException e) {
                System.out.println("\n[ERRO] Entrada inválida. Por favor, digite um número.\n");
                reader.nextLine(); 
                choice = -1;
            } catch (GameException e) {
                System.out.println("\n[ERRO DE JOGO] " + e.getMessage() + "\n");
                choice = -1;
            } catch (Exception e) {
                 System.out.println("\n[ERRO INTERNO] Ocorreu um erro inesperado: " + e.getMessage() + "\n");
                 choice = -1;
            }
        } while (choice != 0);
        System.out.println("Aplicação encerrada. Até logo!");
        reader.close();
    }

    private void displayMenu() {
        System.out.println("\n--- MENU PRINCIPAL (Status: " + (gameManager.isMatchActive() ? "ATIVO" : "INATIVO") + ") ---");
        System.out.println("1. Cadastrar Jogador");
        System.out.println("2. Iniciar Partida");
        System.out.println("3. Encerrar Partida");
        System.out.println("4. Jogar Carta (Simular Ação: Comprar Carta)"); 
        System.out.println("5. Visualizar Status e Placar Completo");
        System.out.println("6. Salvar/Carregar Estado do Jogo (Persistência)");
        System.out.println("0. Sair");
        System.out.println("-----------------------------------------");
    }

    private void handleChoice(int choice) throws GameException {
        switch (choice) {
            case 1: 
                System.out.print("Nome do Novo Jogador: ");
                String playerName = reader.nextLine();
                gameManager.registerPlayer(playerName); 
                System.out.println("-> Jogador '" + playerName + "' cadastrado com sucesso!");
                break;
            case 2: 
                gameManager.startGame(); 
                break;
            case 3: 
                gameManager.endGame(); 
                break;
            case 4: 
                handleGameOperation(); 
                break;
            case 5: 
                viewStatus();
                break;
            case 6: 
                handlePersistence();
                break;
            case 0:
                break;
            default:
                System.out.println("[AVISO] Opção não reconhecida. Tente novamente.");
        }
    }
    
    private void handleGameOperation() throws GameException {
        System.out.println("\n--- 4. OPERAÇÃO: COMPRAR CARTA ---");
        System.out.println("Jogadores Disponíveis:");
        
        List<Player> availablePlayers = gameManager.getPlayers(); 
        availablePlayers.forEach(p -> System.out.println(" - " + p.getName()));
        
        System.out.print("Digite o nome do jogador que irá comprar uma carta: ");
        String playerToDraw = reader.nextLine();
        
        gameManager.drawCard(playerToDraw);
        System.out.println("-> " + playerToDraw + " comprou uma carta com sucesso!");
    }
    
    private void viewStatus() {
        System.out.println("\n--- 5. STATUS ATUAL DA PARTIDA ---");
        System.out.println("Partida Ativa: " + gameManager.isMatchActive());
        
        List<Player> playerList = gameManager.getPlayers(); 
        System.out.println("Total de Jogadores Cadastrados: " + playerList.size());
        
        System.out.println("\n[A] JOGADORES E CARTAS NA MÃO:");
        if (playerList.isEmpty()) {
            System.out.println(" - Nenhum jogador cadastrado.");
        } else {
            for(Player player : playerList) {
                System.out.println(" - " + player.getName() + " (Cartas: " + player.getHandSize() + ")"); 
            }
        }
        
        if (gameManager.isMatchActive() && gameManager.getDeck() != null) {
            System.out.println("\n[B] ESTADO DO BARALHO:");
            System.out.println(" - Cartas no Baralho: " + gameManager.getDeck().getDeckSize());
        }
        
        System.out.println("\n[C] PLACAR (PONTUAÇÃO ATUAL):");
        Map<Player, Integer> score = gameManager.getScore();
        if (score.isEmpty()) {
            System.out.println(" - Placar indisponível ou vazio.");
        } else {
            score.forEach((p, s) -> System.out.println(" - " + p.getName() + ": " + s + " pontos"));
        }
        
        System.out.println("\n[D] HISTÓRICO DE OPERAÇÕES:");
        List<String> history = gameManager.getHistory();
        if (history.isEmpty()) {
            System.out.println(" - Histórico vazio.");
        } else {
            history.forEach(op -> System.out.println(" - " + op));
        }
    }
    
    private void handlePersistence() {
        System.out.println("\n--- 6. PERSISTÊNCIA (SALVAR/CARREGAR) ---");
        System.out.print("Digite o nome do arquivo (.dat): ");
        String fileName = reader.nextLine();
        
        System.out.print("1. Salvar / 2. Carregar. Escolha: ");
        int persistenceChoice;
        try {
            persistenceChoice = reader.nextInt();
            reader.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("[ERRO] Entrada inválida. Esperado 1 ou 2.");
            reader.nextLine();
            return;
        }

        if (persistenceChoice == 1) {
            gameManager.saveState(fileName);
            System.out.println("-> Estado do jogo salvo com sucesso em '" + fileName + "'.");
        } else if (persistenceChoice == 2) {
            try {
                 gameManager.loadState(fileName);
                 System.out.println("-> Estado do jogo carregado com sucesso. Partida pronta para demonstração.");
            } catch (GameException e) {
                 System.out.println("[ERRO AO CARREGAR] " + e.getMessage());
            }
        } else {
            System.out.println("[AVISO] Escolha inválida. Use 1 para Salvar ou 2 para Carregar.");
        }
    }
}