package persistence;

import model.Player;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *  This code is mostly cited from the work in JsonSerializationDemo
 *  and is implemented to work with my Blackjack application.
 *
 *  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

//Represents a writer that stores the player's data into a JSON file.
public class DataWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String location;

    //EFFECTS: constructs writer to write to destination file
    public DataWriter(String location) {
        this.location = location;
    }

    //MODIFIES: this
    //EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    //be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(location));
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representation of player to file
    public void write(Player player) {
        JSONObject json = player.toJson();
        saveToFile(json.toString(TAB));
    }

    //MODIFIES: this
    //EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
