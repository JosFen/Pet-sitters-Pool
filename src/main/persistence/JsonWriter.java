package persistence;

import model.PetSitters;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of PetSitters to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String fileDestination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        fileDestination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileDestination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of PetSitters to file
    public void write(PetSitters psData) {
        JSONObject json = psData.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}

