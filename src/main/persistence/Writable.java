package persistence;

import org.json.JSONObject;

/**
 *  This code is mostly cited from the work in JsonSerializationDemo
 *  and is implemented to work with my Blackjack application.
 *
 *  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
