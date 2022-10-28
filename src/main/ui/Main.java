package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new PetSitterApp();
        } catch (FileNotFoundException e) {
            System.out.println("Failed to run application: file not found");
        }
    }
}
