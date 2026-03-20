

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*; // Added for save/load functionality

/**
 * ContagionGUI class - The main visual window for the disease simulation.
 * This class creates the grid display, handles user interactions through buttons,
 * and manages the animation timer that drives the simulation forward.
 * It extends JFrame to create a standalone window with controls and statistics.
 *
 * @author Codevid-19 Team
 * @version 1.0
 */
public class ContagionGUI extends JFrame {
    //all the variables for the class
    /** Size of one grid cell in pixels (20x20) */
    private static final int CELL_SIZE = 20; //this is the size of one cell (square) (in this case 20x20)
    /** Number of columns in the grid */
    private static final int GRID_COLS = 40; //number of columns
    /** Number of rows in the grid */
    private static final int GRID_ROWS = 30; //number of rows
    /** Array of all age group names */
    private static final String[] GROUP_NAMES = {"baby", "child", "teen", "adult", "elder"}; //list of strings of all ages
    /** Color for healthy individuals (YELLOW) */
    private static final Color COLOR_HEALTHY   = Color.YELLOW; //yellow is the color of someone who's healthy
    /** Color for asymptomatic infected individuals (ORANGE) */
    private static final Color COLOR_INFECTED  = Color.ORANGE; //orange for infected
    /** Color for symptomatic infected individuals (RED) */
    private static final Color COLOR_SYMPTOMATIC = Color.RED; //red for symptoms
    /** Color for recovered individuals (BLUE) */
    private static final Color COLOR_RECOVERED = Color.BLUE; //blue for a recoveree
    /** Color for deceased individuals (BLACK) */
    private static final Color COLOR_DECEASED  = Color.BLACK; //black for deceased
    /** Color for vaccinated individuals (CYAN) */
    private static final Color COLOR_VACCINATED = new Color(0, 180, 180); //cyan for vaccinated

    /** The disease being simulated */
    private final Disease disease; //from Disease class
    /** Population settings including demographics */
    private final PopulationSettings settings; //from PopulationSettings class
    /** The simulation model that tracks statistics */
    private ContagionModel model; //from ContagionModel class
    /** List of all Person objects in the simulation */
    private ArrayList<Person> people; //array list of all the people
    /** Timer that drives the animation ticks */
    private final Timer animTimer; //timer
    /** Flag indicating whether animation is currently running */
    private boolean animating = false; //initially set to false, if user wants to animate they can
    /** Current tick/day counter */
    private int tickCount = 0; //initial tick count is 0
    /** Random number generator for various chance calculations */
    private final Random rand = new Random(); //random
    /** The panel where the grid is drawn */
    private final SimPanel simPanel; //simpanel
    /** Button to start/stop animation */
    private JButton animateBtn; //button
    /** Slider to control animation speed */
    private JSlider speedSlider; //slider
    /** Label showing current tick count */
    private JLabel tickLabel; //label for the ticks
    /** Array of labels for each age group's statistics */
    private JLabel[] groupLabels;  //labels: one per age group
    /** Label showing total population statistics */
    private JLabel totalLabel; //label for the total
    /** Button to save current simulation state */
    private JButton saveButton; // Added for save functionality
    /** Button to load a previously saved simulation */
    private JButton loadButton; // Added for load functionality


    /**
     * Constructor - Creates the main simulation window.
     * Initializes all GUI components, sets up the timer, and creates the initial population.
     *
     * @param disease The disease object containing name and properties
     * @param settings The population settings with demographics
     */
    //constructor: names the disease parameter as the title, sets the background, grid panel,
    //timer, and all the panels, and sets all the people onto the grid
    public ContagionGUI(Disease disease, PopulationSettings settings) {
        super("Codevid-19: " + disease.getName() + " Simulation");

        this.disease  = disease;
        this.settings = settings;
        this.model    = new ContagionModel(settings);

        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(new Color(30, 30, 30));

        //grid panel
        simPanel = new SimPanel();
        simPanel.setPreferredSize(new Dimension(GRID_COLS * CELL_SIZE, GRID_ROWS * CELL_SIZE));
        simPanel.setBackground(new Color(34, 139, 34));
        add(simPanel, BorderLayout.CENTER);

        //stats panel
        add(buildStatsPanel(), BorderLayout.EAST);

        //control panel
        add(buildControlPanel(), BorderLayout.SOUTH);

        //disease info
        add(buildInfoBar(), BorderLayout.NORTH);

        //timer (had help from google)
        animTimer = new Timer(100, e -> doTick());

        //build population and place on grid
        initPeople();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Initializes the population by creating all Person objects based on settings.
     * Creates healthy and vaccinated individuals for each age group,
     * then selects one random adult to be Patient Zero (first infected).
     */
    // method that sets all the people up, sets up all the ages, statuses, gender,
    // and randomly picks one person as patient zero
    private void initPeople() {
        people = new ArrayList<>();

        for (int g = 0; g < GROUP_NAMES.length; g++) {
            String group = GROUP_NAMES[g];

            int healthy   = model.getHealthyCount(g);
            int vaccinated = model.getVaccinatedCount(g);

            // create healthy people
            for (int i = 0; i < healthy; i++) {
                int[] pos = randomEmptyCell();
                Healthy p = new Healthy(pos[0], pos[1], randSex(g), group, randBelief());
                people.add(p);
            }

            //create vaccinated people (Healthy + vaccinated flag)
            for (int i = 0; i < vaccinated; i++) {
                int[] pos = randomEmptyCell();
                Healthy p = new Healthy(pos[0], pos[1], randSex(g), group, randBelief());
                p.getVaccinated();
                people.add(p);
            }
        }

        //infect patient zero — pick a random adult
        for (Person p : people) {
            if (p.ageGroup.equalsIgnoreCase("adult") && p instanceof Healthy) {
                //replace with Infected in the list
                int idx = people.indexOf(p);
                Infected infected = new Infected(p.row, p.col,
                        p.sex, p.ageGroup, p.belief);
                people.set(idx, infected);
                model.infectGroup("adult", 1);
                break;
            }
        }

        simPanel.repaint();
        updateStats();
    }

    /**
     * Finds a random empty cell on the grid that is not already occupied.
     *
     * @return int array of length 2 containing [row, col] of empty cell
     */
    //returns a random unoccupied cell on the grid
    private int[] randomEmptyCell() {
        boolean[][] occupied = new boolean[GRID_ROWS][GRID_COLS];
        for (Person p : people) {
            if (p.row >= 0 && p.row < GRID_ROWS && p.col >= 0 && p.col < GRID_COLS) {
                occupied[p.row][p.col] = true;
            }
        }
        int row, col;
        do {
            row = rand.nextInt(GRID_ROWS);
            col = rand.nextInt(GRID_COLS);
        } while (occupied[row][col]);
        return new int[]{row, col};
    }

    /**
     * Randomly determines sex based on male percentage for the given age group.
     *
     * @param groupIndex Index of the age group
     * @return "MALE" or "FEMALE"
     */
    //uses the percentage of males to fill out randomly which people are male
    private String randSex(int groupIndex) {
        int malePct = settings.getGroupMalePercent(groupIndex);
        return rand.nextInt(100) < malePct ? "MALE" : "FEMALE";
    }

    /**
     * Randomly determines belief status (50/50 chance).
     *
     * @return "BELIEVER" or "NON-BELIEVER"
     */
    //uses the percentage of males to fill out randomly which people are believers
    private String randBelief() {
        return rand.nextBoolean() ? "BELIEVER" : "NON-BELIEVER";
    }

    /**
     * Executes one tick/day of the simulation.
     * Moves all people, spreads disease, progresses infections,
     * updates display, and checks if simulation should end.
     */
    //one tick of the simulation, moves all the people, spreads the disease, progresses infections,
    //and stops the simulation if there are no more infected people left
    private void doTick() {
        movePeople();
        spreadDisease();
        progressInfections();

        tickCount++;
        simPanel.repaint();
        updateStats();

        //dtop animation if no infected left
        if (countByStatus("infected") == 0 && animating) {
            toggleAnimation();
            JOptionPane.showMessageDialog(this,
                    "Simulation complete! No more infected people.",
                    "Simulation Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Moves all living, non-quarantined people to adjacent cells.
     * Deceased people do not move.
     */
    //move one cell in a random direction unless they are dead or quarantined
    private void movePeople() {
        for (Person p : people) {
            if (p instanceof Deceased) continue; // dead don't move

            // If quarantined, don't move
            if (p.isQuarantined) continue;

            p.move(GRID_ROWS, GRID_COLS);
        }
    }

    /**
     * Handles disease transmission between infected and healthy individuals.
     * Checks for adjacent infected and healthy pairs, calculates infection chance
     * based on disease contagiousness and belief status, and creates new infections.
     */
    //how diseases spread, if there is a person nearby, use the contagious percentage as a chance
    //to infect the nearby person, believers are careful and are less likely to get infected
    private void spreadDisease() {
        ArrayList<Person> newlyInfected = new ArrayList<>();

        //had help from google
        for (Person source : people) {
            if (!(source instanceof Infected inf)) continue;
            if (!inf.isContagious()) continue;

            for (Person target : people) {
                if (!(target instanceof Healthy)) continue;
                if (target.isVaccinated()) continue; // vaccinated are protected

                //check if adjacent (within 1 cell in any direction)
                int dr = Math.abs(source.row - target.row);
                int dc = Math.abs(source.col - target.col);
                if (dr > 1 || dc > 1) continue;

                //believers are careful and half transmission chance
                double beliefModifier = target.belief.equals("BELIEVER") ? 0.5 : 1.0;
                double chance = (disease.getContagious() / 100.0) * beliefModifier;

                if (rand.nextDouble() < chance) {
                    newlyInfected.add(target);
                }
            }
        }

        //replace Healthy with Infected for newly infected people
        for (Person p : newlyInfected) {
            int idx = people.indexOf(p);
            Infected newInf = new Infected(p.row, p.col, p.sex, p.ageGroup, p.belief);
            people.set(idx, newInf);
            model.infectGroup(p.ageGroup, 1);
        }
    }

    /**
     * Updates the status of all infected individuals.
     * Increases days infected, checks for symptom development,
     * and after 14 days determines if they recover or die based on age and disease deadliness.
     */
    //infection day goes up, if 2 weeks pass, resolve what happens to the infected,
    //uses arraylists to replace people in case they die or heal
    private void progressInfections() {
        ArrayList<Integer> toReplace = new ArrayList<>();
        ArrayList<Person> replacements = new ArrayList<>();

        for (int i = 0; i < people.size(); i++) {
            Person p = people.get(i);
            if (!(p instanceof Infected inf)) continue;

            inf.updateInfection(); //counts daysInfected, develops symptoms, etc.

            //after 14 days, resolve outcome
            if (inf.daysInfected >= 14) {
                //age group modifies death chance
                double ageMod = getAgeDeathModifier(p.ageGroup);
                double deathChance = (disease.getDeadly() / 100.0) * ageMod;

                Person replacement;
                if (rand.nextDouble() < deathChance) {
                    replacement = new Deceased(p.row, p.col, p.sex, p.ageGroup, p.belief);
                    model.deceasedGroup(p.ageGroup, 1);
                } else {
                    replacement = new Recovered(p.row, p.col, p.sex, p.ageGroup, p.belief);
                    model.recoveredGroup(p.ageGroup, 1);
                }
                toReplace.add(i);
                replacements.add(replacement);
            }
        }

        for (int k = 0; k < toReplace.size(); k++) {
            people.set(toReplace.get(k), replacements.get(k));
        }
    }

    /**
     * Returns a death risk multiplier based on age group.
     * Elders and babies have higher risk, children and teens have lower risk.
     *
     * @param ageGroup The age group as a string
     * @return Multiplier for death chance
     */
    //old people and babies are more likely to die
    private double getAgeDeathModifier(String ageGroup) {
        return switch (ageGroup.toLowerCase()) {
            case "baby" -> 1.5;
            case "child" -> 0.5;
            case "teen" -> 0.3;
            case "adult" -> 0.8;
            case "elder" -> 2.5;
            default -> 1.0;
        };
    }

    /**
     * Saves the current simulation state to a text file.
     * Includes disease parameters, tick count, and every person's current status.
     */
    // Added save functionality
    private void saveSimulation() {
        try {
            String filename = JOptionPane.showInputDialog(this,
                    "Enter filename to save simulation:");
            if (filename == null || filename.trim().isEmpty()) return;

            PrintWriter writer = new PrintWriter(new FileWriter(filename));

            // Save disease info
            writer.println("DISEASE_NAME=" + disease.getName());
            writer.println("DEADLINESS=" + disease.getDeadly());
            writer.println("CONTAGIOUS=" + disease.getContagious());
            writer.println("TICK=" + tickCount);

            // Save each person
            for (Person p : people) {
                String status = "HEALTHY";
                if (p instanceof Infected) status = "INFECTED";
                else if (p instanceof Recovered) status = "RECOVERED";
                else if (p instanceof Deceased) status = "DECEASED";

                writer.println(p.row + "," + p.col + "," +
                        p.sex + "," +
                        p.ageGroup + "," +
                        p.belief + "," +
                        status + "," +
                        p.daysInfected + "," +
                        p.isVaccinated + "," +
                        p.isQuarantined);
            }

            writer.close();
            JOptionPane.showMessageDialog(this, "Simulation saved to " + filename);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving: " + e.getMessage());
        }
    }

    /**
     * Loads a previously saved simulation state from a text file.
     * Restores all disease parameters and recreates every person with their saved status.
     */
    // Added load functionality
    private void loadSimulation() {
        try {
            String filename = JOptionPane.showInputDialog(this,
                    "Enter filename to load simulation:");
            if (filename == null || filename.trim().isEmpty()) return;

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            ArrayList<Person> newPeople = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("DISEASE_NAME=")) {
                    // Skip disease info - already have it
                    continue;
                } else if (line.startsWith("DEADLINESS=")) {
                    continue;
                } else if (line.startsWith("CONTAGIOUS=")) {
                    continue;
                } else if (line.startsWith("TICK=")) {
                    tickCount = Integer.parseInt(line.split("=")[1]);
                } else {
                    // This is a person line
                    String[] parts = line.split(",");
                    if (parts.length >= 9) {
                        int row = Integer.parseInt(parts[0]);
                        int col = Integer.parseInt(parts[1]);
                        String sex = parts[2];
                        String ageGroup = parts[3];
                        String belief = parts[4];
                        String status = parts[5];
                        int daysInfected = Integer.parseInt(parts[6]);
                        boolean vaccinated = Boolean.parseBoolean(parts[7]);
                        boolean quarantined = Boolean.parseBoolean(parts[8]);

                        Person p;
                        switch (status) {
                            case "HEALTHY":
                                p = new Healthy(row, col, sex, ageGroup, belief);
                                if (vaccinated) ((Healthy)p).getVaccinated();
                                break;
                            case "INFECTED":
                                p = new Infected(row, col, sex, ageGroup, belief);
                                ((Infected)p).daysInfected = daysInfected;
                                break;
                            case "RECOVERED":
                                p = new Recovered(row, col, sex, ageGroup, belief);
                                break;
                            case "DECEASED":
                                p = new Deceased(row, col, sex, ageGroup, belief);
                                break;
                            default:
                                p = new Healthy(row, col, sex, ageGroup, belief);
                        }

                        p.isQuarantined = quarantined;
                        newPeople.add(p);
                    }
                }
            }

            reader.close();
            people = newPeople;
            simPanel.repaint();
            updateStats();
            JOptionPane.showMessageDialog(this, "Simulation loaded from " + filename);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading: " + e.getMessage());
        }
    }

    //everything after this point is purely GUI stuff and I had had help from google with
//buttons

    /**
     * Toggles the animation on/off.
     * Starts or stops the timer and updates button text accordingly.
     */
    private void toggleAnimation() {
        if (animating) {
            animTimer.stop();
            animateBtn.setText("▶  Animate");
        } else {
            animTimer.start();
            animateBtn.setText("⏸  Stop");
        }
        animating = !animating;
    }

    /**
     * Resets the simulation to its initial state.
     * Stops animation, resets tick counter, recreates the model and population.
     */
    private void resetSim() {
        if (animating) toggleAnimation();
        tickCount = 0;
        model = new ContagionModel(settings);
        initPeople();
        simPanel.repaint();
        updateStats();
    }

    /**
     * Updates all statistics displays including group labels and total label.
     */
    //stats: had help from google
    private void updateStats() {
        tickLabel.setText("  Tick: " + tickCount + "  ");

        for (int i = 0; i < GROUP_NAMES.length; i++) {
            groupLabels[i].setText(String.format(
                    "<html><b>%s</b>: H%d I%d R%d D%d V%d</html>",
                    GROUP_NAMES[i].toUpperCase(),
                    model.getHealthyCount(i),
                    model.getInfectedCount(i),
                    model.getRecoveredCount(i),
                    model.getDeceasedCount(i),
                    model.getVaccinatedCount(i)
            ));
        }

        int h = countByStatus("healthy");
        int inf = countByStatus("infected");
        int r = countByStatus("recovered");
        int d = countByStatus("deceased");

        totalLabel.setText(String.format(
                "<html>TOTAL — Healthy: <font color='yellow'>%d</font>  " +
                        "Infected: <font color='orange'>%d</font>  " +
                        "Recovered: <font color='cyan'>%d</font>  " +
                        "Deceased: <font color='gray'>%d</font></html>",
                h, inf, r, d
        ));
    }

    /**
     * Counts the number of people with a given status.
     *
     * @param status The status to count ("healthy", "infected", "recovered", "deceased")
     * @return The count of people with that status
     */
    private int countByStatus(String status) {
        int count = 0;
        for (Person p : people) {
            switch (status) {
                case "healthy":   if (p instanceof Healthy && !p.isVaccinated()) count++; break;
                case "infected":  if (p instanceof Infected)  count++; break;
                case "recovered": if (p instanceof Recovered) count++; break;
                case "deceased":  if (p instanceof Deceased)  count++; break;
            }
        }
        return count;
    }

//ui

    /**
     * Builds the information bar at the top of the window.
     * Displays disease name, deadliness, contagiousness, and current tick.
     *
     * @return JPanel containing the info bar
     */
    private JPanel buildInfoBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        bar.setBackground(new Color(20, 20, 60));

        JLabel title = new JLabel("  Disease: " + disease.getName());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel deadly = new JLabel("Deadliness: " + disease.getDeadly() + "%");
        deadly.setForeground(new Color(255, 100, 100));
        deadly.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JLabel contagious = new JLabel("Contagiousness: " + disease.getContagious() + "%");
        contagious.setForeground(new Color(255, 200, 80));
        contagious.setFont(new Font("SansSerif", Font.PLAIN, 12));

        tickLabel = new JLabel("  Tick: 0  ");
        tickLabel.setForeground(Color.CYAN);
        tickLabel.setFont(new Font("Monospaced", Font.BOLD, 13));

        bar.add(title);
        bar.add(new JSeparator(SwingConstants.VERTICAL));
        bar.add(deadly);
        bar.add(contagious);
        bar.add(Box.createHorizontalStrut(30));
        bar.add(tickLabel);

        return bar;
    }

    /**
     * Builds the statistics panel on the right side.
     * Shows counts for each age group and includes the legend.
     *
     * @return JPanel containing statistics display
     */
    //had a little help from google
    private JPanel buildStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(25, 25, 35));
        panel.setPreferredSize(new Dimension(200, GRID_ROWS * CELL_SIZE));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "Group Stats", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 11), Color.GRAY
        ));

        groupLabels = new JLabel[GROUP_NAMES.length];

        for (int i = 0; i < GROUP_NAMES.length; i++) {
            groupLabels[i] = new JLabel("loading...");
            groupLabels[i].setForeground(Color.LIGHT_GRAY);
            groupLabels[i].setFont(new Font("Monospaced", Font.PLAIN, 11));
            groupLabels[i].setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 4));
            panel.add(groupLabels[i]);
            panel.add(new JSeparator());
        }

        panel.add(Box.createVerticalStrut(10));

        //legend (had help from google)
        panel.add(buildLegend());

        return panel;
    }

    /**
     * Builds the legend panel explaining what each color represents.
     *
     * @return JPanel containing the legend
     */
    private JPanel buildLegend() {//had a little help from google
        JPanel legend = new JPanel();
        legend.setLayout(new BoxLayout(legend, BoxLayout.Y_AXIS));
        legend.setBackground(new Color(25, 25, 35));
        legend.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "Legend", TitledBorder.CENTER, TitledBorder.TOP,
                new Font("SansSerif", Font.PLAIN, 10), Color.GRAY
        ));

        addLegendRow(legend, COLOR_HEALTHY,    "Healthy");
        addLegendRow(legend, COLOR_VACCINATED, "Vaccinated");
        addLegendRow(legend, COLOR_INFECTED,   "Infected (no symptoms)");
        addLegendRow(legend, COLOR_SYMPTOMATIC,"Infected (symptoms)");
        addLegendRow(legend, COLOR_RECOVERED,  "Recovered");
        addLegendRow(legend, COLOR_DECEASED,   "Deceased");

        JLabel believerNote = new JLabel("  [Blue border = Believer]");
        believerNote.setForeground(Color.GRAY);
        believerNote.setFont(new Font("SansSerif", Font.PLAIN, 10));
        legend.add(believerNote);

        return legend;
    }

    /**
     * Helper method to add a row to the legend panel.
     *
     * @param panel The legend panel to add to
     * @param color The color to display
     * @param label The text label
     */
    private void addLegendRow(JPanel panel, Color color, String label) {
        JLabel lbl = new JLabel("  ■ " + label);
        lbl.setForeground(color.equals(Color.BLACK) ? Color.GRAY : color);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        panel.add(lbl);
    }

    /**
     * Builds the control panel at the bottom with all buttons and slider.
     *
     * @return JPanel containing all controls
     */
    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panel.setBackground(new Color(40, 40, 40));

        //total label spans full width above buttons
        totalLabel = new JLabel("Loading...");
        totalLabel.setForeground(Color.WHITE);
        totalLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topRow.setBackground(new Color(40, 40, 40));
        topRow.add(totalLabel);

        animateBtn = new JButton("▶  Animate");
        JButton tickBtn = new JButton("⏭  Tick");
        JButton resetBtn = new JButton("↺  Reset");
        JButton quitBtn = new JButton("✕  Quit");

        // Added save and load buttons
        saveButton = new JButton("💾 Save");
        loadButton = new JButton("📂 Load");

        styleButton(animateBtn, new Color(50, 140, 50));
        styleButton(tickBtn,    new Color(50, 90,  170));
        styleButton(resetBtn,   new Color(160, 110, 20));
        styleButton(quitBtn,    new Color(160, 40,  40));
        styleButton(saveButton, new Color(100, 100, 150)); // Style for save button
        styleButton(loadButton, new Color(100, 100, 150)); // Style for load button

        animateBtn.addActionListener(e -> toggleAnimation());
        tickBtn.addActionListener(e -> doTick());
        resetBtn.addActionListener(e -> resetSim());
        quitBtn.addActionListener(e -> System.exit(0));
        saveButton.addActionListener(e -> saveSimulation()); // Add save action
        loadButton.addActionListener(e -> loadSimulation()); // Add load action

        JLabel slowLbl = new JLabel("Slow");
        JLabel fastLbl = new JLabel("Fast");
        slowLbl.setForeground(Color.GRAY);
        fastLbl.setForeground(Color.GRAY);

        //had help with sliders
        speedSlider = new JSlider(10, 490, 390); // higher = faster
        speedSlider.setPreferredSize(new Dimension(140, 28));
        speedSlider.setBackground(new Color(40, 40, 40));
        speedSlider.addChangeListener(e ->
                animTimer.setDelay(500 - speedSlider.getValue())
        );

        panel.add(animateBtn);
        panel.add(tickBtn);
        panel.add(resetBtn);
        panel.add(quitBtn);
        panel.add(saveButton); // Add save button to panel
        panel.add(loadButton); // Add load button to panel
        panel.add(Box.createHorizontalStrut(15));
        panel.add(slowLbl);
        panel.add(speedSlider);
        panel.add(fastLbl);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(40, 40, 40));
        wrapper.add(topRow, BorderLayout.NORTH);
        wrapper.add(panel, BorderLayout.CENTER);
        return wrapper;
    }

    /**
     * Applies consistent styling to buttons.
     *
     * @param btn The button to style
     * @param bg The background color for the button
     */
    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

//had a pretty good amount of help from google

    /**
     * Inner class representing the panel where the simulation grid is drawn.
     * Handles rendering all Person objects on the grid.
     */
    class SimPanel extends JPanel {
        /**
         * Paints the grid and all people on it.
         *
         * @param g The Graphics object to draw with
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            //draw all people using their own draw() method
            if (people != null) {
                for (Person p : people) {
                    p.draw(g);
                }
            }
        }
    }
}