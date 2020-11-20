package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DealerTest {
    private Dealer test, test2, test3;
    private int handValue;

    @BeforeEach
    public void setup() {
        test = new Dealer("J","5");
        test2 = new Dealer("3","K");
        test3 = new Dealer("A","2");
        handValue = test.handTotal();
    }

    @Test
    public void hitTest() {
        assertEquals(test.toString(), "[J] [5] ");
        test.hit();
        assertTrue(test.handTotal() > handValue);
        assertNotEquals(test.toString(), "[J] [5] ");
    }

    @Test
    public void dealTest() {
        assertEquals(test.toString(), "[J] [5] ");
        //Add a large set of cards to the dealers hand to make sure that we don't randomly get the same
        //two cards values dealt in our test or if they were to equal the same amount with different cards.
        for (int i = 0; i < 20; i++) {
            test.hit();
        }
        handValue = test.handTotal();
        //Reset to 2 new random cards
        test.deal();
        assertTrue((test.handTotal() < handValue) && !(test.toString().equals("[J] [5] ")));
    }

    @Test
    public void handTotalTest() {
        assertEquals(test.handTotal(), 15);
        assertEquals(test2.handTotal(), 13);
    }

    @Test
    public void dealerHandTest() {
        assertEquals(test.dealerHand(), "[J] [ ]");
        assertEquals(test2.dealerHand(), "[3] [ ]");
    }

    @Test
    public void toStringTest() {
        assertEquals(test.dealerHand(), "[J] [ ]");
        assertEquals(test2.dealerHand(), "[3] [ ]");
    }

    @Test
    public void setBustTest() {
        assertFalse(test.getBust());
        test.setBust(true);
        assertTrue(test.getBust());
    }

    @Test
    public void hasAceTest() {
        assertFalse(test.hasAce());
        assertTrue(test3.hasAce());
    }

    @Test
    public void swapAceTest() {
        int handValue3 = test3.handTotal(); //13
        assertEquals(handValue3, 13);
        test3.addGivenCard("9"); //Dealers hand is over 21 (22)
        test3.swapAce(); //11 -> 1
        handValue3 = test3.handTotal();
        assertEquals(handValue3, 12);
    }

    @Test
    public void swapTwoAceTest() {
        //Should only swap one
        Dealer testTwoAce = new Dealer("A","A");
        assertEquals(testTwoAce.handTotal(), 22);
        testTwoAce.swapAce();
        assertEquals(testTwoAce.handTotal(), 12);
    }

    @Test
    public void swapNoAceTest() {
        assertEquals(test.handTotal(), 15);
        test.swapAce();
        assertEquals(test.handTotal(), 15);
    }

    @Test
    public void swapOneAceButHandTotalNotOver() {
        //Should not change as the hand total doesn't exceed 21
        assertEquals(test3.handTotal(), 13);
        test3.swapAce();
        assertEquals(test3.handTotal(), 13);
    }

    @Test
    public void swapNoAceHandOver() {
        assertEquals(test.handTotal(), 15);
        test.addGivenCard("J");
        test.swapAce();
        assertEquals(test.handTotal(), 25);
    }

    @Test
    public void addGivenCardTest() {
        assertEquals(test.toString(), "[J] [5] ");
        test.addGivenCard("3");
        assertEquals(test.toString(), "[J] [5] [3] ");
    }

    @Test
    public void getHandTest() {
        ArrayList<Card> hand = test.getHand();
        ArrayList<Card> hand2 = new ArrayList<>();
        hand2.add(new Card("J"));
        hand2.add(new Card("5"));
        for (int i = 0; i < hand2.size(); i++) {
            assertEquals(hand.get(i).toString(), hand2.get(i).toString());
        }
    }
}
