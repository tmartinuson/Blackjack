package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertTrue(test.toString().equals("[J] [5] "));
        test.hit();
        assertTrue(test.handTotal() > handValue);
        assertFalse(test.toString().equals("[J] [5] "));
    }

    @Test
    public void dealTest() {
        assertTrue(test.toString().equals("[J] [5] "));
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
        assertTrue(test.dealerHand().equals("[J] [ ]"));
        assertTrue(test2.dealerHand().equals("[3] [ ]"));
    }

    @Test
    public void toStringTest() {
        assertTrue(test.dealerHand().equals("[J] [ ]"));
        assertTrue(test2.dealerHand().equals("[3] [ ]"));
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
    public void addGivenCardTest() {
        assertEquals(test.toString(), "[J] [5] ");
        test.addGivenCard("3");
        assertEquals(test.toString(), "[J] [5] [3] ");
    }

    @Test
    public void doubleAceDraw() {
        test = new Dealer("A","A");
    }
}
