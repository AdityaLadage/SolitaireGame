package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solitaire {

    private Stack cardStack1;
    private Stack cardStack2;
    private Stack cardStack3;
    private Stack cardStack4;
    private Stack cardStack5;
    private Stack cardStack6;
    private Stack cardStack7;
    private Stack endStack1;
    private Stack endStack2;
    private Stack endStack3;
    private Stack endStack4;
    private Stack tempStack;
    private CircularList pack;

    public static void main(String[] args) throws IOException {
        Solitaire game = new Solitaire();
        /*while(!game.isGameComplete()) {
            if(!game.checkEndStack())
                if(!game.checkCardStack())
                    game.checkPack();
        }*/

        System.out.println("*************** Choose Your Move ***************");
        System.out.println("1. Open Pack");
        System.out.println("2. Pack to EndStack");
        System.out.println("3. Pack to CardStack");
        System.out.println("4. CardStack to EndStack");
        System.out.println("5. CardStack to CardStack");
        System.out.println("6. Open CardStack");
        System.out.println("7. Shift King to empty CardStack");
        System.out.println("8. PutBack Pack\n");
        System.out.println("9. Move Multiple Cards from CardStack to CardStack\n");
        System.out.println("Enter your move: ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int val = Integer.parseInt(br.readLine());
        int name1, name2;
        String name3;

        while (!game.isGameComplete()){
            switch (val){
                case 1:
                    game.pack.open();
                    break;
                case 2:
                    System.out.print("Enter the End Stack(Hearts/Spades/Diamonds/Clubs): ");
                    System.out.println(game.movePackToEndStack(br.readLine()));
                    break;
                case 3:
                    System.out.print("Enter the Card Stack(1/2/3/4/5/6/7): ");
                    System.out.println(game.movePackToCardStack(Integer.parseInt(br.readLine())));
                    break;
                case 4:
                    System.out.print("Enter the Card Stack(1/2/3/4/5/6/7): ");
                    name1 = Integer.parseInt(br.readLine());
                    System.out.print("Enter the End Stack(Hearts/Spades/Diamonds/Clubs): ");
                    name3 = br.readLine();
                    System.out.println(game.moveCardStackToEndStack(name1, name3));
                    break;
                case 5:
                    System.out.print("Enter the Source Card Stack(1/2/3/4/5/6/7): ");
                    name1 = Integer.parseInt(br.readLine());
                    System.out.print("Enter the Destination Card Stack(1/2/3/4/5/6/7): ");
                    name2 = Integer.parseInt(br.readLine());
                    System.out.println(game.moveCardStackToCardStack(name1, name2));
                    break;
                case 6:
                    System.out.print("Enter the Card Stack(1/2/3/4/5/6/7): ");
                    System.out.println(game.openCardStack(Integer.parseInt(br.readLine())));
                    break;
                case 7:
                    System.out.print("Enter the Source Card Stack(1/2/3/4/5/6/7): ");
                    name1 = Integer.parseInt(br.readLine());
                    System.out.print("Enter the Destination Card Stack(1/2/3/4/5/6/7): ");
                    name2 = Integer.parseInt(br.readLine());
                    System.out.println(game.moveKingToCardStack(name1, name2));
                    break;
                case 8:
                    game.pack.putBack();
                    break;
                case 9:
                    System.out.print("Enter the Source Card Stack(1/2/3/4/5/6/7): ");
                    name1 = Integer.parseInt(br.readLine());
                    System.out.print("Enter the Destination Card Stack(1/2/3/4/5/6/7): ");
                    name2 = Integer.parseInt(br.readLine());
                    System.out.println(game.moveMultipleCardStackToCardStack(name1, name2));
            }
            System.out.println("Enter your next move: ");
            val = Integer.parseInt(br.readLine());
        }

    }

    private boolean isGameComplete() {
        return endStack1.getTop() == 12 && endStack2.getTop() == 12 && endStack3.getTop() == 12 && endStack4.getTop() == 12;
    }

    private String movePackToEndStack(String name) {
        if (name.equals(pack.open().getSuit())) {
            if (name.equals("Hearts") && pack.open().getFaceName() == endStack1.peek().getFaceName() + 1) {
                endStack1.push(pack.pop());
            } else if (name.equals("Spades") && pack.open().getFaceName() == endStack2.peek().getFaceName() + 1) {
                endStack2.push(pack.pop());
            } else if (name.equals("Diamonds") && pack.open().getFaceName() == endStack3.peek().getFaceName() + 1) {
                endStack3.push(pack.pop());
            } else if (name.equals("Clubs") && pack.open().getFaceName() == endStack4.peek().getFaceName() + 1) {
                endStack4.push(pack.pop());
            } else {
                return "Invalid Move.";
            }
        }
        return "Move Completed.";
    }

    private String movePackToCardStack(int name) {
        Stack move = integerToCardStack(name);
        if (!move.peek().getColor().equals(pack.open().getColor())) {
            if (move.peek().getFaceName() == pack.open().getFaceName() + 1) {
                move.push(pack.pop());
                move.setMaxOpen(move.getMaxOpen()+1);
                return "Move Completed.";
            }
        }
        return "Invalid Move.";
    }

    private String moveCardStackToEndStack(int name1, String name2) {
        Stack move1 = integerToCardStack(name1);
        Stack move2 = stringToEndStack(name2);
        if (move2.peek().getSuit().equals(move1.peek().getSuit()))
            if (move1.peek().getFaceName() == move2.peek().getFaceName() + 1) {
                move2.push(move1.pop());
                move1.setMaxOpen(move1.getMaxOpen() - 1);
                return "Move Completed.";
            }
        return "Invalid Move.";
    }

    private String moveCardStackToCardStack(int name1, int name2) {
        Stack move1 = integerToCardStack(name1);
        Stack move2 = integerToCardStack(name2);
        if (!move1.peek().getColor().equals(move2.peek().getColor()))
            if (move1.peek().getFaceName() == move2.peek().getFaceName() + 1) {
                move1.push(move2.pop());
                move1.setMaxOpen(move1.getMaxOpen() - 1);
                move2.setMaxOpen(move2.getMaxOpen() + 1);
                return "Move Completed.";
            }
        return "Invalid Move.";
    }

    private String openCardStack(int name) {
        Stack move = integerToCardStack(name);
        if (move.getMinOpen() > move.getMaxOpen() && move.getMinOpen() == move.getTop()+1) {
            move.setMaxOpen(move.getMaxOpen()-1);
            move.setMinOpen(move.getMinOpen()-1);
            return "Move Completed.";
        }
        return "Invalid Move.";
    }

    private String moveKingToCardStack(int name1, int name2) {
        Stack move1 = integerToCardStack(name1);
        Stack move2 = integerToCardStack(name2);
        if (move2.isEmpty()) {
            for (int i = move1.getMaxOpen(); i >= move1.getMinOpen(); i--) {
                tempStack.push(move1.pop());
                move1.setMaxOpen(move1.getMaxOpen()-1);
            }
            while (!tempStack.isEmpty()) {
                move2.push(tempStack.pop());
                move2.setMaxOpen(move2.getMaxOpen()+1);
            }
            return "Move Completed.";
        }
        return "Invalid Move.";
    }

    private String moveMultipleCardStackToCardStack(int name1, int name2) {
        Stack move1 = integerToCardStack(name1);
        Stack move2 = integerToCardStack(name2);
        if (!move1.peek().getColor().equals(move2.peek().getColor()))
            if (move1.peek().getFaceName() == move2.peek().getFaceName()-1) {
                for (int i = move1.getMaxOpen(); i >= move1.getMinOpen(); i--) {
                    tempStack.push(move1.pop());
                    move1.setMaxOpen(move1.getMaxOpen() - 1);
                }
                while (!tempStack.isEmpty()) {
                    move2.push(tempStack.pop());
                    move2.setMaxOpen(move2.getMaxOpen() + 1);
                }
                return "Move Completed.";
            }
        return "Invalid Move.";
    }

    private Stack integerToCardStack(int name) {
        if (name == 1) {
            return cardStack1;
        }
        else if (name == 2) {
            return cardStack2;
        }
        else if (name == 3) {
            return cardStack3;
        }
        else if (name == 4) {
            return cardStack4;
        }
        else if (name == 5) {
            return cardStack5;
        }
        else if (name == 6) {
            return cardStack6;
        }
        else {
            return cardStack7;
        }
    }

    private Stack stringToEndStack(String name) {
        if (name.equals("Hearts")) {
            return endStack1;
        } else if (name.equals("Spade")) {
            return endStack1;
        } else if (name.equals("Diamonds")) {
            return endStack1;
        } else {
            return endStack1;
        }
    }

    private Solitaire() {
        Card[] deck;
        cardStack1 = new Stack();
        cardStack2 = new Stack();
        cardStack3 = new Stack();
        cardStack4 = new Stack();
        cardStack5 = new Stack();
        cardStack6 = new Stack();
        cardStack7 = new Stack();
        endStack1 = new Stack();
        endStack2 = new Stack();
        endStack3 = new Stack();
        endStack4 = new Stack();
        tempStack = new Stack();
        pack = new CircularList();
        deck = new Card[52];
        int counter = 0;

        //creating the deck
        for (int i = 1; i < 14; i++){
            deck[counter] = new Card(i, "Red", "Hearts");
            counter++;
            deck[counter] = new Card(i, "Black", "Spades");
            counter++;
            deck[counter] = new Card(i, "Red", "Diamonds");
            counter++;
            deck[counter] = new Card(i, "Black", "Clubs");
            counter++;
        }

        //shuffling the cards
        for (int i = 0; i < 60; i++){
            int x = (int) (Math.random() * deck.length);
            Card temp = deck[0];
            deck[0] = deck[x];
            deck[x] = temp;
        }

        counter = 0;

        //setting the cards
        for (int i = 0; i < 7; i++) {
            if (i < 1) {
                cardStack1.push(deck[counter]);
                counter++;
            }
            if (i < 2) {
                cardStack2.push(deck[counter]);
                counter++;
            }
            if (i < 3) {
                cardStack3.push(deck[counter]);
                counter++;
            }
            if (i < 4) {
                cardStack4.push(deck[counter]);
                counter++;
            }
            if (i < 5) {
                cardStack5.push(deck[counter]);
                counter++;
            }
            if (i < 6) {
                cardStack6.push(deck[counter]);
                counter++;
            }

            cardStack7.push(deck[counter]);
            counter++;

        }

        cardStack1.setMinOpen(cardStack1.getTop());
        cardStack2.setMinOpen(cardStack2.getTop());
        cardStack3.setMinOpen(cardStack3.getTop());
        cardStack4.setMinOpen(cardStack4.getTop());
        cardStack5.setMinOpen(cardStack5.getTop());
        cardStack6.setMinOpen(cardStack6.getTop());
        cardStack7.setMinOpen(cardStack7.getTop());

        cardStack1.setMaxOpen(cardStack1.getTop());
        cardStack2.setMaxOpen(cardStack2.getTop());
        cardStack3.setMaxOpen(cardStack3.getTop());
        cardStack4.setMaxOpen(cardStack4.getTop());
        cardStack5.setMaxOpen(cardStack5.getTop());
        cardStack6.setMaxOpen(cardStack6.getTop());
        cardStack7.setMaxOpen(cardStack7.getTop());

        //putting the remaining cards aside
        for (int i = counter; i < deck.length; i++) {
            pack.push(deck[i]);
        }

    }

}
