package persistence;

import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  This code is mostly cited from the work in JsonSerializationDemo
 *  and is implemented to work with my Blackjack application.
 *
 *  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class DataReaderTest {

    @Test
    void testReadNonExistFile() {
        DataReader reader = new DataReader("./data/nonExistentFile.json");
        try {
            Player player = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReadEmptyFile() {
        DataReader reader = new DataReader("./data/testReadEmptyFile.json");
        try {
            Player player = reader.read();
            assertEquals("", player.getName());
            assertEquals(0, player.getCash());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    void testReadExistingFile() {
        DataReader reader = new DataReader("./data/testReadFile.json");
        try {
            Player player = reader.read();
            assertEquals("Edward", player.getName());
            assertEquals(7490, player.getCash());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }
}
