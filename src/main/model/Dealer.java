package model;

import java.util.ArrayList;

public class Dealer {
    protected ArrayList<Card> hand;

    public Dealer() {
        hand = new ArrayList<>();
        //Might not need this initially until the player makes a bet
        deal();
    }

    public void hit() {
        hand.add(new Card());
    }

    public void deal() {
        if (!hand.isEmpty()) {
            hand = new ArrayList<>();
        }
        hand.add(new Card());
        hand.add(new Card());
    }

    public int handTotal() {
        int answer = 0;
        for (Card i: hand) {
            answer += i.getCardValue();
        }
        return answer;
    }

    public String dealerHand() {
        return "[" + hand.get(0) + "] [ ]";
    }

    public String toString() {
        String answer = "";
        for (Card i: hand) {
            answer += "[" + i + "] ";
        }
        return answer;
    }

    public boolean hasAce() {
        return hand.contains(new Card("A"));
    }

    public void swapAce() {
        for (Card i: hand) {
            if (handTotal() > 21 && i.getCardValue() == 11) {
                i.swapAceValue();
            }
        }
    }
}
