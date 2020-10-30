package persistence;

import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *  This code is mostly cited from the work in JsonSerializationDemo
 *  and is implemented to work with my Blackjack application.
 *
 *  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class DataWriterTest {

    @Test
    void testWriteInvalidFile() {
        try {
            Player player = new Player();
            DataWriter writer = new DataWriter("./data/\0bad:file- name.json");
            writer.open();
            fail("File should not have opened");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriteEmptyPlayer() {
        try {
            Player player = new Player();
            player.setCash(0);
            player.setName("");
            DataWriter writer = new DataWriter("./data/testWriteEmptyPlayer.json");
            writer.open();
            writer.write(player);
            writer.close();

            DataReader reader = new DataReader("./data/testWriteEmptyPlayer.json");
            player = reader.read();
            assertEquals("", player.getName());
            assertEquals(0, player.getCash());
        } catch (IOException e) {
            fail("Exception shouldn't be thrown");
        }
    }

    @Test
    void testWritePlayer() {
        try {
            Player player = new Player();
            player.setCash(4750);
            player.setName("Alice");
            DataWriter writer = new DataWriter("./data/testWritePlayer.json");
            writer.open();
            writer.write(player);
            writer.close();

            DataReader reader = new DataReader("./data/testWritePlayer.json");
            player = reader.read();
            assertEquals("Alice", player.getName());
            assertEquals(4750, player.getCash());
        } catch (IOException e) {
            fail("Exception shouldn't be thrown");
        }
    }
}
