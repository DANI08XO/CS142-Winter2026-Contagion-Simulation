
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * PERSON CLASS - Level 2 of inheritance
 * This class inherits from Entity
 * Person adds human-like attributes to the basic Entity
 * This is the "parent" class for all specific person types
 */
public class Person extends Entity {
    // SIMPLE STRING VARIABLES for person attributes
    public String sex;           // "MALE" or "FEMALE"
    public String ageGroup;      // "CHILD", "TEEN", "ADULT", "ELDER" - age category
    public String belief;        // "BELIEVER" or "NONBELIEVER" - whether they believe in the disease

    // INFECTION RELATED VARIABLES - Track health status
    public int daysInfected;          // How many days this person has been infected (0 if healthy)
    public boolean hasSymptoms;       // true if showing symptoms, false if asymptomatic
    public boolean isQuarantined;     // true if staying home, false if moving freely
    public boolean isVaccinated;      // true if got vaccine, false if not
    public int quarantineDaysLeft;    // How many more days of quarantine remaining

    // RANDOM NUMBER GENERATOR - For making random decisions (movement, infection, etc.)
    public Random rand;

    /**
     * CONSTRUCTOR - Creates a new Person
     * @param row Starting row on grid
     * @param col Starting column on grid
     * @param sex Male or Female
     * @param ageGroup Child, Teen, Adult, Elder
     * @param belief Believer or Non-believer
     */
    public Person(int row, int col, String sex, String ageGroup, String belief) {
        // Call the parent constructor (Entity) first!
        super(row, col);

        // Set all the person-specific attributes
        this.sex = sex;
        this.ageGroup = ageGroup;
        this.belief = belief;

        // Initialize infection variables to default (healthy) values
        this.daysInfected = 0;
        this.hasSymptoms = false;
        this.isQuarantined = false;
        this.isVaccinated = false;
        this.quarantineDaysLeft = 0;

        // Create the random number generator
        this.rand = new Random();
    }

    /**
     * MOVE method - How this person moves around the grid
     * @param gridRows Total number of rows in the grid
     * @param gridCols Total number of columns in the grid
     */
    public void move(int gridRows, int gridCols) {
        // Quarantined people don't move at all (they stay home)
        if (isQuarantined) {
            return; // Exit the method early
        }

        // BELIEF AFFECTS MOVEMENT:
        // Believers move less (30% chance to move) because they're more careful
        // Non-believers move more (70% chance to move) because they're carefree
        int moveChance = belief.equals("BELIEVER") ? 30 : 70;

        // Check if this person will move today
        // rand.nextInt(100) gives a number 0-99
        if (rand.nextInt(100) < moveChance) {
            // Try to move to a random adjacent cell (up, down, left, or right)
            int newRow = row;  // Start with current position
            int newCol = col;

            // Pick a random direction: 0=up, 1=down, 2=left, 3=right
            int direction = rand.nextInt(4);
            if (direction == 0) newRow = row - 1; // Up
            else if (direction == 1) newRow = row + 1; // Down
            else if (direction == 2) newCol = col - 1; // Left
            else if (direction == 3) newCol = col + 1; // Right

            // Check if new position is within the grid boundaries
            if (newRow >= 0 && newRow < gridRows && newCol >= 0 && newCol < gridCols) {
                // Valid move! Update position
                row = newRow;
                col = newCol;
            }
            // If move would go outside grid, just stay put
        }
    }

    /**
     * UPDATE INFECTION - This will be overridden by subclasses
     * Different types of people (Healthy, Infected, etc.) update differently
     * This is called POLYMORPHISM - same method name, different behavior
     */
    public void updateInfection() {
        // Base class does nothing - subclasses will override this
    }

    /**
     * QUARANTINE - Put this person in quarantine
     * @param days How many days to quarantine for
     */
    public void quarantine(int days) {
        isQuarantined = true;      // They're now quarantined
        quarantineDaysLeft = days; // Set how many days they must stay home
    }

    /**
     * VACCINATE - Give this person a vaccine
     */
    public void vaccinate() {
        isVaccinated = true; // They're now vaccinated
    }
}