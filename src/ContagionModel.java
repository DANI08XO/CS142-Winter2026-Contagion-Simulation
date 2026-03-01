
// Contagion Model for Contagion Simulation main

public class ContagionModel {

    private int populationSize;
    private int babyAmount;
    private int babyMaleAmount;


    // Constructor
    public ContagionModel (PopulationSettings population) {

        populationSize = population.getPopulationSize();

        babyAmount = population.getBabyPercent();
        babyMaleAmount = population.getBabyMalePercent();

        teenAmount = population.getTeenPercent();
        teenMaleAmount = population.getAdultMalePercent();

    }

    // Math for how many people in the population a certain group (baby, child, teen, adult, elder)
    private int amountInPopulation (int percent) {
        return populationSize * (percent/100);
    }


    // Math for how many people in the group (baby, child, teen, adult, elder) are males
    // We can find women amount by subtracting number of people in group - males
    private int malesInGroup (int group, int males) {
        return group * (males/100);
    }

}
