package model;

import java.util.ArrayList;

public class Dealer {
    protected ArrayList<Card> hand;
    protected boolean bust;

    public Dealer() {
        hand = new ArrayList<>();
        bust = false;
        //Might not need this initially until the player makes a bet
        deal();
        //That one circumstance that the dealer gets 2 aces on the draw
        if (hasAce()) {
            swapAce();
        }
    }

    //For testing purposes, we pass the constructor the cards we want to test.
    public Dealer(String cardOne, String cardTwo) {
        hand = new ArrayList<>();
        bust = false;
        hand.add(new Card(cardOne));
        hand.add(new Card(cardTwo));
    }

    //MODIFIES: this
    //EFFECTS: adds a new card to the dealer's hand
    public void hit() {
        hand.add(new Card());
    }

    //MODIFIES: this
    //EFFECTS: clears the dealer's hand and deals 2 new cards.
    public void deal() {
        if (!hand.isEmpty()) {
            hand = new ArrayList<>();
        }
        hand.add(new Card());
        hand.add(new Card());
    }

    //EFFECTS: adds up the value of the cards in hand
    public int handTotal() {
        int answer = 0;
        for (Card i: hand) {
            answer += i.getCardValue();
        }
        return answer;
    }

    //EFFECTS: returns a string that only reveals the dealer's first card in their hand
    public String dealerHand() {
        return "[" + hand.get(0) + "] [ ]";
    }

    //EFFECTS: returns a string that shows the contents of the dealer's hand
    @Override
    public String toString() {
        String answer = "";
        for (Card i: hand) {
            answer += "[" + i + "] ";
        }
        return answer;
    }

    //MODIFIES: this
    //EFFECTS: sets if the dealer has busted or not given the parameter
    public void setBust(boolean bust) {
        this.bust = bust;
    }

    public boolean getBust() {
        return bust;
    }

    //EFFECTS: checks to see if the dealer's hand has an ace in it
    public boolean hasAce() {
        for (Card i: hand) {
            if (i.toString().equals("A")) {
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: an ace in blackjack can have 2 values 1 and 11. This switches
    //the ace value in the hand from the default 11 to 1;
    public void swapAce() {
        for (Card i: hand) {
            if (handTotal() > 21 && i.getCardValue() == 11) {
                i.swapAceValue();
            }
        }
    }

    //Tester method for swapAceTest()
    //MODIFIES: this
    //EFFECTS: adds a new card from the given string to the dealer's hand.
    public void addGivenCard(String face) {
        hand.add(new Card(face));
    }
}
