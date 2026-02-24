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

            // User set amount of babies, children, adolescents, adults, and elders. User also set how many males/females for each one
            int babyAmount = -1;
            int childrenAmount = -1;
            int adoleAmount = -1;
            int adultAmount = -1;
            int elderAmount = -1;

            int babyMales = -1;
            int childrenMales = -1;
            int adoleMales = -1;
            int adultMales = -1;
            int elderMales = -1;

            // Keep repeating until user types in valid numbers that add up to 100
            while (true) {

                babyAmount = getPercentage(input, "What percentage of your population would you like to be babies? ");
                if (babyAmount == -1) break;
                babyMales = getPercentage(input, "What percentage of babies would you like to be males? ");

                childrenAmount = getPercentage(input,
                        "What percentage of your population would you like to be children? ");
                if (childrenAmount == -1) break;
                childrenMales = getPercentage(input, "What percentage of children would you like to be males? ");

                adoleAmount = getPercentage(input,
                        "What percentage of your population would you like to be adolescents? ");
                if (adoleAmount == -1) break;
                adoleMales = getPercentage(input, "What percentage of adolescents would you like to be males? ");

                adultAmount = getPercentage(input,
                        "What percentage of your population would you like to be adults? ");
                if (adultAmount == -1) break;
                adultMales = getPercentage(input, "What percentage of adults would you like to be males? ");

                elderAmount = 100 - (childrenAmount + adoleAmount + adultAmount);

                if (elderAmount < 0) {
                    System.out.println("Percentages exceed 100. Try again.\n");
                } else {
                    elderMales = getPercentage(input, "What percentage of elder would you like to be males? (Note: elder percentage has already been calculated: " + elderAmount + ")" );
                    break;
                }
            }

            // Invalid numbers
            if (babyAmount == -1 ||childrenAmount == -1 || adoleAmount == -1 || adultAmount == -1) {
                break;
            }

            System.out.println("Babies percentage: " + babyAmount + "%");
            System.out.println("Babies male percentage: " + babyMales + "% female percentage: " + (100-babyMales) + "%");
            System.out.println("Children percentage: " + childrenAmount + "%");
            System.out.println("Children male percentage: " + childrenMales + "% female percentage: " + (100-childrenMales) + "%");
            System.out.println("Adolescents percentage: " + adoleAmount + "%");
            System.out.println("Adolescents male percentage: " + adoleMales + "% female percentage: " + (100-adoleMales) + "%");
            System.out.println("Adults percentage: " + adultAmount + "%");
            System.out.println("Adults male percentage: " + adultMales + "% female percentage: " + (100-adultMales) + "%");
            System.out.println("Elders percentage: " + elderAmount + "%");
            System.out.println("Elders male percentage: " + elderMales + "% female percentage: " + (100-elderMales) + "%");

            // User choose the percentage of peoople in simulation vaccinated
            int vaccinated = getPercentage(input, "What percentage of the population is vaccinated? ");

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
