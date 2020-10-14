package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    private Player test;

    @BeforeEach
    public void setup() {
        test = new Player();
        test.setCash(5000);
    }

    @Test
    public void subtractCashTest() {
        assertEquals(test.getCash(), 5000);
        test.subtractCash(100);
        assertEquals(test.getCash(), 4900);
    }

    @Test
    public void addCashTest() {
        assertEquals(test.getCash(), 5000);
        test.addCash(100);
        assertEquals(test.getCash(), 5100);
    }
}
