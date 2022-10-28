package persistence;

import model.PetSitter;
import model.PetSitters;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            PetSitters psData = new PetSitters();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPetSitters() {
        try {
            PetSitters psData = new PetSitters();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPetSittersPool.json");
            writer.open();
            writer.write(psData);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPetSittersPool.json");
            psData = reader.read();
            assertEquals(0, psData.getPetSittersNum());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            PetSitters psList = new PetSitters();
            psList.addPetSitter(new PetSitter("js", "John", "Smith",
                    "Vancouver", 2, 30.5));
            psList.addPetSitter(new PetSitter("hp", "harry", "potter",
                    "London", 1, 16.0));
            psList.getPetSitters().get(1).setRating(9);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPetSittersPool.json");
            writer.open();
            writer.write(psList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPetSittersPool.json");
            psList = reader.read();
            List<PetSitter> petSitters = psList.getPetSitters();
            assertEquals(2, petSitters.size());
            checkPetSitter("js", "John", "SMITH",
                    "Vancouver", 2, 30.5, 0, petSitters.get(0));
            checkPetSitter("hp", "Harry", "POTTER",
                    "London", 1, 16.0, 9, petSitters.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
