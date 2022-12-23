package persistence;

import model.PetSitter;
import model.PetSitters;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads pet-sitters data list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads pet-sitters from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PetSitters read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObj = new JSONObject(jsonData);
        return parsePetSitters(jsonObj);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private PetSitters parsePetSitters(JSONObject jsonObj) {
        PetSitters psData = new PetSitters();
        readPetSitters(psData, jsonObj);
        return psData;
    }

    // MODIFIES: psData
    // EFFECTS: parses petSitter list from JSON object and adds them to psData
    private void readPetSitters(PetSitters psData, JSONObject jsonObj) {
        JSONArray jsonArr = jsonObj.getJSONArray("petSitters");
        for (Object json : jsonArr) {
            JSONObject nextPetSitter = (JSONObject) json;
            readPetSitter(psData, nextPetSitter);
        }
    }

    // MODIFIES: psData
    // EFFECTS: parses PetSitter from JSON object and adds it to PetSitters
    private void readPetSitter(PetSitters psData, JSONObject jsonObj) {
        String usrId = jsonObj.getString("userId");
        String firstName = jsonObj.getString("firstName");
        String lastName = jsonObj.getString("lastName");
        String city = jsonObj.getString("city");
        int experience = jsonObj.getInt("experience");
        double hrRate = jsonObj.getDouble("hrRate");
        int rating = jsonObj.getInt("rating");

        PetSitter ps = new PetSitter(usrId, firstName, lastName, city, experience, hrRate);
        ps.setRating(rating);
        psData.addPetSitter(ps);
    }
}
