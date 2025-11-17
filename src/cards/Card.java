package cards;

public class Card {
    private String color;
    private String value;

    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return color + " " + value;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 192160e7516e5ea25a9620a3bffed3879585d63d
