/*unção: Representar as cartas numéricas (0 a 9). */


package cards;

public class NormalCard extends Card {
    private int number;

    public NormalCard(String color, int number) {
        super(color);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String getDescription() {
        return getColor() + " " + number;
    }
}
