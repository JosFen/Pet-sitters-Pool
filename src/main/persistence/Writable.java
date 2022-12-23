package persistence;

import org.json.JSONObject;

// Make class field data writable as json format
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}