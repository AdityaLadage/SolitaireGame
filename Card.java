package com.company;

public class Card {
    private int faceName;
    private String color;
    private String suit;

    public Card(int faceName, String color, String suit){
        this.color = color;
        this.faceName = faceName;
        this.suit = suit;
    }

    public int getFaceName() {
        return faceName;
    }

    public String getColor() {
        return color;
    }

    public String getSuit() {
        return suit;
    }
}
