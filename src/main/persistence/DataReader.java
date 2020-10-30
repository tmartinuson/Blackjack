package persistence;

import model.Player;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 *  This code is mostly cited from the work in JsonSerializationDemo
 *  and is implemented to work with my Blackjack application.
 *
 *  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

//Represents a reader that reads player data from a JSON data file.
public class DataReader {
    private String source;

    //EFFECTS: constructs a reader to read from the source file
    public DataReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads player from file and returns it;
    //throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: parses player data from the given JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Player player = new Player();
        player.setCash((int)jsonObject.get("bank"));
        player.setName(name);
        return player;
    }
}
