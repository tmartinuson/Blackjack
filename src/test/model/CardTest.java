package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {
    @Test
    public void swapAceValueTest() {
        Card testAce = new Card("A");
        assertEquals(testAce.getCardValue(), 11);
        testAce.swapAceValue();
        assertEquals(testAce.getCardValue(), 1);
    }
    @Test
    public void toStringTest() {
        Card test1 = new Card("J");
        Card test2 = new Card("5");
        assertEquals(test1.toString(), "J");
        assertEquals(test2.toString(), "5");
    }
}
