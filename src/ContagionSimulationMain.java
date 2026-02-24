// Final Project for CS 142: Contagion Simulation
// Codevid-19 Group: Daniel, Kenzuki, Helen, Kushal

import java.util.Scanner;

public class ContagionSimulationMain {

    public static void main(String[] args) {

        // Scanner for user input
        Scanner input = new Scanner(System.in);

        // Intro to our Project
        System.out.println("Welcome to Codevid-19 final project for CS 142");
        System.out.println("This program simulates disease spread.");

        // User wil be able to name their disease
        System.out.print("Name of your disease? ");
        String diseaseName = input.nextLine();

        // While loop for the user input and questions
        while (true) {

            // Get how deadly the disease is
            int deadly = getPercentage(input,
                    "How deadly do you want " + diseaseName + "? (1-100 or type quit to stop): ");

            if (deadly == -1) break;

            // Get how contagious the disease is
            int contagious = getPercentage(input,
                    "How contagious do you want " + diseaseName + "? (1-100 or type quit to stop): ");

            if (contagious == -1) break;

            // Show stats of disease
            System.out.println("Disease created:");
            System.out.println("Deadliness: " + deadly + "%");
            System.out.println("Contagiousness: " + contagious + "%");
        }

        /*
        System.out.println("Simulation ended.");
        input.close();
    */
    }


    // Helper method to handle percentage input
    public static int getPercentage(Scanner input, String message) {

        while (true) {
            System.out.print(message);
            String userInput = input.nextLine();

            if (userInput.equalsIgnoreCase("quit")) {
                return -1;
            }

            try {
                int percentage = Integer.parseInt(userInput);

                if (percentage >= 1 && percentage <= 100) {
                    return percentage;
                } else {
                    System.out.println("Please enter a number between 1 and 100.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
}
