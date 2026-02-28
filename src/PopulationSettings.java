
/*
 * POPULATION SETTINGS CLASS - Stores all demographic information
 * This is a simple data holder class
 * Created based on user input in the main class
 */

public class PopulationSettings {

    private int populationSize;

    private int babyPercent;
    private int babyMalePercent;

    private int childPercent;
    private int childMalePercent;

    private int teenPercent;
    private int teenMalePercent;

    private int adultPercent;
    private int adultMalePercent;

    private int elderPercent;
    private int elderMalePercent;

    // Constructor
    public PopulationSettings (int populationSize) {
        this.populationSize = populationSize;
    }

    // Return baby percentage in population
    public int getBabyPercent() { return babyPercent;}

    // Return percentage of baby males
    public int getBabyMalePercent() { return babyMalePercent; }

    // Return children percentage in population
    public int getChildPercent() { return childPercent; }

    // Return percentage of children males
    public int getChildMalePercent() { return childMalePercent; }

    // Return teen percentage in population
    public int getTeenPercent() { return teenPercent; }

    // Return percentage of teen males
    public int getTeenMalePercent() { return teenMalePercent; }

    // Return adult percentage in population
    public int getAdultPercent() { return adultPercent; }

    // Return percentage of adult males
    public int getAdultMalePercent() { return adultMalePercent; }

    // Return percentage of elders in population
    public int getElderPercent() { return elderPercent; }

    // Return percentage of male elders
    public int getElderMalePercent() { return elderMalePercent; }

    // Return population size
    public int getPopulationSize() { return populationSize; }

    // Set percentage of babies in population
    public void setBabyPercent(int babyPercent) { this.babyPercent = babyPercent; }

    // Set percentage of male babies in population
    public void setBabyMalePercent(int babyMalePercent) { this.babyMalePercent = babyMalePercent; }

    // Set percentage of children in population
    public void setChildPercent(int childPercent) { this.childPercent = childPercent; }

    // Set percentage of male children in population
    public void setChildMalePercent(int childMalePercent) { this.childMalePercent = childMalePercent; }

    // Set percentage of teens in population
    public void setTeenPercent(int teenPercent) { this.teenPercent = teenPercent; }

    // Set percentage of male teens in population
    public void setTeenMalePercent(int teenMalePercent) { this.teenMalePercent = teenMalePercent; }

    // Set percentage of adults in population
    public void setAdultPercent(int adultPercent) { this.adultPercent = adultPercent; }

    // Set percentage of adult males in population
    public void setAdultMalePercent(int adultMalePercent) { this.adultMalePercent = adultMalePercent; }

    // Set percentage of elders in population
    public void setElderPercent(int elderPercent) { this.elderPercent = elderPercent; }

    // Set percentage of male elders in population
    public void setElderMalePercent(int elderMalePercent) { this.elderMalePercent = elderMalePercent; }

}


/*
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

/*
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
*/
