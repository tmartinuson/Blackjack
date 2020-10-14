package model;

import java.util.ArrayList;

// Represents the dealer with the methods required to play a game of Blackjack
public class Dealer {
    protected ArrayList<Card> hand;         // stores the cards in the dealer's possession
    protected boolean bust;                 // conditional if the dealer has busted or not

    //EFFECTS: creates a new empty hand for the dealer,
    // sets the dealers bust to false,
    // deals the dealer 2 new cards,
    // and swaps the ace out in case of 2 aces appearing in a hand.
    public Dealer() {
        hand = new ArrayList<>();
        bust = false;
        deal();
        //That one odd circumstance that the dealer gets 2 aces on the draw
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

    /**Could merge this into swapAce() later to save time on O(n). (2 searches for the same answer essentially) **/
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

    /** Tester method for swapAceTest() **/
    //MODIFIES: this
    //EFFECTS: adds a new card from the given string to the dealer's hand.
    public void addGivenCard(String face) {
        hand.add(new Card(face));
    }
}
