package com.company;

import java.util.LinkedList;
import java.util.List;

public class CircularList {
    List<Card> list;

    public CircularList() {
        list = new LinkedList<>();
    }

    public Card pop() {
        return list.remove(0);
    }

    public void push(Card c) {
        list.add(c);
    }

    public Card open() {
        return list.get(0);
    }

    public void putBack() {
        list.add(list.remove(0));
    }
}
