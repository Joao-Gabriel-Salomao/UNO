package cards;

public class WildCard extends Card {
    private String type; // "Wild", "+4"

    public WildCard(String type) {
        super("Preta");
        this.type = type;
    }

    @Override
    public String getValue() {
        return type;
    }
}
