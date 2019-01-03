package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Stack {
    private int minOpen;
    private int maxOpen;
    private List<Card> list;

    public Stack() {
        list = new ArrayList<>();
    }

    public int getMinOpen() {
        return minOpen;
    }

    public void setMinOpen(int minOpen) {
        this.minOpen = minOpen;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    public void setMaxOpen(int maxOpen) {
        this.maxOpen = maxOpen;
    }

    public void push(Card c) {
        list.add(c);
    }

    public int getTop() {
        return (list.size()-1);
    }

    public Card pop() {
        if(list.size() == 0)
            throw new NoSuchElementException("Underflow Exception");
        return list.remove(list.size()-1);
    }

    public Card peek() {
        if(list.size() == 0)
            throw new NoSuchElementException("Underflow Exception");
        return list.get(list.size()-1);
    }

    public boolean isEmpty() {
        return (list.size() == 0);
    }
}
