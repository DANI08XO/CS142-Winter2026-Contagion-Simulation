
// Final Project for CS 142: Contagion Simulation
// Codevid-19 Group: Daniel, Kenzuki, Helen, Kushal

import javax.swing.SwingUtilities;

/**
 * CONTAGION SIMULATION MAIN CLASS
 * This is the ENTRY POINT of the program.
 * It launches the UserInputGUI window which handles all user interaction
 * for setting up disease parameters and demographics.
 *
 * <p>Once the user completes the setup, this class provides the
 * {@link #launchSimulation(Disease, PopulationSettings)} method to start
 * the visual simulation grid.</p>
 *
 * @author Codevid-19 Team
 * @version 1.0
 */
public class ContagionSimulationMain {

    /**
     * MAIN METHOD - Where the program starts.
     * Launches the UserInputGUI on the Event Dispatch Thread to ensure
     * thread safety with Swing components.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {

        // ============ INTRODUCTION ============
        System.out.println("==========================================");
        System.out.println("Welcome to Codevid-19 final project for CS 142");
        System.out.println("This program simulates disease spread.");
        System.out.println("==========================================\n");
        System.out.println("Opening setup window...");

        // Launch user input GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new UserInputGUI().setVisible(true);
        });
    }

    /**
     * LAUNCH SIMULATION - Starts the visual simulation GUI.
     * This method is called by UserInputGUI after all disease parameters
     * and demographics have been collected from the user.
     *
     * <p>The simulation GUI is created on the Event Dispatch Thread to
     * ensure proper Swing threading behavior.</p>
     *
     * @param disease The disease object containing name, deadliness, and contagiousness
     * @param settings The population settings with all demographic percentages
     */
    public static void launchSimulation(Disease disease, PopulationSettings settings) {
        // SwingUtilities.invokeLater ensures GUI is created on the right thread
        // This is standard practice for Swing applications
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create the GUI with the user's settings
                new ContagionGUI(disease, settings);
            }
        });
    }

}