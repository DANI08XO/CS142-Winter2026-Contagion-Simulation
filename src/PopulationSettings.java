

/**
 * PopulationSettings class - Stores all demographic data for the simulation.
 * This class holds the population size, age distribution percentages,
 * gender ratios for each age group, and vaccination rate.
 *
 * @author Codevid-19 Team
 * @version 1.0
 */
public class PopulationSettings {
    // Private variables - data is encapsulated
    private int totalSize;
    private int babyPercent;
    private int childPercent;
    private int teenPercent;
    private int adultPercent;
    private int elderPercent;
    private int babyMalePercent;
    private int childMalePercent;
    private int teenMalePercent;
    private int adultMalePercent;
    private int elderMalePercent;
    private int vaccinatedPercent;

    /**
     * Constructor - Creates a new PopulationSettings object.
     *
     * @param size The total population size
     */
    public PopulationSettings(int size) {
        this.totalSize = size;

        // Initialize with default values
        this.babyPercent = 0;
        this.childPercent = 0;
        this.teenPercent = 0;
        this.adultPercent = 0;
        this.elderPercent = 0;
        this.babyMalePercent = 50;
        this.childMalePercent = 50;
        this.teenMalePercent = 50;
        this.adultMalePercent = 50;
        this.elderMalePercent = 50;
        this.vaccinatedPercent = 0;
    }

    // ============ GETTER METHODS ============

    /** @return The total population size */
    public int getPopulationSize() { return totalSize; }

    /** @return Percentage of population that are babies */
    public int getBabyPercent() { return babyPercent; }

    /** @return Percentage of population that are children */
    public int getChildPercent() { return childPercent; }

    /** @return Percentage of population that are teens */
    public int getTeenPercent() { return teenPercent; }

    /** @return Percentage of population that are adults */
    public int getAdultPercent() { return adultPercent; }

    /** @return Percentage of population that are elders */
    public int getElderPercent() { return elderPercent; }

    /** @return Percentage of babies that are male */
    public int getBabyMalePercent() { return babyMalePercent; }

    /** @return Percentage of children that are male */
    public int getChildMalePercent() { return childMalePercent; }

    /** @return Percentage of teens that are male */
    public int getTeenMalePercent() { return teenMalePercent; }

    /** @return Percentage of adults that are male */
    public int getAdultMalePercent() { return adultMalePercent; }

    /** @return Percentage of elders that are male */
    public int getElderMalePercent() { return elderMalePercent; }

    /** @return Percentage of total population vaccinated */
    public int getVaccinatedPercent() { return vaccinatedPercent; }

    /**
     * Gets the percentage for a specific age group by index.
     *
     * @param index 0=baby, 1=child, 2=teen, 3=adult, 4=elder
     * @return The percentage for that age group
     */
    public int getGroupPercent(int index) {
        switch(index) {
            case 0: return babyPercent;
            case 1: return childPercent;
            case 2: return teenPercent;
            case 3: return adultPercent;
            case 4: return elderPercent;
            default: return 0;
        }
    }

    /**
     * Gets the male percentage for a specific age group by index.
     *
     * @param index 0=baby, 1=child, 2=teen, 3=adult, 4=elder
     * @return The male percentage for that age group
     */
    public int getGroupMalePercent(int index) {
        switch(index) {
            case 0: return babyMalePercent;
            case 1: return childMalePercent;
            case 2: return teenMalePercent;
            case 3: return adultMalePercent;
            case 4: return elderMalePercent;
            default: return 50;
        }
    }

    // ============ SETTER METHODS ============

    /** @param percent Percentage of population that are babies */
    public void setBabyPercent(int percent) { this.babyPercent = percent; }

    /** @param percent Percentage of population that are children */
    public void setChildPercent(int percent) { this.childPercent = percent; }

    /** @param percent Percentage of population that are teens */
    public void setTeenPercent(int percent) { this.teenPercent = percent; }

    /** @param percent Percentage of population that are adults */
    public void setAdultPercent(int percent) { this.adultPercent = percent; }

    /** @param percent Percentage of population that are elders */
    public void setElderPercent(int percent) { this.elderPercent = percent; }

    /** @param percent Percentage of babies that are male */
    public void setBabyMalePercent(int percent) { this.babyMalePercent = percent; }

    /** @param percent Percentage of children that are male */
    public void setChildMalePercent(int percent) { this.childMalePercent = percent; }

    /** @param percent Percentage of teens that are male */
    public void setTeenMalePercent(int percent) { this.teenMalePercent = percent; }

    /** @param percent Percentage of adults that are male */
    public void setAdultMalePercent(int percent) { this.adultMalePercent = percent; }

    /** @param percent Percentage of elders that are male */
    public void setElderMalePercent(int percent) { this.elderMalePercent = percent; }

    /** @param percent Percentage of total population vaccinated */
    public void setVaccinatedPercent(int percent) { this.vaccinatedPercent = percent; }

    /**
     * Sets the percentage for a specific age group by index.
     *
     * @param index 0=baby, 1=child, 2=teen, 3=adult, 4=elder
     * @param percent The percentage to set
     */
    public void setGroupPercent(int index, int percent) {
        switch(index) {
            case 0: babyPercent = percent; break;
            case 1: childPercent = percent; break;
            case 2: teenPercent = percent; break;
            case 3: adultPercent = percent; break;
            case 4: elderPercent = percent; break;
        }
    }

    /**
     * Sets the male percentage for a specific age group by index.
     *
     * @param index 0=baby, 1=child, 2=teen, 3=adult, 4=elder
     * @param percent The male percentage to set
     */
    public void setGroupMalePercent(int index, int percent) {
        switch(index) {
            case 0: babyMalePercent = percent; break;
            case 1: childMalePercent = percent; break;
            case 2: teenMalePercent = percent; break;
            case 3: adultMalePercent = percent; break;
            case 4: elderMalePercent = percent; break;
        }
    }

    /**
     * Returns the names of age groups in order.
     *
     * @return Array of group names
     */
    public String[] getGroupNames() {
        return new String[]{"babies", "children", "adolescents", "adults", "elders"};
    }
}