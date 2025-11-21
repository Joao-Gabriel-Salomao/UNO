package cards;

public class ActionCard extends Card {
    private String action; // "Skip", "Reverse", "+2"

    public ActionCard(String color, String action) {
        super(color);
        this.action = action;
    }

    @Override
    public String getValue() {
        return action;
    }
}
