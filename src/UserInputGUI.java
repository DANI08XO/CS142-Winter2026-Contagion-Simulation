
// GUI for user input in introduction

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInputGUI extends JFrame {

    private JTextField diseaseNameField;
    private JTextField deadlyField;
    private JTextField contagiousField;
    private JTextField populationField;
    private JButton startButton;
    private JTextArea outputArea;

    //Constructor
    public UserInputGUI() {

        super("User Input for Contagion Simulation"); // Title of GUI

        setSize(350, 350);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ================== DISEASE INPUT ======================

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        // ===== Disease Name =====
        JLabel nameLabel = new JLabel("Name of your disease:");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);

        diseaseNameField = new JTextField();
        diseaseNameField.setMaximumSize(new Dimension(200, 25));
        diseaseNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(diseaseNameField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ===== Deadliness =====
        JLabel deadlyLabel = new JLabel("Deadliness (1-100%):");
        deadlyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(deadlyLabel);

        deadlyField = new JTextField();
        deadlyField.setMaximumSize(new Dimension(100, 25));
        deadlyField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(deadlyField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ===== Contagiousness =====
        JLabel contagiousLabel = new JLabel("Contagiousness (1-100%):");
        contagiousLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(contagiousLabel);

        contagiousField = new JTextField();
        contagiousField.setMaximumSize(new Dimension(100, 25));
        contagiousField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(contagiousField);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ===== Population =====
        JLabel popLabel = new JLabel("Population Size (1-1000):");
        popLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(popLabel);

        populationField = new JTextField();
        populationField.setMaximumSize(new Dimension(120, 25));
        populationField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(populationField);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // ===== Button =====
        startButton = new JButton("Start Simulation");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(startButton);

        panel.add(Box.createVerticalGlue());

        // Add panel to center of frame
        add(panel, BorderLayout.CENTER);

        startButton.addActionListener(e -> runSetup());

    }

    private void runSetup() {

        try {

            // ============ DISEASE PROPERTIES ===============
            String diseaseName = diseaseNameField.getText();
            int deadly = Integer.parseInt(deadlyField.getText());
            int contagious = Integer.parseInt(contagiousField.getText());
            int populationSize = Integer.parseInt(populationField.getText());

            Disease disease = new Disease (diseaseName, deadly, contagious);

            PopulationSettings settings = new PopulationSettings(populationSize);

            // ================ DEMOGRAPHICS ====================

            String[] groupNames = settings.getGroupNames();
            int totalPercent = 0;

            for (int i = 0; i < groupNames.length - 1; i++) {
                String percentStr = JOptionPane.showInputDialog(this, "What percentage of your population would you like to be " + groupNames[i] + "? (current population percent used: " + totalPercent + "% )");
                int percent = Integer.parseInt(percentStr);
                settings.setGroupPercent(i, percent);
                totalPercent += percent;

                String maleStr = JOptionPane.showInputDialog(this, "What percentage of " + groupNames[i] + " would you like to be males?");
                int malePercent = Integer.parseInt(maleStr);
                settings.setGroupMalePercent(i, malePercent);
            }

            int elderPercent = 100 - totalPercent;

            if (elderPercent < 0) {
                JOptionPane.showMessageDialog(this, "ERROR: Percentage must not exceed 100%!");
                return;
            }
            settings.setGroupPercent(groupNames.length - 1, elderPercent);

            String elderMaleStr = JOptionPane.showInputDialog(this, "What percentage of elders would you like to be male (percent of elders " + elderPercent +"% )? ");
            int elderMalePercent = Integer.parseInt(elderMaleStr);
            settings.setGroupPercent(groupNames.length - 1, elderMalePercent);

            String vaccinatedStr = JOptionPane.showInputDialog(this, "What percentage of the population should already be vaccinated? ");
            int vaccinatedPercent = Integer.parseInt(vaccinatedStr);
            settings.setVaccinatedPercent(vaccinatedPercent);

            ContagionSimulationMain.launchSimulation(disease, settings);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
