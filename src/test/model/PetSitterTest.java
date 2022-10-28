package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PetSitterTest {
    private PetSitter testPS1;
    private PetSitter testPS2;

    @BeforeEach
    public void runBefore() {
        testPS1 = new PetSitter("josm", "John", "Smith",
                "Vancouver", 2, 30.5);
        testPS2 = new PetSitter("hapo", "harry", "potter",
                "London", 1, 16);
    }

    @Test
    public void testConstructor() {
        assertEquals("josm", testPS1.getUserId());
        assertEquals("SMITH, John", testPS1.getFullName());
        assertEquals("Vancouver", testPS1.getCity());
        assertEquals(2, testPS1.getExperience());
        assertEquals(30.5, testPS1.getHrRate());
        assertEquals(0, testPS1.getRating());

        assertEquals("hapo", testPS2.getUserId());
        assertEquals("POTTER, Harry", testPS2.getFullName());
        assertEquals("London", testPS2.getCity());
        assertEquals(1, testPS2.getExperience());
        assertEquals(16.0, testPS2.getHrRate());
        assertEquals(0, testPS2.getRating());
    }

    @Test
    public void testSetCity() {
        testPS1.setCity("Burnaby");
        assertEquals("Burnaby", testPS1.getCity());
        testPS2.setCity("Oxford");
        assertEquals("Oxford", testPS2.getCity());
    }

    @Test
    public void testSetExperience() {
        testPS1.setExperience(1);
        assertEquals(1, testPS1.getExperience());
        testPS1.setExperience(25);
        assertEquals(25, testPS1.getExperience());
    }

    @Test
    public void testSetHrRate() {
        testPS1.setHrRate(0);
        assertEquals(0.0, testPS1.getHrRate());
        testPS1.setHrRate(60);
        assertEquals(60.0, testPS1.getHrRate());
    }

    @Test
    public void testSetRating() {
        testPS1.setRating(5);
        assertEquals(5, testPS1.getRating());
        testPS1.setRating(-1);
        assertEquals(0, testPS1.getRating());
        testPS1.setRating(22);
        assertEquals(10, testPS1.getRating());
    }

    @Test
    public void testDisplayPetSitter() {
        assertEquals("josm\t" + "SMITH, John\tVancouver\t2\t30.5\t0\n",
                testPS1.displayPetSitter());
        assertEquals("hapo\t" + "POTTER, Harry\tLondon\t1\t16.0\t0\n",
                testPS2.displayPetSitter());
    }
}