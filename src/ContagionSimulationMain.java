
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

        // Scanner for reading user input from keyboard
        Scanner input = new Scanner(System.in);

        // ============ INTRODUCTION ============
        System.out.println("Welcome to Codevid-19 final project for CS 142");
        System.out.println("This program simulates disease spread.");

        // ============ DISEASE NAME ============
        // User gets to name their disease (fun part!)
        System.out.print("Name of your disease? ");
        String diseaseName = input.nextLine();

        // ============ MAIN LOOP ============
        // Allows user to run multiple simulations without restarting
        while (true) {

            // ============ DISEASE PROPERTIES ============
            // Get how deadly the disease is (1-100%)
            int deadly = getPercentage(input,
                    "How deadly do you want " + diseaseName + "? (1 - 100 or type quit to stop): ");

            if (deadly == -1) break; // User typed "quit"

            // Get how contagious the disease is (1-100%)
            int contagious = getPercentage(input,
                    "How contagious do you want " + diseaseName + "? (1 - 100 or type quit to stop): ");

            if (contagious == -1) break; // User typed "quit"

            // Show disease stats to user
            System.out.println("\nDisease created:");
            System.out.println("Name: " + diseaseName);
            System.out.println("Deadliness: " + deadly + "%");
            System.out.println("Contagiousness: " + contagious + "%");

            // Create a Disease object to store these values
            Disease disease = new Disease(diseaseName, deadly, contagious);

            // ============ POPULATION SIZE ============
            int populationSize = getPopulationSize(input);
            if (populationSize == -1) break; // User typed "quit"
            System.out.println("Population size: " + populationSize);

            // Create a PopulationSettings object to store demographics
            PopulationSettings settings = new PopulationSettings(populationSize);

            // ============ DEMOGRAPHICS ============
            System.out.println("\nNow let's set up the population demographics:");
            System.out.println("(Percentages should add up to 100%)");

            // Get baby percentage
            settings.setBabyPercent(getPercentage(input, "What percentage of your population would you like to be babies? "));
            if (settings.getBabyPercent() == -1) break;
            settings.setBabyMalePercent(getPercentage(input, "What percentage of babies would you like to be males? "));

            // Get child percentage
            settings.setChildPercent(getPercentage(input,
                    "What percentage of your population would you like to be children? "));
            if (settings.getChildPercent() == -1) break;
            settings.setChildMalePercent(getPercentage(input, "What percentage of children would you like to be males? "));

            // Get teen percentage
            settings.setTeenPercent(getPercentage(input,
                    "What percentage of your population would you like to be adolescents? "));
            if (settings.getTeenPercent() == -1) break;
            settings.setTeenMalePercent(getPercentage(input, "What percentage of adolescents would you like to be males? "));

            // Get adult percentage
            settings.setAdultPercent(getPercentage(input,
                    "What percentage of your population would you like to be adults? "));
            if (settings.getAdultPercent() == -1) break;
            settings.setAdultMalePercent(getPercentage(input, "What percentage of adults would you like to be males? "));

            // Calculate elder percentage (whatever is left to make 100%)
            settings.setElderPercent(100 - (settings.getBabyPercent() + settings.getChildPercent() +
                    settings.getTeenPercent() + settings.getAdultPercent()));

            // Check if percentages exceed 100% (error)
            if (settings.getElderPercent() < 0) {
                System.out.println("ERROR: Percentages exceed 100%. Please run again.");
                break;
            } else {
                // Get elder male percentage
                settings.setElderMalePercent(getPercentage(input,
                        "What percentage of elders would you like to be males? (Elder percentage: " +
                                settings.getElderPercent() + "%)" ));
                if (settings.getElderMalePercent() == -1) break;
            }

            // ============ SUMMARY ============
            System.out.println("\n=== POPULATION SUMMARY ===");
            System.out.println("Babies: " + settings.getBabyPercent() + "% (Males: " +
                    settings.getBabyMalePercent() + "%, Females: " + (100-settings.getBabyMalePercent()) + "%)");
            System.out.println("Children: " + settings.getChildPercent() + "% (Males: " +
                    settings.getChildMalePercent() + "%, Females: " + (100-settings.getChildMalePercent()) + "%)");
            System.out.println("Adolescents: " + settings.getTeenPercent() + "% (Males: " +
                    settings.getTeenMalePercent() + "%, Females: " + (100-settings.getTeenMalePercent()) + "%)");
            System.out.println("Adults: " + settings.getAdultPercent() + "% (Males: " +
                    settings.getAdultMalePercent() + "%, Females: " + (100-settings.getAdultMalePercent()) + "%)");
            System.out.println("Elders: " + settings.getElderPercent() + "% (Males: " +
                    settings.getElderMalePercent() + "%, Females: " + (100-settings.getElderMalePercent()) + "%)");

            // ============ VACCINATION ============
            settings.vaccinatedPercent = getPercentage(input,
                    "What percentage of the population is vaccinated? ");

            if (settings.vaccinatedPercent == -1) break;

            // ============ LAUNCH SIMULATION ============
            System.out.println("\n=== LAUNCHING SIMULATION ===");
            System.out.println("Starting visual simulation with your settings...");
            System.out.println("The grid window will appear now!");

            // Launch the GUI with user's settings
            // This runs on a separate thread so the text interface doesn't freeze
            launchSimulation(disease, settings);

            // ============ RUN AGAIN? ============
            System.out.print("\nRun another simulation? (yes/no): ");
            String again = input.nextLine();
            if (!again.equalsIgnoreCase("yes")) {
                break; // Exit the while loop
            }
        }

        // ============ GOODBYE ============
        System.out.println("Thanks for using Codevid-19 simulation!");
        input.close(); // Close the scanner (good practice)
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
}