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
                    "How deadly do you want " + diseaseName + "? (1 - 100 or type quit to stop): ");

            if (deadly == -1) break;

            // Get how contagious the disease is
            int contagious = getPercentage(input,
                    "How contagious do you want " + diseaseName + "? (1 - 100 or type quit to stop): ");

            if (contagious == -1) break;

            // Show stats of disease
            System.out.println("Disease created:");
            System.out.println("Deadliness: " + deadly + "%");
            System.out.println("Contagiousness: " + contagious + "%");

            // User set population size (Minimum : 1, Max : 1 thousand)
            int populationSize = getPopulationSize(input);
            if (populationSize == -1) break;
            System.out.println("Population size: " + populationSize);

            // User set amount of children, adolescents, adults, and elders
            int childrenAmount = -1;
            int adoleAmount = -1;
            int adultAmount = -1;
            int elderAmount = -1;

            // Keep repeating until user types in valid numbers that add up to 100
            while (true) {

                childrenAmount = getPercentage(input,
                        "What percentage of your population would you like to be children? ");
                if (childrenAmount == -1) break;

                adoleAmount = getPercentage(input,
                        "What percentage of your population would you like to be adolescents? ");
                if (adoleAmount == -1) break;

                adultAmount = getPercentage(input,
                        "What percentage of your population would you like to be adults? ");
                if (adultAmount == -1) break;

                elderAmount = 100 - (childrenAmount + adoleAmount + adultAmount);

                if (elderAmount < 0) {
                    System.out.println("Percentages exceed 100. Try again.\n");
                } else {
                    break;
                }
            }

            // Invalid numbers
            if (childrenAmount == -1 || adoleAmount == -1 || adultAmount == -1) {
                break;
            }

            System.out.println("Children percentage: " + childrenAmount + "%");
            System.out.println("Adolescents percentage: " + adoleAmount + "%");
            System.out.println("Adults percentage: " + adultAmount + "%");
            System.out.println("Elders percentage: " + elderAmount + "%");

        }

        /*
        System.out.println("Simulation ended.");
        input.close();
    */
    }


    // Helper method to handle percentage input
    public static int getPercentage(Scanner input, String message) {

        // Repeat message until user enters a valid number
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

    // Helper method of population size
    public static int getPopulationSize(Scanner input) {

        // Repeat until user enters a valid population number
        while (true) {
            System.out.print("How big do you want your population to be? (1 - 1000 or type quit to stop): ");
            String userInput = input.nextLine();

            if (userInput.equalsIgnoreCase("quit")) {
                return -1;
            }

            try {
                int size = Integer.parseInt(userInput);

                if (size >= 1 && size <= 1000) {
                    return size;
                } else {
                    System.out.println("Please enter a number between 1 and 100000.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

}
