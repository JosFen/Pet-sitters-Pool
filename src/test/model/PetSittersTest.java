package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class PetSittersTest {
    private PetSitters psList;
    private PetSitter ps1;
    private PetSitter ps2;

    @BeforeEach
    public void runBefore() {
        psList = new PetSitters();
        ps1 = new PetSitter("jodo", "John", "doe",
                "Vancouver", 2, 30.5);
        ps2 = new PetSitter("hapo", "harry", "potter",
                "London", 1, 46.0);
    }

    @Test
    public void testAddPetSitter() {
        assertTrue(psList.addPetSitter(ps1));
        assertTrue(psList.getPetSitters().contains(ps1));
        assertEquals(1, psList.getPetSittersNum());

        assertTrue(psList.addPetSitter(ps2));
        assertTrue(psList.getPetSitters().contains(ps2));
        assertEquals(2, psList.getPetSittersNum());

        Iterator<Event> itr = EventLog.getInstance().iterator();
        assertEquals("A pet-sitter (UserID: jodo) is added to the pool.", itr.next().getDescription());
        assertEquals("A pet-sitter (UserID: hapo) is added to the pool.", itr.next().getDescription());

        assertFalse(psList.addPetSitter(ps1));
        assertFalse(psList.addPetSitter(ps2));
        assertFalse(psList.getPetSitters().contains("jodo"));
    }

    @Test
    public void testRemovePetSitter() {
        psList.addPetSitter(ps1);
        psList.addPetSitter(ps2);
        assertEquals(2, psList.getPetSittersNum());

        EventLog.getInstance().clear();
        assertTrue(psList.removePetSitter(ps1));
        assertFalse(psList.removePetSitter(ps1));
        assertFalse(psList.getPetSitters().contains(ps1));
        assertEquals(1, psList.getPetSittersNum());
        assertTrue(psList.removePetSitter(ps2));
        assertFalse(psList.removePetSitter(ps2));
        assertFalse(psList.getPetSitters().contains(ps2));
        assertEquals(0, psList.getPetSittersNum());

        Iterator<Event> itr = EventLog.getInstance().iterator();
        itr.next();
        assertEquals("A pet-sitter (UserID: jodo) is removed from the pool.", itr.next().getDescription());
        assertEquals("A pet-sitter (UserID: hapo) is removed from the pool.", itr.next().getDescription());
    }

    @Test
    public void testGetPetSitter() {
        psList.addPetSitter(ps1);
        psList.addPetSitter(ps2);
        assertEquals(ps1, psList.getPetSitter(ps1.getUserId()));
        assertEquals(ps2, psList.getPetSitter(ps2.getUserId()));
        assertNull(psList.getPetSitter("xy123"));
    }

    @Test
    public void testShowAllPetSitters() {
        String output = "Pet-sitter ID" + "\t" + "Full Name" + "\t" + "City " + "\t" + "Experience" + "\t"
                + "Hourly Rate" + "\t" + "Rating" + "\n";
        assertEquals(output, psList.showAllPetSitters());
        psList.addPetSitter(ps1);
        output += "jodo\tDOE, John\tVancouver\t2\t30.5\t0\n";
        assertEquals(output, psList.showAllPetSitters());
        psList.addPetSitter(ps2);
        output += "hapo\tPOTTER, Harry\tLondon\t1\t46.0\t0\n";
        assertEquals(output, psList.showAllPetSitters());
    }

    @Test
    public void testToJson() {
        JSONObject jsonObj = psList.toJson();
        assertEquals(0, jsonObj.getJSONArray("petSitters").length());
        psList.addPetSitter(ps1);
        jsonObj = psList.toJson();
        assertEquals(1, jsonObj.getJSONArray("petSitters").length());
        psList.addPetSitter(ps2);
        jsonObj = psList.toJson();
        assertEquals(2, jsonObj.getJSONArray("petSitters").length());

        JSONObject jsonPS1 = jsonObj.getJSONArray("petSitters").getJSONObject(0);
        assertEquals(ps1.getUserId(), jsonPS1.getString("userId"));
        assertEquals(ps1.getFullName(),
                jsonPS1.getString("lastName") + ", " + jsonPS1.getString("firstName"));
        assertEquals(ps1.getCity(), jsonPS1.getString("city"));
        assertEquals(ps1.getExperience(), jsonPS1.getInt("experience"));
        assertEquals(ps1.getHrRate(), jsonPS1.getDouble("hrRate"));
        assertEquals(ps1.getRating(), jsonPS1.getInt("rating"));
    }

    @Test
    public void testSortPetSitters() {
        psList.addPetSitter(ps1);
        psList.addPetSitter(ps2);
        EventLog.getInstance().clear();

        psList.sortPetSitters("User Id");
        assertEquals(ps2, psList.getPetSitters().get(0));
        assertEquals(ps1, psList.getPetSitters().get(1));

        psList.sortPetSitters("Full Name");
        assertEquals(ps1, psList.getPetSitters().get(0));
        assertEquals(ps2, psList.getPetSitters().get(1));

        psList.sortPetSitters("City");
        assertEquals(ps2, psList.getPetSitters().get(0));
        assertEquals(ps1, psList.getPetSitters().get(1));

        psList.sortPetSitters("Hourly rate");
        assertEquals(ps1, psList.getPetSitters().get(0));
        assertEquals(ps2, psList.getPetSitters().get(1));

        psList.sortPetSitters("Experience");
        assertEquals(ps2, psList.getPetSitters().get(0));
        assertEquals(ps1, psList.getPetSitters().get(1));

        ps2.setRating(1);

        psList.sortPetSitters("Rating");
        assertEquals(ps1, psList.getPetSitters().get(0));
        assertEquals(ps2, psList.getPetSitters().get(1));

        Iterator<Event> itr = EventLog.getInstance().iterator();
        itr.next();
        assertEquals("Pet-sitters pool is sorted by \"User Id\"", itr.next().getDescription());
        assertEquals("Pet-sitters pool is sorted by \"Full Name\"", itr.next().getDescription());
        assertEquals("Pet-sitters pool is sorted by \"City\"", itr.next().getDescription());
        assertEquals("Pet-sitters pool is sorted by \"Hourly rate\"", itr.next().getDescription());
        assertEquals("Pet-sitters pool is sorted by \"Experience\"", itr.next().getDescription());
        assertEquals("Pet-sitters pool is sorted by \"Rating\"", itr.next().getDescription());
    }
}
