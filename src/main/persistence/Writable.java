package persistence;

import org.json.JSONObject;

// CITATION: Taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Make class field data writable as json format
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}