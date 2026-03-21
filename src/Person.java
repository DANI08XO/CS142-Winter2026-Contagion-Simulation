

// Person class extends Entity, represents a default person
import java.util.*;
import java.awt.*;

/**
 * Person class - Level 2 of inheritance, extends Entity.
 * Represents a human individual in the simulation with all their attributes
 * and behaviors. This class adds human characteristics to the basic Entity,
 * including demographics, health status, and movement capabilities.
 *
 * <p>Person is the parent class for all specific person types:
 * Healthy, Infected, Recovered, and Deceased.</p>
 *
 * @author Codevid-19 Team
 * @version 1.0
 */
public class Person extends Entity {

    // ============ PERSON ATTRIBUTES ============
    /** Gender of the person - "MALE" or "FEMALE" */
    public String sex;

    /** Age group - "baby", "child", "teen", "adult", "elder" */
    public String ageGroup;

    /** Belief status - "BELIEVER" or "NON-BELIEVER" (affects cautiousness) */
    public String belief;

    /** Health status - "healthy", "infected", "recovered", "deceased" */
    public String status;

    /** Current color of the person (changes based on status) */
    public Color color;

    // ============ HEALTH STATUS FLAGS ============
    /** True if the person has been vaccinated */
    public boolean isVaccinated;

    /** True if the person is in quarantine (stays home) */
    public boolean isQuarantined;

    /** Number of days remaining in quarantine */
    public int quarantineDaysLeft;

    /** Number of days the person has been infected (0 if healthy) */
    public int daysInfected;

    /** True if the infected person is showing symptoms */
    public boolean hasSymptoms;

    /** Random number generator for chance-based decisions */
    public Random rand;

    /**
     * Constructor - Creates a new Person with specified attributes.
     * Initializes all health status flags to default values (healthy, not vaccinated,
     * not quarantined, no symptoms).
     *
     * @param row Starting row position on the grid
     * @param col Starting column position on the grid
     * @param sex "MALE" or "FEMALE"
     * @param ageGroup "baby", "child", "teen", "adult", or "elder"
     * @param belief "BELIEVER" or "NON-BELIEVER"
     */
    public Person(int row, int col, String sex, String ageGroup, String belief) {
        super(row, col);           // Entity sets this.row, this.col, this.size
        this.sex = sex;
        this.ageGroup = ageGroup;
        this.belief = belief;
        this.color = Color.WHITE;
        this.status = "healthy";
        this.isVaccinated = false;
        this.isQuarantined = false;
        this.quarantineDaysLeft = 0;
        this.daysInfected = 0;
        this.hasSymptoms = false;
        this.rand = new Random();
    }

    // ============ GETTER METHODS ============

    /**
     * Returns the current color of the person.
     * Color changes based on health status and symptoms.
     *
     * @return The Color object representing the person's current color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the current health status of the person.
     *
     * @return "healthy", "infected", "recovered", or "deceased"
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the person's location on the grid.
     *
     * @param newX New row position
     * @param newY New column position
     */
    public void SetLocation(int newX, int newY){
        this.row = newX;
        this.col = newY;
    }

    /**
     * Checks if the person is male.
     *
     * @return true if sex is "MALE", false if "FEMALE"
     */
    public boolean isMale(){
        return sex.equals("male");
    }

    /**
     * Returns whether this person is vaccinated.
     *
     * @return true if vaccinated, false otherwise
     */
    public boolean isVaccinated() {
        return isVaccinated;
    }

    // ============ BEHAVIOR METHODS ============

    /**
     * Moves the person to an adjacent cell in a random direction.
     * Movement is random - can move up, down, left, right, or stay in place.
     * The position is clamped to stay within grid boundaries.
     *
     * @param gridRows Total number of rows in the grid
     * @param gridCols Total number of columns in the grid
     */
    public void move(int gridRows, int gridCols) {
        int[] dirs = {-1, 0, 1};
        row += dirs[(int)(Math.random() * 3)];
        col += dirs[(int)(Math.random() * 3)];
        // Clamp to grid boundaries
        row = Math.max(0, Math.min(gridRows - 1, row));
        col = Math.max(0, Math.min(gridCols - 1, col));
    }

}