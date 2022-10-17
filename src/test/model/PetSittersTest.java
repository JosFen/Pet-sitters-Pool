package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PetSittersTest {
    private PetSitters psList;
    private PetSitter ps1;
    private PetSitter ps2;

    @BeforeEach
    public void runBefore() {
        psList = new PetSitters();
        ps1 = new PetSitter("js", "John", "Smith",
                "Vancouver", 2, 30);
        ps2 = new PetSitter("hp", "harry", "potter",
                "London", 1, 16);
    }

    @Test
    public void testAddPetSitter() {
        assertTrue(psList.addPetSitter(ps1));
        assertTrue(psList.getPetSitters().contains(ps1));
        assertEquals(1, psList.getPetSittersNum());
        assertTrue(psList.addPetSitter(ps2));
        assertTrue(psList.getPetSitters().contains(ps2));
        assertEquals(2, psList.getPetSittersNum());
        assertFalse(psList.addPetSitter(ps1));
        assertFalse(psList.addPetSitter(ps2));
    }

    @Test
    public void testRemovePetSitter() {
        psList.addPetSitter(ps1);
        psList.addPetSitter(ps2);
        assertEquals(2, psList.getPetSittersNum());
        assertTrue(psList.removePetSitter(ps1));
        assertFalse(psList.removePetSitter(ps1));
        assertFalse(psList.getPetSitters().contains(ps1));
        assertEquals(1, psList.getPetSittersNum());
        assertTrue(psList.removePetSitter(ps2));
        assertFalse(psList.removePetSitter(ps2));
        assertFalse(psList.getPetSitters().contains(ps2));
        assertEquals(0, psList.getPetSittersNum());
    }

    @Test
    public void testGetPetSitter() {
        psList.addPetSitter(ps1);
        psList.addPetSitter(ps2);
        assertEquals(ps1, psList.getPetSitter("js1"));
        assertEquals(ps2, psList.getPetSitter("hp2"));
        assertNull(psList.getPetSitter("xy3"));
    }

    @Test
    public void testShowAllPetSitters(){
        String output = "Full Name" + "\t" + "City " + "\t" + "Experience" + "\t" + "Hourly Rate"
                + "\t" + "Rating" + "\n";
        assertEquals(output, psList.showAllPetSitters());
        psList.addPetSitter(ps1);
        output += "SMITH, John\tVancouver\t2\t30\t0\n";
        assertEquals(output, psList.showAllPetSitters());
        psList.addPetSitter(ps2);
        output += "POTTER, Harry\tLondon\t1\t16\t0\n";
        assertEquals(output, psList.showAllPetSitters());
    }
}
