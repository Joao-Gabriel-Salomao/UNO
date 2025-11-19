/*Tipo: Subclasse de Card
Função: Representar cartas coringa:

Wild (escolhe a cor)

+4 (coringa + compra 4) */


package cards;

public class WildCard extends Card {
    private String type; // "Wild" ou "+4"

    public WildCard(String type) {
        super("Preta"); // cor padrão
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return type;
    }
}
