package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Card {
    private String cardFace;
    private int cardValue;
    private static final ArrayList<String> deck = new ArrayList<>(
            Arrays.asList("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"));

    //This creates a random card instance
    public Card() {
        cardFace = drawCard();
        cardValue = findValue(cardFace);
    }

    //This creates a determined card instance
    public Card(String newFace) {
        cardFace = newFace;
        cardValue = findValue(cardFace);
    }

    public int getCardValue() {
        return cardValue;
    }

    @Override
    public String toString() {
        return cardFace;
    }

    //REQUIRES: this card be an Ace or "A"
    //MODIFIES: this
    //EFFECTS: when called this function will switch the Ace value from 11 to 1
    public void swapAceValue() {
        cardValue = 1;
    }

    //REQUIRES: face to contain a value in the standard deck [A,2,3,4,5,6,7,8,9,10,J,Q,K]
    //EFFECTS: converts the given face card to an representable int value.
    private static int findValue(String face) {
        int answer;
        switch (face) {
            case "J":
            case "Q":
            case "K":
                answer = 10;
                break;
            case "A":
                answer = 11;
                break;
            default:
                answer = Integer.parseInt(face);
        }
        return answer;
    }

    //MODIFIES: this
    //EFFECTS: draws a random card face from the deck and returns the face as a String
    private static String drawCard() {
        Random rand = new Random();

        return deck.get(rand.nextInt(deck.size()));
    }
}
