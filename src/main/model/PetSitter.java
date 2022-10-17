package model;

// Represent a pet sitter with username (unchangeable), firstname, last name, experience, city, hourly rate, rating
// Note: name should be unchangeable, if a pet sitter changed name later, a new instance should be created
public class PetSitter {
    private static int idStep = 1;
    private final String usrId;            // unchangeable username
    private final String firstName;        // first name of pet sitter
    private final String lastName;         // last name of pet sitter
    private String city;                   // city location of pet sitter
    private int experience;                // years of experience
    private int hrRate;                    // hourly rate requested by pet sitter, o for volunteer pet sitters
    private int rating = 0;                // rating of pet sitter, default (no rating yet) is 0

    /*
     * REQUIRES: All the String fields should have length greater than 0, all the int fields should be
     *           no less than 0.
     * EFFECTS:  Create a new pet sitter with required information, with the names formatted
     */
    public PetSitter(String usrName, String firstName, String lastName, String city, int experience, int hrRate) {
        this.usrId = usrName.toLowerCase() + (idStep++);
        this.firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        this.lastName = lastName.toUpperCase();
        this.city = city;
        this.experience = experience;
        this.hrRate = hrRate;
    }

    public String getUsrId() {
        return this.usrId;
    }

    // EFFECTS: return full name of pet sitter in the format of LASTNAME, Firstname
    public String getFullName() {
        return this.lastName + ", " + this.firstName;
    }

    public String getCity() {
        return this.city;
    }

    // REQUIRES: length of city should be greater than 0
    // MODIFIES: this
    // EFFECTS:  Update pet sitter's city
    public void setCity(String city) {
        this.city = city;
    }

    public int getExperience() {
        return this.experience;
    }

    // REQUIRES: experience should be no less than 0
    // MODIFIES: this
    // EFFECTS:  Update pet sitter's experience
    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getHrRate() {
        return this.hrRate;
    }

    // REQUIRES: hourly rate should be no less than 0
    // MODIFIES: this
    // EFFECTS:  Update pet sitter's hourly rate
    public void setHrRate(int hrRate) {
        this.hrRate = hrRate;
    }

    public int getRating() {
        return this.rating;
    }

    // REQUIRES: rating is within the range of 1 to 10, inclusive.
    // MODIFIES: this
    // EFFECTS:  Update pet sitter's rating
    public void setRating(int rating) {
        if (rating <= 0) {
            this.rating = 0;
        } else if (rating >= 10) {
            this.rating = 10;
        } else {
            this.rating = rating;
        }
    }

    // EFFECTS: show information of a specific pet sitter.
    public String displayPetSitter() {
        return this.getFullName() + "\t" + this.getCity() + "\t" + this.getExperience() + "\t"
                + this.getHrRate() + "\t" + this.getRating() + "\n";
    }
}
