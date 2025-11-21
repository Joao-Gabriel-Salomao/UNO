package game;

import java.io.*;
import java.util.*;
import players.Player;
import cards.*;

public class Game implements Serializable {
    private List<Player> players;
    private Deck deck;
    private boolean matchActive;
    private Map<String,Integer> score;
    private List<String> history;

    // turn control
    private int currentIndex;
    private int direction; // 1 or -1
    private Card topCard;
    private boolean skipNext;
    private int drawPenalty; // number of cards next player must draw (from +2 / +4 stacking simple)

    public static class GameException extends Exception {
        public GameException(String msg) { super(msg); }
    }

    public Game() {
        players = new ArrayList<>();
        score = new HashMap<>();
        history = new ArrayList<>();
        matchActive = false;
        currentIndex = 0;
        direction = 1;
        topCard = null;
        skipNext = false;
        drawPenalty = 0;
    }

    public void registerPlayer(String name) throws GameException {
        if (matchActive) throw new GameException("Nao e possivel cadastrar com partida ativa.");
        for (Player p : players) if (p.getName().equalsIgnoreCase(name)) throw new GameException("Ja existe um jogador com esse nome.");
        Player np = new Player(name);
        players.add(np);
        score.put(name,0);
        history.add("Jogador cadastrado: " + name);
    }

    public void startGame() throws GameException {
        if (players.size() < 2) throw new GameException("E necessario pelo menos 2 jogadores.");
        if (matchActive) throw new GameException("A partida ja esta ativa.");
        deck = new Deck();
        deck.shuffle();
        matchActive = true;
        for (Player p : players) { p.clearHand(); p.draw(deck,7); }
        topCard = deck.draw();
        // if topCard is wild, set a default color to first player's first card color if exists (simple fix)
        if (topCard instanceof WildCard) {
            topCard.setColor("Vermelho"); // default
        }
        currentIndex = 0;
        direction = 1;
        skipNext = false;
        drawPenalty = 0;
        history.add("Partida iniciada. Carta inicial: " + (topCard == null ? "nenhuma": topCard.toString()));
    }

    public void endGame() throws GameException {
        if (!matchActive) throw new GameException("Nenhuma partida ativa.");
        matchActive = false;
        history.add("Partida encerrada.");
    }

    public boolean isMatchActive() { return matchActive; }

    public List<Player> getPlayers() { return players; }

    public Deck getDeck() { return deck; }

    public Map<Player,Integer> getScore() { // compatibility with ConsoleUI expecting Map<Player,Integer>
        Map<Player,Integer> m = new LinkedHashMap<>();
        for (Player p : players) m.put(p, score.getOrDefault(p.getName(),0));
        return m;
    }

    public List<String> getHistory() { return history; }

    public Card getTopCard() { return topCard; }

    // get current player name
    public Player getCurrentPlayer() { return players.isEmpty() ? null : players.get(currentIndex); }

    // draw card action (for player to draw manually or penalty)
    public void drawCard(String playerName) throws GameException {
        if (!matchActive) throw new GameException("A partida nao esta ativa.");
        Player p = findByName(playerName);
        if (p == null) throw new GameException("Jogador nao encontrado.");
        if (drawPenalty > 0) {
            p.draw(deck, drawPenalty);
            history.add(playerName + " recebeu penalidade e comprou " + drawPenalty + " cartas.");
            drawPenalty = 0;
            advanceTurn(); // after penalty, skip turn
            return;
        }
        p.draw(deck,1);
        history.add(playerName + " comprou 1 carta.");
        advanceTurn();
    }

    // play card by index (validates and applies effects)
    public void playCard(String playerName, int index) throws GameException {
        if (!matchActive) throw new GameException("A partida nao esta ativa.");
        Player p = findByName(playerName);
        if (p == null) throw new GameException("Jogador nao encontrado.");
        if (players.get(currentIndex) != p) throw new GameException("Nao e a vez de " + playerName + ".");
        if (index < 0 || index >= p.getHandSize()) throw new GameException("Indice invalido.");
        Card chosen = p.playCard(index);
        if (chosen == null) throw new GameException("Indice invalido.");

        if (topCard != null && !chosen.isPlayableOn(topCard)) {
            // return card to player's hand end
            p.getHand().add(chosen);
            throw new GameException("Carta invalida para jogar sobre a carta atual.");
        }

        // valid play
        // if wild, allow player to set color (we will not have UI here; ConsoleUI will call setTopCardColor after play if wild)
        topCard = chosen;
        deck.discard(chosen);
        history.add(playerName + " jogou: " + chosen.toString());

        // effects
        if (chosen instanceof ActionCard) {
            String act = chosen.getValue();
            if (act.equalsIgnoreCase("Skip")) {
                advanceTurn(); // skip next
                history.add("Acao: Skip - proximo jogador pulado.");
            } else if (act.equalsIgnoreCase("Reverse")) {
                direction *= -1;
                history.add("Acao: Reverse - direcao invertida.");
                // if two players, reverse acts like skip (immediately advance)
                if (players.size() == 2) advanceTurn();
            } else if (act.equalsIgnoreCase("+2")) {
                drawPenalty += 2;
                history.add("Acao: +2 - proxima penalidade aumentada em 2.");
            }
        } else if (chosen instanceof WildCard) {
            String type = chosen.getValue();
            if (type.equalsIgnoreCase("+4")) {
                drawPenalty += 4;
                history.add("Acao: +4 - proxima penalidade aumentada em 4.");
            } else {
                history.add("Acao: Wild - cor sera escolhida pelo jogador.");
            }
        }

        // check win
        if (p.getHandSize() == 0) {
            matchActive = false;
            history.add(playerName + " venceu a partida!");
        } else {
            // if penalty exists, next player will suffer on draw or be able to stack if they play +2/+4
            advanceTurn();
        }
    }

    private void advanceTurn() {
        if (players.isEmpty()) return;
        if (direction == 1) currentIndex = (currentIndex + 1) % players.size();
        else currentIndex = (currentIndex - 1 + players.size()) % players.size();
    }

    private Player findByName(String name) {
        for (Player p : players) if (p.getName().equalsIgnoreCase(name)) return p;
        return null;
    }

    // helper to set topCard color after a wild chosen
    public void setTopCardColor(String color) {
        if (topCard != null) topCard.setColor(color);
        history.add("Cor escolhida: " + color);
    }

    // persistence
    public void saveState(String file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(this);
            out.close();
            history.add("Estado salvo em: " + file);
        } catch (Exception e) {
            history.add("Erro ao salvar: " + e.getMessage());
        }
    }

    public void loadState(String file) throws GameException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Game loaded = (Game) in.readObject();
            in.close();
            this.players = loaded.players;
            this.deck = loaded.deck;
            this.matchActive = loaded.matchActive;
            this.score = loaded.score;
            this.history = loaded.history;
            this.currentIndex = loaded.currentIndex;
            this.direction = loaded.direction;
            this.topCard = loaded.topCard;
            this.skipNext = loaded.skipNext;
            this.drawPenalty = loaded.drawPenalty;
            history.add("Estado carregado do arquivo: " + file);
        } catch (Exception e) {
            throw new GameException("Falha ao carregar arquivo.");
        }
    }
}
