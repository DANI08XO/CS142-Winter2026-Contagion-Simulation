
// Final Project for CS 142: Contagion Simulation
// Codevid-19 Group: Daniel, Kenzuki, Helen, Kushal

import java.util.Scanner;
import javax.swing.SwingUtilities;

/**
 * CONTAGION SIMULATION MAIN CLASS
 * This is the ENTRY POINT of the program
 * It handles:
 * - User input via text interface
 * - Creating disease and population settings
 * - Launching the visual GUI
 *
 * This is a TEXT-BASED setup wizard that then opens a GRAPHICAL simulation
 */

public class ContagionSimulationMain {

    /**
     * MAIN METHOD - Where the program starts
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {

        // ============ INTRODUCTION ============
        System.out.println("Welcome to Codevid-19 final project for CS 142");
        System.out.println("This program simulates disease spread.");

        // launch user input GUI
        SwingUtilities.invokeLater(() -> {
            new UserInputGUI().setVisible(true);
        });
    }

    /**
     * LAUNCH SIMULATION - Starts the GUI in a separate thread
     * @param disease The disease object with all settings
     * @param settings The population settings with demographics
     */


    public static void launchSimulation(Disease disease, PopulationSettings settings) {
        // SwingUtilities.invokeLater ensures GUI is created on the right thread
        // This is standard practice for Swing applications
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create the GUI with the user's settings
                new ContagionGUI(disease, settings);
            }
        });
    }

    /**
     * GET PERCENTAGE - Helper method to get percentage input from user
     * Keeps asking until user enters a valid number between 1-100
     * @param input The Scanner object
     * @param message The prompt to show the user
     * @return The percentage entered, or -1 if user types "quit"
     */
    /*
    public static int getPercentage(Scanner input, String message) {

        // Loop until user enters valid input
        while (true) {
            System.out.print(message);
            String userInput = input.nextLine();

            // Check if user wants to quit
            if (userInput.equalsIgnoreCase("quit")) {
                return -1; // Signal to quit
            }

            try {
                // Try to convert to integer
                int percentage = Integer.parseInt(userInput);

                // Check if within valid range
                if (percentage >= 1 && percentage <= 100) {
                    return percentage; // Valid input!
                } else {
                    System.out.println("Please enter a number between 1 and 100.");
                }

            } catch (NumberFormatException e) {
                // User didn't type a number
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * GET POPULATION SIZE - Helper method for population input
     * @param input The Scanner object
     * @return Population size (1-1000), or -1 if user types "quit"
     */
    /*
    public static int getPopulationSize(Scanner input) {

        // Loop until user enters valid input
        while (true) {
            System.out.print("How big do you want your population to be? (1 - 1000 or type quit to stop): ");
            String userInput = input.nextLine();

            // Check if user wants to quit
            if (userInput.equalsIgnoreCase("quit")) {
                return -1;
            }

            try {
                int size = Integer.parseInt(userInput);

                // Check if within valid range
                if (size >= 1 && size <= 1000) {
                    return size; // Valid input!
                } else {
                    System.out.println("Please enter a number between 1 and 1000.");
                }

            } catch (NumberFormatException e) {
                // User didn't type a number
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
    */
}
