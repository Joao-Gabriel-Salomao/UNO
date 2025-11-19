/*ActionCard.java
Tipo: Subclasse de Card
Função: Representar cartas especiais de ação:

Skip (pula turno)

Reverse (inverte ordem)

+2 (próximo compra duas) */


package cards;

public class ActionCard extends Card {
    private String action; // "Skip", "Reverse", "+2"

    public ActionCard(String color, String action) {
        super(color);
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String getDescription() {
        return getColor() + " " + action;
    }
}
