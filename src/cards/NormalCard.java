package cards;

public class NormalCard extends Card {
    private int number;

    public NormalCard(String color, int number) {
        super(color);
        this.number = number;
    }

    @Override
    public String getValue() {
        return String.valueOf(number);
    }
}
