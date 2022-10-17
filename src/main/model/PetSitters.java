package model;

import java.util.ArrayList;
import java.util.List;

// Represent a collection of all PetSitters
public class PetSitters {
    private final List<PetSitter> petSitters;

    // EFFECTS: Create a pool of pet sitters
    public PetSitters() {
        petSitters = new ArrayList<>();
    }

    public List<PetSitter> getPetSitters() {
        return this.petSitters;
    }

    // EFFECTS: return number of pet sitters available
    public int getPetSittersNum() {
        return petSitters.size();
    }

    // MODIFIES: this
    // EFFECTS: add a pet sitter object and return True if it does not exist yet; return False otherwise
    public boolean addPetSitter(PetSitter ps) {
        if (!petSitters.contains(ps)) {
            petSitters.add(ps);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a pet sitter object and return True if it exists, return False otherwise
    public boolean removePetSitter(PetSitter ps) {
        if (petSitters.contains(ps)) {
            petSitters.remove(ps);
            return true;
        } else {
            return false;
        }
    }

    // REQUIRES: user ID string
    // EFFECTS: return a specific pet sitter by user
    public PetSitter getPetSitter(String usrId) {
        for (PetSitter ps : petSitters) {
            if (ps.getUsrId().equals(usrId)) {
                return ps;
            }
        }
        return null;
    }

    // EFFECTS: show information of all the pet sitters in the list
    public String showAllPetSitters() {
        String petSitterPool = "Pet-sitter ID" + "\t" + "Full Name" + "\t" + "City " + "\t" + "Experience" + "\t"
                + "Hourly Rate" + "\t" + "Rating" + "\n";
        for (PetSitter ps : petSitters) {
            petSitterPool += ps.displayPetSitter();
        }
        return petSitterPool;
    }
}
