

import java.awt.Color;
import java.awt.Graphics;

/**
 * DECEASED CLASS - Level 3 of inheritance
 * This class inherits from Person
 * Represents a person who died from the disease
 * Color: BLACK with a white X
 */
public class Deceased extends Person {

    /**
     * CONSTRUCTOR - Creates a new Deceased person
     * @param row Starting row on grid
     * @param col Starting column on grid
     * @param sex Male or Female
     * @param ageGroup Child, Teen, Adult, Elder
     * @param belief Believer or Non-believer
     */
    public Deceased(int row, int col, String sex, String ageGroup, String belief) {
        // Call the Person constructor first!
        super(row, col, sex, ageGroup, belief);

        // Deceased people are BLACK
        this.color = Color.BLACK;
    }

    /**
     * MOVE - Override the move method
     * Dead people don't move - they stay where they died
     * @param gridRows Total rows (unused)
     * @param gridCols Total columns (unused)
     */
    @Override
    public void move(int gridRows, int gridCols) {
        // Do nothing - dead people stay in place
        // This is different from living people who move around
    }

    /**
     * DRAW - How to draw a Deceased person on the grid
     * Overrides the Person draw method
     * @param g The Graphics object for drawing
     */
    @Override
    public void draw(Graphics g) {
        // Draw the person as a black square
        g.setColor(color);
        g.fillRect(col * size, row * size, size - 1, size - 1);

        // Draw a white X to show they're dead
        g.setColor(Color.WHITE);
        // Draw first diagonal line: top-left to bottom-right
        g.drawLine(col * size, row * size, (col * size) + size, (row * size) + size);
        // Draw second diagonal line: top-right to bottom-left
        g.drawLine((col * size) + size, row * size, col * size, (row * size) + size);
    }
}