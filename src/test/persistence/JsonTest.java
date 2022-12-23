package persistence;

import model.PetSitter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPetSitter(String userId, String fn, String ln, String city, int exp, double hrRate,
                                  int rating, PetSitter ps) {
        assertEquals(userId, ps.getUserId());
        assertEquals(ln + ", " + fn, ps.getFullName());
        assertEquals(city, ps.getCity());
        assertEquals(exp, ps.getExperience());
        assertEquals(hrRate, ps.getHrRate());
        assertEquals(rating, ps.getRating());
    }
}
