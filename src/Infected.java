
import java.awt.Color;
import java.awt.Graphics;

/**
 * INFECTED CLASS - Level 3 of inheritance
 * This class inherits from Person
 * Represents a person who IS sick with the disease
 * Colors: ORANGE (no symptoms) or RED (with symptoms)
 */
public class Infected extends Person {

    /**
     * CONSTRUCTOR - Creates a new Infected person
     * @param row Starting row on grid
     * @param col Starting column on grid
     * @param sex Male or Female
     * @param ageGroup Child, Teen, Adult, Elder
     * @param belief Believer or Non-believer
     */
    public Infected(int row, int col, String sex, String ageGroup, String belief) {
        // Call the Person constructor first!
        super(row, col, sex, ageGroup, belief);

        // Infected people start at day 0 of infection
        this.daysInfected = 0;
        // Initially they might not show symptoms (asymptomatic)
        this.hasSymptoms = false;
        // Asymptomatic infected are ORANGE
        this.color = Color.ORANGE;
    }

    /**
     * UPDATE INFECTION - How infected people change over time
     * Overrides the Person class method
     * Called each day to update the infection
     */
    @Override
    public void updateInfection() {
        // Count another day of being infected
        daysInfected++;

        // SYMPTOM DEVELOPMENT:
        // Symptoms usually appear after 3-5 days
        // 30% chance each day after day 3 to develop symptoms
        if (!hasSymptoms && daysInfected > 3 && rand.nextInt(100) < 30) {
            hasSymptoms = true; // Now showing symptoms
            if (hasSymptoms) {
                // Symptomatic infected are RED
                this.color = Color.RED;
            }
        }

        // QUARANTINE UPDATE:
        // If quarantined, count down the days
        if (isQuarantined) {
            quarantineDaysLeft--;
            // When quarantine ends, they can move again
            if (quarantineDaysLeft <= 0) {
                isQuarantined = false;
            }
        }
    }

    /**
     * IS CONTAGIOUS - Check if this person can spread the disease
     * @return true if still contagious, false if no longer contagious
     */
    public boolean isContagious() {
        // People are contagious for 14 days after infection
        return daysInfected < 14;
    }

    /**
     * DRAW - How to draw an Infected person on the grid
     * Overrides the Person draw method
     * @param g The Graphics object for drawing
     */
    @Override
    public void draw(Graphics g) {
        // Draw the person as a colored square
        g.setColor(color);
        g.fillRect(col * size, row * size, size - 1, size - 1);

        // DRAW BELIEF BORDER:
        if (belief.equals("BELIEVER")) {
            g.setColor(Color.BLUE);
            g.drawRect(col * size, row * size, size - 1, size - 1);
        } else {
            g.setColor(Color.RED);
            g.drawRect(col * size, row * size, size - 1, size - 1);
        }

        // DRAW STATUS SYMBOLS:
        g.setColor(Color.WHITE);

        // Show days infected as a number (1, 2, 3, etc.)
        // This helps track how long they've been sick
        g.drawString(String.valueOf(daysInfected), col * size + 5, row * size + 15);

        if (isQuarantined) {
            // "Q" means quarantined - shown next to days
            g.drawString("Q", col * size + 12, row * size + 15);
        }
    }
}