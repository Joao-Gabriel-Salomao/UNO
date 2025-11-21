package cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    protected String color; // "Vermelho","Azul","Verde","Amarelo" or "Preta" for wilds

    public Card(String color) {
        this.color = color;
    }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public abstract String getValue(); // number or action string

    public boolean isPlayableOn(Card top) {
        if (top == null) return true;
        if (this.color != null && this.color.equalsIgnoreCase("Preta")) return true;
        if (this.color != null && top.color != null && this.color.equalsIgnoreCase(top.color)) return true;
        if (this.getValue() != null && this.getValue().equalsIgnoreCase(top.getValue())) return true;
        return false;
    }

    @Override
    public String toString() {
        if (color == null) return getValue();
        if (color.equalsIgnoreCase("Preta")) return getValue();
        return color + " " + getValue();
    }
}
