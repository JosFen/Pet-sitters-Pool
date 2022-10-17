package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PetSitterTest {
    private PetSitter testPetSitter1;
    private PetSitter testPetSitter2;

    @BeforeEach
    public void runBefore() {
        testPetSitter1 = new PetSitter("js", "John", "Smith",
                "Vancouver", 2, 30.5);
        testPetSitter2 = new PetSitter("hp", "harry", "potter",
                "London", 1, 16);
    }

    @Test
    public void testConstructor() {
        assertEquals("js1", testPetSitter1.getUsrId());
        assertEquals("SMITH, John", testPetSitter1.getFullName());
        assertEquals("Vancouver", testPetSitter1.getCity());
        assertEquals(2, testPetSitter1.getExperience());
        assertEquals(30.5, testPetSitter1.getHrRate());
        assertEquals(0, testPetSitter1.getRating());

        assertEquals("hp2", testPetSitter2.getUsrId());
        assertEquals("POTTER, Harry", testPetSitter2.getFullName());
        assertEquals("London", testPetSitter2.getCity());
        assertEquals(1, testPetSitter2.getExperience());
        assertEquals(16.0, testPetSitter2.getHrRate());
        assertEquals(0, testPetSitter2.getRating());
    }

    @Test
    public void testSetCity(){
        testPetSitter1.setCity("Burnaby");
        assertEquals("Burnaby", testPetSitter1.getCity());
        testPetSitter2.setCity("Oxford");
        assertEquals("Oxford", testPetSitter2.getCity());
    }

    @Test
    public void testSetExperience(){
        testPetSitter1.setExperience(1);
        assertEquals(1, testPetSitter1.getExperience());
        testPetSitter1.setExperience(25);
        assertEquals(25, testPetSitter1.getExperience());
    }

    @Test
    public void testSetHrRate(){
        testPetSitter1.setHrRate(0);
        assertEquals(0.0, testPetSitter1.getHrRate());
        testPetSitter1.setHrRate(60);
        assertEquals(60.0, testPetSitter1.getHrRate());
    }

    @Test
    public void testSetRating(){
        testPetSitter1.setRating(5);
        assertEquals(5, testPetSitter1.getRating());
        testPetSitter1.setRating(-1);
        assertEquals(0, testPetSitter1.getRating());
        testPetSitter1.setRating(22);
        assertEquals(10, testPetSitter1.getRating());
    }

    @Test
    public void testDisplayPetSitter(){
        assertEquals("js1" + "\t" + "SMITH, John\tVancouver\t2\t30.5\t0\n", testPetSitter1.displayPetSitter());
        assertEquals("hp2" + "\t" + "POTTER, Harry\tLondon\t1\t16.0\t0\n", testPetSitter2.displayPetSitter());
    }
}