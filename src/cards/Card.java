/*Tipo: Classe abstrata
Função: Representar uma carta genérica de UNO, usada como base para todas as outras cartas.
✔ O que essa classe faz?

Define o atributo color (cor da carta).

Fornece getters e setters para encapsulamento.

Obriga todas as subclasses a implementar o método: */


package cards;

public abstract class Card {
    private String color;

    public Card(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) { 
        this.color = color; 
    }

    // Polimorfismo: cada tipo de carta tem sua própria descrição
    public abstract String getDescription();

    @Override
    public String toString() {
        return getDescription();
    }
}
