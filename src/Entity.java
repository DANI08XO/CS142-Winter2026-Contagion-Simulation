import java.awt.Color;
import java.awt.Graphics;

/**
 * ENTITY CLASS - Level 1 of inheritance
 * This is the base class for everything in our simulation.
 * Think of it as the "grandparent" of all objects.
 * Every person in our simulation is an Entity first!
 */

public class Entity {
    // PUBLIC VARIABLES - Making them public makes them easy to access
    public int row;        // Which row in the grid this entity is on (like Y coordinate)
    public int col;        // Which column in the grid this entity is on (like X coordinate)
    public int size;       // How big to draw this entity (all entities are 20x20 pixels)
    public Color color;    // What color to draw this entity

    /**
     * CONSTRUCTOR - Runs when we create a new Entity
     * @param row The starting row position
     * @param col The starting column position
     */

    public Entity(int row, int col) {
        this.row = row;           // 'this.row' means the variable above, 'row' is the parameter
        this.col = col;           // Set the column position
        this.size = 20;           // Each grid cell is 20x20 pixels
        this.color = Color.WHITE; // Default color is white (will be changed by subclasses)
    }

    /**
     * DRAW method - How to draw this entity on the screen
     * @param g The Graphics object used for drawing (provided by Java)
     */
    public void draw(Graphics g) {
        g.setColor(color);                                    // Set the drawing color
        g.fillRect(col * size, row * size, size - 1, size - 1); // Draw a filled square
        // col * size converts grid position to pixel position
        // size - 1 leaves a tiny gap between grid cells so we can see the grid lines
    }
}