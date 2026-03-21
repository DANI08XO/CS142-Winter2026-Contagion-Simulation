

// GUI for user input in introduction

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * UserInputGUI class - The initial setup window for the contagion simulation.
 * This class provides a simple interface for users to enter disease parameters
 * before launching the main simulation. It collects disease name, deadliness,
 * contagiousness, and population size, then guides users through demographic setup.
 *
 * <p>Features include:
 * <ul>
 *   <li>Text fields for basic disease parameters</li>
 *   <li>Save/Load functionality to reuse simulation setups</li>
 *   <li>Pop-up dialogs for age group and gender percentages</li>
 *   <li>Summary display before launching the simulation</li>
 * </ul>
 *
 * @author Codevid-19 Team
 * @version 1.0
 */
public class UserInputGUI extends JFrame {

    /** Text field for entering the disease name */
    private JTextField diseaseNameField;

    /** Text field for entering deadliness percentage (1-100) */
    private JTextField deadlyField;

    /** Text field for entering contagiousness percentage (1-100) */
    private JTextField contagiousField;

    /** Text field for entering population size (1-1000) */
    private JTextField populationField;

    /** Button to start the demographic setup process */
    private JButton startButton;

    /** Text area for output (currently unused but kept for potential future use) */
    private JTextArea outputArea;

    /** Button to save current settings to a file */
    private JButton saveButton;

    /** Button to load settings from a file */
    private JButton loadButton;

    /** PopulationSettings object to store demographic data */
    private PopulationSettings settings;

    /**
     * Constructor - Creates the initial input window.
     * Sets up all text fields and buttons for disease parameter entry.
     * Initializes the settings object with default values.
     */
    public UserInputGUI() {

        super("User Input for Contagion Simulation"); // Title of GUI

        setSize(400, 500); // Increased size to accommodate new buttons
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================== DISEASE INPUT ======================
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(Box.createVerticalGlue());

        // ===== Disease Name =====
        JLabel nameLabel = new JLabel("Name of your disease:");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(nameLabel);

        diseaseNameField = new JTextField();
        diseaseNameField.setMaximumSize(new Dimension(200, 25));
        diseaseNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(diseaseNameField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ===== Deadliness =====
        JLabel deadlyLabel = new JLabel("Deadliness (1-100%):");
        deadlyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        deadlyLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(deadlyLabel);

        deadlyField = new JTextField();
        deadlyField.setMaximumSize(new Dimension(100, 25));
        deadlyField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(deadlyField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ===== Contagiousness =====
        JLabel contagiousLabel = new JLabel("Contagiousness (1-100%):");
        contagiousLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contagiousLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(contagiousLabel);

        contagiousField = new JTextField();
        contagiousField.setMaximumSize(new Dimension(100, 25));
        contagiousField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(contagiousField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ===== Population =====
        JLabel popLabel = new JLabel("Population Size (1-1000):");
        popLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        popLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(popLabel);

        populationField = new JTextField();
        populationField.setMaximumSize(new Dimension(120, 25));
        populationField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(populationField);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setMaximumSize(new Dimension(400, 40));

        // Start Button
        startButton = new JButton("Choose Population Demographics");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        startButton.setBackground(new Color(50, 140, 50));
        startButton.setForeground(Color.BLACK);
        startButton.setFocusPainted(false);
        buttonPanel.add(startButton);

        panel.add(buttonPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ===== Save/Load Button Panel =====
        JPanel saveLoadPanel = new JPanel();
        saveLoadPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        saveLoadPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveLoadPanel.setMaximumSize(new Dimension(400, 40));

        // Save Button
        saveButton = new JButton(" Save Parameters");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        saveButton.setBackground(new Color(100, 100, 150));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);
        saveButton.setToolTipText("Save current parameters to a file for later use");
        saveLoadPanel.add(saveButton);

        // Load Button
        loadButton = new JButton("Load Parameters");
        loadButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        loadButton.setBackground(new Color(100, 100, 150));
        loadButton.setForeground(Color.BLACK);
        loadButton.setFocusPainted(false);
        loadButton.setToolTipText("Load previously saved parameters from a file");
        saveLoadPanel.add(loadButton);

        panel.add(saveLoadPanel);

        panel.add(Box.createVerticalGlue());

        // Add panel to center of frame
        add(panel, BorderLayout.CENTER);

        // Initialize settings with default values
        settings = new PopulationSettings(100);

        // Add action listeners
        startButton.addActionListener(e -> runSetup());
        saveButton.addActionListener(e -> saveParameters());
        loadButton.addActionListener(e -> loadParameters());
    }

    /**
     * Saves the current disease parameters to a text file.
     * Collects values from text fields and uses DataSaver to write to file.
     * Only saves the parameters (settings), not the simulation state.
     *
     * The saved file format is key=value pairs, making it human-readable.
     *
     * @throws NumberFormatException if text fields contain invalid numbers
     */
    private void saveParameters() {
        try {
            // Get values from text fields
            String diseaseName = diseaseNameField.getText();

            // Check if fields are empty
            if (diseaseName.isEmpty() || deadlyField.getText().isEmpty() ||
                    contagiousField.getText().isEmpty() || populationField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields first!");
                return;
            }

            int deadly = Integer.parseInt(deadlyField.getText());
            int contagious = Integer.parseInt(contagiousField.getText());
            int populationSize = Integer.parseInt(populationField.getText());

            // Validate inputs
            if (deadly < 1 || deadly > 100) {
                JOptionPane.showMessageDialog(this, "Deadliness must be between 1-100!");
                return;
            }
            if (contagious < 1 || contagious > 100) {
                JOptionPane.showMessageDialog(this, "Contagiousness must be between 1-100!");
                return;
            }
            if (populationSize < 1 || populationSize > 1000) {
                JOptionPane.showMessageDialog(this, "Population must be between 1-1000!");
                return;
            }

            // Ask for filename
            String filename = JOptionPane.showInputDialog(this,
                    "Enter filename to save parameters (e.g., outbreak.txt):");

            if (filename == null || filename.trim().isEmpty()) return;

            // Use DataSaver to save the parameters
            DataSaver.saveParameters(filename, diseaseName, deadly, contagious,
                    populationSize, settings);

            JOptionPane.showMessageDialog(this, "Parameters saved to " + filename);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers first!");
        }
    }

    /**
     * Loads disease parameters from a previously saved text file.
     * Uses DataSaver to read the file and populates all text fields and settings object.
     * After loading, all text fields will be filled with the saved values.
     *
     * @throws Exception if file cannot be read or contains invalid data
     */
    private void loadParameters() {
        try {
            // Ask for filename
            String filename = JOptionPane.showInputDialog(this,
                    "Enter filename to load parameters:");

            if (filename == null || filename.trim().isEmpty()) return;

            // Load the settings
            DataSaver.ParametersData data = DataSaver.loadParameters(filename);

            if (data == null) {
                JOptionPane.showMessageDialog(this, "Could not load file!");
                return;
            }

            // Fill in the text fields
            diseaseNameField.setText(data.diseaseName);
            deadlyField.setText(String.valueOf(data.deadly));
            contagiousField.setText(String.valueOf(data.contagious));
            populationField.setText(String.valueOf(data.populationSize));

            // Update settings object using setter methods
            settings.setBabyPercent(data.babyPercent);
            settings.setChildPercent(data.childPercent);
            settings.setTeenPercent(data.teenPercent);
            settings.setAdultPercent(data.adultPercent);
            settings.setElderPercent(data.elderPercent);
            settings.setBabyMalePercent(data.babyMalePercent);
            settings.setChildMalePercent(data.childMalePercent);
            settings.setTeenMalePercent(data.teenMalePercent);
            settings.setAdultMalePercent(data.adultMalePercent);
            settings.setElderMalePercent(data.elderMalePercent);
            settings.setVaccinatedPercent(data.vaccinatedPercent);

            JOptionPane.showMessageDialog(this, "Parameters loaded from " + filename +
                    "\n\nDisease: " + data.diseaseName +
                    "\nDeadliness: " + data.deadly + "%" +
                    "\nContagiousness: " + data.contagious + "%" +
                    "\nPopulation: " + data.populationSize);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
        }
    }

    /**
     * Runs the demographic setup process after disease parameters are entered.
     * Guides the user through a series of input dialogs to set age percentages,
     * gender ratios, and vaccination rate, then launches the main simulation.
     *
     * <p>The demographic input sequence:
     * <ol>
     *   <li>Ask for percentage of babies, children, teens, adults</li>
     *   <li>Calculate elder percentage (what's left from 100%)</li>
     *   <li>Ask for male percentage for each age group</li>
     *   <li>Ask for vaccination percentage</li>
     *   <li>Show summary and launch simulation</li>
     * </ol>
     *
     * @throws NumberFormatException if user enters invalid numbers
     */
    private void runSetup() {

        try {

            // ============ DISEASE PROPERTIES ===============
            String diseaseName = diseaseNameField.getText();

            // Validate disease name
            if (diseaseName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a disease name!");
                return;
            }

            int deadly = Integer.parseInt(deadlyField.getText());
            int contagious = Integer.parseInt(contagiousField.getText());
            int populationSize = Integer.parseInt(populationField.getText());

            // Validate inputs
            if (deadly < 1 || deadly > 100) {
                JOptionPane.showMessageDialog(this, "Deadliness must be between 1-100!");
                return;
            }
            if (contagious < 1 || contagious > 100) {
                JOptionPane.showMessageDialog(this, "Contagiousness must be between 1-100!");
                return;
            }
            if (populationSize < 1 || populationSize > 1000) {
                JOptionPane.showMessageDialog(this, "Population must be between 1-1000!");
                return;
            }

            Disease disease = new Disease(diseaseName, deadly, contagious);

            // Update settings with population size
            settings = new PopulationSettings(populationSize);

            // ================ DEMOGRAPHICS ====================

            String[] groupNames = settings.getGroupNames();
            int totalPercent = 0;

            // Get percentages for all groups except elders
            for (int i = 0; i < groupNames.length - 1; i++) {
                String percentStr = JOptionPane.showInputDialog(this,
                        "What percentage of your population would you like to be " + groupNames[i] +
                                "? (current total: " + totalPercent + "%)");

                if (percentStr == null) return; // User cancelled

                int percent = Integer.parseInt(percentStr);

                if (percent < 0 || percent > 100) {
                    JOptionPane.showMessageDialog(this, "Please enter a number between 0-100!");
                    return;
                }

                settings.setGroupPercent(i, percent);
                totalPercent += percent;

                String maleStr = JOptionPane.showInputDialog(this,
                        "What percentage of " + groupNames[i] + " (" + percent + "%) would you like to be males?");

                if (maleStr == null) return; // User cancelled

                int malePercent = Integer.parseInt(maleStr);

                if (malePercent < 0 || malePercent > 100) {
                    JOptionPane.showMessageDialog(this, "Please enter a number between 0-100!");
                    return;
                }

                settings.setGroupMalePercent(i, malePercent);
            }

            // Calculate elder percentage (what's left to make 100%)
            int elderPercent = 100 - totalPercent;

            if (elderPercent < 0) {
                JOptionPane.showMessageDialog(this, "ERROR: Percentages exceed 100%!");
                return;
            }
            settings.setGroupPercent(groupNames.length - 1, elderPercent);

            // Get elder male percentage
            String elderMaleStr = JOptionPane.showInputDialog(this,
                    "What percentage of elders would you like to be male? (Elders are " + elderPercent + "% of population)");

            if (elderMaleStr == null) return; // User cancelled

            int elderMalePercent = Integer.parseInt(elderMaleStr);

            if (elderMalePercent < 0 || elderMalePercent > 100) {
                JOptionPane.showMessageDialog(this, "Please enter a number between 0-100!");
                return;
            }

            settings.setGroupMalePercent(groupNames.length - 1, elderMalePercent);

            // Get vaccination percentage
            String vaccinatedStr = JOptionPane.showInputDialog(this,
                    "What percentage of the population should already be vaccinated? ");

            if (vaccinatedStr == null) return; // User cancelled

            int vaccinatedPercent = Integer.parseInt(vaccinatedStr);

            if (vaccinatedPercent < 0 || vaccinatedPercent > 100) {
                JOptionPane.showMessageDialog(this, "Please enter a number between 0-100!");
                return;
            }

            settings.setVaccinatedPercent(vaccinatedPercent);

            // Show summary before launching - using getter methods
            String summary = "=== SIMULATION SUMMARY ===\n" +
                    "Disease: " + diseaseName + "\n" +
                    "Deadliness: " + deadly + "%\n" +
                    "Contagiousness: " + contagious + "%\n" +
                    "Population: " + populationSize + "\n\n" +
                    "Babies: " + settings.getBabyPercent() + "% (Male: " + settings.getBabyMalePercent() + "%)\n" +
                    "Children: " + settings.getChildPercent() + "% (Male: " + settings.getChildMalePercent() + "%)\n" +
                    "Teens: " + settings.getTeenPercent() + "% (Male: " + settings.getTeenMalePercent() + "%)\n" +
                    "Adults: " + settings.getAdultPercent() + "% (Male: " + settings.getAdultMalePercent() + "%)\n" +
                    "Elders: " + settings.getElderPercent() + "% (Male: " + settings.getElderMalePercent() + "%)\n\n" +
                    "Vaccinated: " + settings.getVaccinatedPercent() + "%";

            int confirm = JOptionPane.showConfirmDialog(this, summary,
                    "Ready to Launch?", JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (confirm != JOptionPane.OK_OPTION) return;

            // Launch the main simulation
            ContagionSimulationMain.launchSimulation(disease, settings);

            // Close this window
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
