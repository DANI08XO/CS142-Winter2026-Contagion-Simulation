
import java.awt.Color;
import java.awt.Graphics;

/**
 * RECOVERED CLASS - Level 3 of inheritance
 * This class inherits from Person
 * Represents a person who got sick but survived and is now immune
 * Color: BLUE
 */
public class Recovered extends Person {

    /**
     * CONSTRUCTOR - Creates a new Recovered person
     * @param row Starting row on grid
     * @param col Starting column on grid
     * @param sex Male or Female
     * @param ageGroup Child, Teen, Adult, Elder
     * @param belief Believer or Non-believer
     */
    public Recovered(int row, int col, String sex, String ageGroup, String belief) {
        // Call the Person constructor first!
        super(row, col, sex, ageGroup, belief);

        // Recovered people are BLUE
        this.color = Color.BLUE;
    }

    /**
     * HAS IMMUNITY - Check if this person is immune to reinfection
     * @return true (recovered people are immune in our simulation)
     */
    public boolean hasImmunity() {
        return true; // Once recovered, you're immune (simplified)
    }

    /**
     * DRAW - How to draw a Recovered person on the grid
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

        // Draw "R" for Recovered - so we know they survived
        g.setColor(Color.WHITE);
        g.drawString("R", col * size + 5, row * size + 15);
    }
}