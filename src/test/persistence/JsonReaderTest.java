package persistence;

import model.PetSitter;
import model.PetSitters;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/non_existFile.json");
        try {
            PetSitters psData = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPetSittersPool() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPetSittersPool.json");
        try {
            PetSitters psData = reader.read();
            assertEquals(0, psData.getPetSittersNum());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPetSittersPool() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPetSittersPool.json");
        try {
            PetSitters psData = reader.read();
            List<PetSitter> petSitters = psData.getPetSitters();
            assertEquals(2, petSitters.size());
            checkPetSitter("tc", "Tom", "CAT", "Feline Kingdom", 3, 25.8,
                    5, petSitters.get(0));
            checkPetSitter("jm", "Jerry", "MOUSE", "Rat Field", 1, 15.2,
                    9, petSitters.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
