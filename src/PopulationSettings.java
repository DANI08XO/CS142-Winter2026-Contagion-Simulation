/**
 * POPULATION SETTINGS CLASS - Stores all demographic information
 * This is a simple data holder class
 * Created based on user input in the main class
 */
public class PopulationSettings {
    // BASIC POPULATION INFO
    public int totalSize;  // Total number of people in simulation

    // AGE PERCENTAGES - What percentage of population is each age group
    public int babyPercent;    // 0-100%
    public int childPercent;   // 0-100%
    public int teenPercent;    // 0-100%
    public int adultPercent;   // 0-100%
    public int elderPercent;   // 0-100% (calculated as what's left)

    // GENDER PERCENTAGES for each age group
    // What percentage of each age group is male (rest are female)
    public int babyMalePercent;    // 0-100% male among babies
    public int childMalePercent;   // 0-100% male among children
    public int teenMalePercent;    // 0-100% male among teens
    public int adultMalePercent;   // 0-100% male among adults
    public int elderMalePercent;   // 0-100% male among elders

    // VACCINATION
    public int vaccinatedPercent;  // What percentage is vaccinated (0-100%)

    /**
     * CONSTRUCTOR - Creates new PopulationSettings
     * @param size Total population size
     */
    public PopulationSettings(int size) {
        this.totalSize = size;

        // Set default values (will be overwritten by user input)
        this.babyPercent = 20;
        this.childPercent = 20;
        this.teenPercent = 20;
        this.adultPercent = 20;
        this.elderPercent = 20;
        this.babyMalePercent = 50;
        this.childMalePercent = 50;
        this.teenMalePercent = 50;
        this.adultMalePercent = 50;
        this.elderMalePercent = 50;
        this.vaccinatedPercent = 0;
    }
}


