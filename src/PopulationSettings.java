
// Population Settings class for Contagion Simulation

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
