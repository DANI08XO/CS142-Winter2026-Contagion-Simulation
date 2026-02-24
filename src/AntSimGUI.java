// AntSimGUI.java

import javax.swing.*;
import java.awt.*;

public class AntSimGUI extends JFrame {

    private final AntSim sim;
    private final GridPanel panel;

    // Adjust these
    private static final int TILE = 15;
    private static final int FPS = 15;

    public AntSimGUI(AntSim sim) {
        super("Ant Simulation");
        if (sim == null) throw new IllegalArgumentException("sim");

        this.sim = sim;
        this.panel = new GridPanel(sim);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        int delayMs = 1000 / FPS;
        new Timer(delayMs, e -> {
            sim.step();
            panel.repaint();
        }).start();
    }

    private static class GridPanel extends JPanel {
        private final AntSim sim;

        GridPanel(AntSim sim) {
            this.sim = sim;
            WorldGrid w = sim.getWorld();
            setPreferredSize(new Dimension(w.getWidth() * TILE, w.getHeight() * TILE));
            setFocusable(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            WorldGrid w = sim.getWorld();

            drawTerrain(g, w);
            drawObjects(g, w);
            drawAnts(g);
        }

        private void drawTerrain(Graphics g, WorldGrid w) {
            for (int y = 0; y < w.getHeight(); y++) {
                for (int x = 0; x < w.getWidth(); x++) {
                    Point p = new Point(x, y);
                    Terrain t = w.getTerrainAt(p);

                    // Simple palette
                    if (t instanceof Tunnel) g.setColor(Color.LIGHT_GRAY);
                    else if (t instanceof Rock) g.setColor(Color.DARK_GRAY);
                    else if (t instanceof Air) g.setColor(Color.WHITE);
                    else g.setColor(new Color(110, 70, 30)); // Dirt default

                    g.fillRect(x * TILE, y * TILE, TILE, TILE);

                    // grid lines
                    g.setColor(Color.BLACK);
                    g.drawRect(x * TILE, y * TILE, TILE, TILE);
                }
            }
        }

        private void drawObjects(Graphics g, WorldGrid w) {
            for (int y = 0; y < w.getHeight(); y++) {
                for (int x = 0; x < w.getWidth(); x++) {
                    Point p = new Point(x, y);
                    WorldObject obj = w.getObjectAt(p);
                    if (obj == null) continue;

                    // Draw a small circle for objects
                    g.setColor(Color.GREEN);
                    int cx = x * TILE + TILE / 4;
                    int cy = y * TILE + TILE / 4;
                    g.fillOval(cx, cy, TILE / 2, TILE / 2);

                    // Optional symbol overlay
                    g.setColor(Color.BLACK);
                    g.drawString(String.valueOf(obj.getSymbol()), x * TILE + TILE / 2 - 3, y * TILE + TILE / 2 + 4);
                }
            }
        }

        private void drawAnts(Graphics g) {
            for (Ant a : sim.getAnts()) {
                if (!a.isAlive()) continue;

                Point p = a.getPoint();

                // Color by type
                if (a instanceof QueenAnt) g.setColor(Color.MAGENTA);
                else if (a instanceof GuardAnt) g.setColor(Color.RED);
                else if (a instanceof WorkerAnt) g.setColor(Color.ORANGE);
                else g.setColor(Color.PINK);

                g.fillOval(p.x * TILE + 2, p.y * TILE + 2, TILE - 4, TILE - 4);

                // Symbol overlay
                g.setColor(Color.BLACK);
                g.drawString(String.valueOf(a.getSymbol()), p.x * TILE + TILE / 2 - 4, p.y * TILE + TILE / 2 + 4);
            }
        }
    }
}