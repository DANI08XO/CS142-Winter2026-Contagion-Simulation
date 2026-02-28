
/**
 * DISEASE CLASS - Stores all information about the disease
 * This is a simple data holder class (no inheritance needed)
 * Created based on user input in the main class
 */

public class Disease {

    public String name;        // Name of the disease (user can name it anything)
    public int deadliness;      // How deadly: 1-100 (percent chance of death)
    public int contagiousness;  // How contagious: 1-100 (base transmission rate)

    /**
     * CONSTRUCTOR - Creates a new Disease
     *
     * @param name       The name the user gave the disease
     * @param deadly     Deadliness percentage (1-100)
     * @param contagious Contagiousness percentage (1-100)
     */
    public Disease(String name, int deadly, int contagious) {
        this.name = name;
        this.deadliness = deadly;
        this.contagiousness = contagious;
    }

    // Return name;
    public String getName() {
        return name;
    }

    // Return how deadly
    public int getDeadly() {
        return deadliness;
    }

    // Return how contagious
    public int getContagious() {
        return contagiousness;
    }


}

