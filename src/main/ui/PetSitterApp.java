package ui;

import model.PetSitter;
import model.PetSitters;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Pet-Sitter Application
public class PetSitterApp {
    private static final String JSON_LOCATION = "./data/petSittersPool.json";
    private PetSitters psPool;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the PetSitter application
    public PetSitterApp() throws FileNotFoundException {
        runPetSitterApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    // inspired by CPSC210 TellerApp class: TellerApp, method: runTeller()
    private void runPetSitterApp() {
        boolean letRunning = true;
        String command;

        startApp();

        while (letRunning) {
            displayMenu();
            command = input.next();
            if (command.equals("6")) {
                System.out.println("Do you want to save updated list of pet-sitters? Enter y or n: ");
                boolean toSave = input.next().equals("y");
                if (toSave) {
                    saveDataToFile();
                }
                letRunning = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nThe application is closed.");
    }

    // MODIFIES: this
    // EFFECTS: initializes and starts the application
    public void startApp() {
        psPool = new PetSitters();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_LOCATION);
        jsonReader = new JsonReader(JSON_LOCATION);

        System.out.println("Do you want to load existing pet-sitters from database? Enter y or n: ");
        boolean toLoad = input.next().equals("y");
        if (toLoad) {
            loadDatabase();
        }
    }

    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: psPool
    // EFFECTS: load existing pet-sitters from file at the start of the application
    public void loadDatabase() {
        try {
            psPool = jsonReader.read();
            System.out.println("Loaded existing pet-sitters from " + JSON_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_LOCATION);
        }
    }

    // CITATION: modeled from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
    // MODIFIES: this
    // EFFECTS: save updated list of pet-sitters to file before exit the application
    public void saveDataToFile() {
        try {
            jsonWriter.open();
            jsonWriter.write(psPool);
            jsonWriter.close();
            System.out.println("Pet-sitters pool updated and saved to " + JSON_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_LOCATION);
        }
    }

    // EFFECTS: displays menu of options to user
    public void displayMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("\t1 -> View the list of all pet-sitters");
        System.out.println("\t2 -> Add a pet-sitter");
        System.out.println("\t3 -> Remove a pet-sitter");
        System.out.println("\t4 -> View information of a pet-sitter");
        System.out.println("\t5 -> Update information of a pet-sitter");
        System.out.println("\t6 -> Exit application");
    }

    // MODIFIES: this
    // EFFECTS: processes user's chosen option
    public void processCommand(String command) {
        if (command.equals("1")) {
            displayAllPetSitters();
        } else if (command.equals("2")) {
            addAPetSitter();
        } else if (command.equals("3")) {
            removeAPetSitter();
        } else if (command.equals("4")) {
            displayPetSitter();
        } else if (command.equals("5")) {
            updatePetSitter();
        } else {
            System.out.println("Invalid selection! Please try again.");
        }
    }

    // EFFECTS: displays all pet-sitters currently in the pool
    public void displayAllPetSitters() {
        System.out.println("List of all pet-sitters: \n" + psPool.showAllPetSitters());
    }

    // MODIFIES: this
    // EFFECTS: creates a pet-sitter from user input and adds it to the pool
    public void addAPetSitter() {
        String psUserName;
        String firstName;
        String lastName;
        String city;
        int experience;
        double hrRate;
        PetSitter userPS;

        System.out.println("Please enter the following information for the pet-sitter:");
        System.out.println("Username for pet-sitter (will be used to create userID): ");
        psUserName = input.next();
        System.out.println("First name of the pet-sitter");
        firstName = input.next();
        System.out.println("Last name of the pet-sitter");
        lastName = input.next();
        System.out.println("Location of the pet-sitter");
        city = input.next();
        System.out.println("Years of Experience of the pet-sitter");
        experience = input.nextInt();
        System.out.println("Hourly rate of the pet-sitter");
        hrRate = input.nextDouble();

        userPS = new PetSitter(psUserName, firstName, lastName, city, experience, hrRate);
        psPool.addPetSitter(userPS);
    }

    // MODIFIES: this
    // EFFECTS: removes a pet-sitter from the pool
    public void removeAPetSitter() {
        String psUserId;
        boolean isRemoved;
        System.out.println("Please enter the userID for the pet-sitter (you may display the list to find):");
        psUserId = input.next();
        isRemoved = psPool.removePetSitter(psPool.getPetSitter(psUserId));
        if (isRemoved) {
            System.out.println("Removed successfully!");
        } else {
            System.out.println("This pet-sitter does not exist!");
        }
    }

    // EFFECTS: display detailed information of a pet-sitter inquired by the user
    public void displayPetSitter() {
        String psUserId;
        PetSitter userPS;
        System.out.println("Please enter the pet-sitter ID you want to view:");
        psUserId = input.next();
        userPS = psPool.getPetSitter(psUserId);
        if (userPS == null) {
            System.out.println("Invalid pet-sitter ID, or this pet-sitter does not exist.");
        } else {
            System.out.println("The information of the pet-sitter shows below: ");
            System.out.println(psPool.getPetSitter(psUserId).displayPetSitter());
        }
    }

    // MODIFIES: this
    // EFFECT: display available options for update and update information of a chosen pet-sitter
    public void updatePetSitter() {
        boolean keepUpdating = true;
        String psUserId;
        PetSitter userPS;
        String userChoice;

        System.out.println("Please enter the pet-sitter ID you want to update:");
        psUserId = input.next();
        userPS = psPool.getPetSitter(psUserId);
        if (userPS == null) {
            System.out.println("Invalid pet-sitter ID, or this pet-sitter does not exist.");
        } else {
            while (keepUpdating) {
                displayUpdateMenu();
                userChoice = input.next();
                if (userChoice.equals("5")) {
                    keepUpdating = false;
                } else {
                    processUpdateCommand(userChoice, userPS);
                }
            }
        }
    }

    // EFFECT: displays update menu of pet-sitter information
    public void displayUpdateMenu() {
        System.out.println("\nSelect information of the pet-sitter to update:");
        System.out.println("\t1 -> City location");
        System.out.println("\t2 -> Years of experience");
        System.out.println("\t3 -> Hourly rate");
        System.out.println("\t4 -> Rate a pet-sitter");
        System.out.println("\t5 -> Finish update");
    }

    // EFFECTS: processes user command for updating a pet-sitter
    public void processUpdateCommand(String userChoice, PetSitter userPS) {
        switch (userChoice) {
            case "1":
                updateCity(userPS);
                break;
            case "2":
                updateExperience(userPS);
                break;
            case "3":
                updateHrRate(userPS);
                break;
            case "4":
                updateRating(userPS);
                break;
            default:
                System.out.println("Invalid input!");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: updates location city of a pet-sitter
    public void updateCity(PetSitter userPS) {
        String city;
        System.out.println("Please enter the new city of the pet-sitter: ");
        city = input.next();
        userPS.setCity(city);
    }

    // MODIFIES: this
    // EFFECTS: updates years of experience of a pet-sitter
    public void updateExperience(PetSitter userPS) {
        int experience;
        System.out.println("Please enter years of experience of the pet-sitter: ");
        experience = input.nextInt();
        userPS.setExperience(experience);
    }

    // MODIFIES: this
    // EFFECTS: updates hourly rate of a pet-sitter
    public void updateHrRate(PetSitter userPS) {
        double hrRate;
        System.out.println("Please enter the new hourly rate of the pet-sitter: ");
        hrRate = input.nextDouble();
        userPS.setHrRate(hrRate);
    }

    // MODIFIES: this
    // EFFECTS: updates rating of a pet-sitter
    public void updateRating(PetSitter userPS) {
        int rating;
        System.out.println("Please enter the new hourly rate of the pet-sitter: ");
        rating = input.nextInt();
        userPS.setRating(rating);
    }

}
