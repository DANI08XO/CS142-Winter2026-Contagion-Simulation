
// Disease class for Contagion Simulation

public class Disease {

    private String name;
    private int deadly;
    private int contagious;

    // Constructor
    public Disease(String name, int deadly, int contagious) {

        this.name = name;
        this.deadly = deadly;
        this.contagious = contagious;

    }

    // Return name;
    public String getName() {
        return name;
    }

    // Return how deadly
    public int getDeadly() {
        return deadly;
    }

    // Return how contagious
    public int getContagious() {
        return contagious;
    }

}
