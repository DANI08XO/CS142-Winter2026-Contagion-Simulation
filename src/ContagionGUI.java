import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class ContagionGUI extends JFrame {
    private static final int CELL_SIZE = 20;
    private static final int GRID_COLS = 40;
    private static final int GRID_ROWS = 30;
    private static final String[] GROUP_NAMES = {"baby", "child", "teen", "adult", "elder"};
    private static final Color COLOR_HEALTHY   = Color.YELLOW;
    private static final Color COLOR_INFECTED  = Color.ORANGE;
    private static final Color COLOR_SYMPTOMATIC = Color.RED;
    private static final Color COLOR_RECOVERED = Color.BLUE;
    private static final Color COLOR_DECEASED  = Color.BLACK;
    private static final Color COLOR_VACCINATED = new Color(0, 180, 180);
    private final Disease disease;
    private final PopulationSettings settings;
    private ContagionModel model;
    private ArrayList<Person> people;
    private final Timer animTimer;
    private boolean animating = false;
    private int tickCount = 0;
    private final Random rand = new Random();
    private final SimPanel simPanel;
    private JButton animateBtn;
    private JSlider speedSlider;
    private JLabel tickLabel;
    private JLabel[] groupLabels;  // one per age group
    private JLabel totalLabel;


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
        simPanel.setBackground(new Color(20, 20, 20));
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

    private String randSex(int groupIndex) {
        int malePct = settings.getGroupMalePercent(groupIndex);
        return rand.nextInt(100) < malePct ? "MALE" : "FEMALE";
    }

    private String randBelief() {
        return rand.nextBoolean() ? "BELIEVER" : "NON-BELIEVER";
    }

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

    //move one cell
    private void movePeople() {
        for (Person p : people) {
            if (p instanceof Deceased) continue; // dead don't move

            // If quarantined, don't move
            if (p.isQuarantined) continue;

            p.move(GRID_ROWS, GRID_COLS);
        }
    }

    //how diseases spread
    private void spreadDisease() {
        ArrayList<Integer> newlyInfected = new ArrayList<>();

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
                    int idx = people.indexOf(target);
                    if (idx != -1) {
                        newlyInfected.add(idx);
                    }
                }
            }
        }

        //replace Healthy with Infected for newly infected people
       /* for (Person p : newlyInfected) {
            int idx = people.indexOf(p);
            Infected newInf = new Infected(p.row, p.col, p.sex, p.ageGroup, p.belief);
            people.set(idx, newInf);
            model.infectGroup(p.ageGroup, 1);
        }*/

        for (int idx : newlyInfected) {
            Person p = people.get(idx);
            Infected newInf = new Infected(p.row, p.col, p.sex, p.ageGroup, p.belief);
            people.set(idx, newInf);
            model.infectGroup(p.ageGroup, 1);
        }

    }

    //infection day goes up
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

//buttons

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

    private void resetSim() {
        if (animating) toggleAnimation();
        tickCount = 0;
        model = new ContagionModel(settings);
        initPeople();
        simPanel.repaint();
        updateStats();
    }

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

    private void addLegendRow(JPanel panel, Color color, String label) {
        JLabel lbl = new JLabel("  ■ " + label);
        lbl.setForeground(color.equals(Color.BLACK) ? Color.GRAY : color);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        panel.add(lbl);
    }

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

        styleButton(animateBtn, new Color(50, 140, 50));
        styleButton(tickBtn,    new Color(50, 90,  170));
        styleButton(resetBtn,   new Color(160, 110, 20));
        styleButton(quitBtn,    new Color(160, 40,  40));

        animateBtn.addActionListener(e -> toggleAnimation());
        tickBtn.addActionListener(e -> doTick());
        resetBtn.addActionListener(e -> resetSim());
        quitBtn.addActionListener(e -> System.exit(0));

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

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

//had a pretty good amount of help from google

    class SimPanel extends JPanel {
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
