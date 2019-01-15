package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solitaire {

    private Stack tempStack;
    private CircularList pack;
    private Stack[] cardStacks = new Stack[7];
    private Stack[] endStacks = new Stack[4];

    public static void main(String[] args) throws IOException {
        Solitaire game = new Solitaire();
        game.displayGame();

        System.out.println("*************** Choose Your Move ***************");
        System.out.println("1. Open Pack");
        System.out.println("2. Pack to EndStack");
        System.out.println("3. Pack to CardStack");
        System.out.println("4. CardStack to EndStack");
        System.out.println("5. CardStack to CardStack");
        System.out.println("6. Open CardStack");
        System.out.println("7. Shift King to empty CardStack");
        System.out.println("8. PutBack Pack");
        System.out.println("9. Move Multiple Cards from CardStack to CardStack\n");
        System.out.print("Enter your move: ");

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
                    System.out.print("Enter the End Stack(H/S/D/C): ");
                    System.out.println(game.movePackToEndStack(br.readLine()));
                    break;
                case 3:
                    System.out.print("Enter the Card Stack(1/2/3/4/5/6/7): ");
                    System.out.println(game.movePackToCardStack(Integer.parseInt(br.readLine())));
                    break;
                case 4:
                    System.out.print("Enter the Card Stack(1/2/3/4/5/6/7): ");
                    name1 = Integer.parseInt(br.readLine());
                    System.out.print("Enter the End Stack(H/S/D/C): ");
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
            game.displayGame();
            System.out.println("Enter your next move: ");
            val = Integer.parseInt(br.readLine());
        }

    }

    private boolean isGameComplete() {
        for (int i = 0; i < 4; i++) {
            if(endStacks[i].getTop() < 12)
                return false;
        }
        return true;
    }

    private String movePackToEndStack(String name) {
        Stack move2 = stringToEndStack(name);
        if (move2.isEmpty()) {
            move2.push(pack.pop());
            return "Move Completed.";
        }
        if (move2.peek().getSuit().equals(pack.open().getSuit()))
            if (pack.open().getFaceName() == move2.peek().getFaceName() + 1) {
                move2.push(pack.pop());
                return "Move Completed.";
            }
        return "Invalid Move.";
    }

    private String movePackToCardStack(int name) {
        Stack move = cardStacks[name-1];
        if(move.isEmpty() && pack.open().getFaceName() == 13) {
            move.push(pack.pop());
            move.setMaxOpen(move.getMaxOpen()+1);
            move.setMinOpen(move.getMinOpen()+1);
            return "Move Completed";
        }
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
        Stack move1 = cardStacks[name1-1];
        Stack move2 = stringToEndStack(name2);
        if (move2.isEmpty()) {
            move2.push(move1.pop());
            move1.setMaxOpen(move1.getMaxOpen() - 1);
            return "Move Completed.";
        }
        if (move2.peek().getSuit().equals(move1.peek().getSuit()))
            if (move1.peek().getFaceName() == move2.peek().getFaceName() + 1) {
                move2.push(move1.pop());
                move1.setMaxOpen(move1.getMaxOpen() - 1);
                return "Move Completed.";
            }
        return "Invalid Move.";
    }

    private String moveCardStackToCardStack(int name1, int name2) {
        Stack move1 = cardStacks[name1-1];
        Stack move2 = cardStacks[name2-1];
        if (!move1.peek().getColor().equals(move2.peek().getColor()))
            if (move1.peek().getFaceName() == move2.peek().getFaceName() - 1) {
                move2.push(move1.pop());
                move1.setMaxOpen(move1.getMaxOpen() - 1);
                move2.setMaxOpen(move2.getMaxOpen() + 1);
                return "Move Completed.";
            }
        return "Invalid Move.";
    }

    private String openCardStack(int name) {
        Stack move = cardStacks[name-1];
        if (move.getMinOpen() > move.getMaxOpen() && move.getMinOpen() == move.getTop()+1) {
            move.setMinOpen(move.getMinOpen()-1);
            return "Move Completed.";
        }
        return "Invalid Move.";
    }

    private String moveKingToCardStack(int name1, int name2) {
        Stack move1 = cardStacks[name1-1];
        Stack move2 = cardStacks[name2-1];
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
        Stack move1 = cardStacks[name1-1];
        Stack move2 = cardStacks[name2-1];
        if (!move1.list.get(move1.getMinOpen()).getColor().equals(move2.peek().getColor())) {
            if (move1.list.get(move1.getMinOpen()).getFaceName() == move2.peek().getFaceName() - 1) {
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
        }
        return "Invalid Move.";
    }

    private Stack stringToEndStack(String name) {
        if (name.equals("H")) {
            return cardStacks[0];
        } else if (name.equals("S")) {
            return cardStacks[1];
        } else if (name.equals("D")) {
            return cardStacks[2];
        } else {
            return cardStacks[3];
        }
    }

    private void displayGame() {
        System.out.println("*************** GAME STATUS ***************\n");
        System.out.println("** PACK **\t\t**** END STACKS ****");
        System.out.println("\t\t\tEnd1\tEnd2\tEnd3\tEnd4");
        System.out.print(pack.open().getFaceName() + pack.open().getSuit() + "\t\t\t");
        for(int i = 0; i < 4; i++) {
            System.out.print(endStacks[i].peek().getFaceName() + endStacks[i].peek().getSuit() + "\t\t");
        }
        System.out.println("\n******** CARD STACKS ********");
        System.out.println("Stack1\tStack2\tStack3\tStack4\tStack5\tStack6\tStack7\t");

        int maxSize = 0;
        for (int i = 0; i < 7; i++){
            if(cardStacks[i].getTop() > maxSize) {
                maxSize = cardStacks[i].getTop();
            }
            //System.out.println(cardStacks[i].getTop());
        }

        for (int i = 0; i < maxSize+1; i++) {
            for(int j = 0; j < 7; j++) {
                if (cardStacks[j].getTop() >= i) {
                    if(cardStacks[j].getMinOpen() <= i && i <= cardStacks[j].getMaxOpen()) {
                        System.out.print(cardStacks[j].list.get(i).getFaceName() + cardStacks[j].list.get(i).getSuit() + "\t\t");
                    }
                    else{
                        System.out.print("#\t\t");
                    }
                }
                else {
                    System.out.print("\t\t");
                }
            }
            System.out.println();
        }
    }

    private Solitaire() {
        Card[] deck = new Card[52];

        for(int i = 0; i < 7; i++) {
            cardStacks[i] = new Stack();
        }

        for(int i = 0; i < 4; i++) {
            endStacks[i] = new Stack();
        }

        tempStack = new Stack();
        pack = new CircularList();
        int counter = 0;

        //creating the deck
        for (int i = 1; i < 14; i++){
            deck[counter] = new Card(i, "Red", "H");
            counter++;
            deck[counter] = new Card(i, "Black", "S");
            counter++;
            deck[counter] = new Card(i, "Red", "D");
            counter++;
            deck[counter] = new Card(i, "Black", "C");
            counter++;
        }

        //shuffling the cards
        for (int i = 0; i < 70; i++){
            int x = (int) (Math.random() * deck.length);
            Card temp = deck[0];
            deck[0] = deck[x];
            deck[x] = temp;
        }

        counter = 0;

        //setting the cards
        for (int i = 0; i < 7; i++) {
            for(int j = 0; j < 7; j++) {
                if(i <= j) {
                    cardStacks[j].push(deck[counter]);
                    counter++;
                }
            }
        }

        for(int i = 0; i < 7; i++) {
            cardStacks[i].setMinOpen(cardStacks[i].getTop());
            cardStacks[i].setMaxOpen(cardStacks[i].getTop());
        }

        //putting the remaining cards aside
        for (int i = counter; i < deck.length; i++) {
            pack.push(deck[i]);
        }

    }

}
