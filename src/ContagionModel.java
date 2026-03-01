
// Contagion Model for Contagion Simulation main

public class ContagionModel {

    private int populationSize;
    private int babyAmount;
    private int babyMaleAmount;
    private int childAmount;
    private int childMaleAmount;
    private int teenAmount;
    private int teenMaleAmount;
    private int adultAmount;
    private int adultMaleAmount;
    private int elderAmount;
    private int elderMaleAmount;

    // Constructor
    public ContagionModel(PopulationSettings population) {

        populationSize = population.getPopulationSize();

        babyAmount = population.getBabyPercent();
        babyMaleAmount = population.getBabyMalePercent();
        childAmount = population.getChildPercent();
        childMaleAmount = population.getChildMalePercent();
        teenAmount = population.getTeenPercent();
        teenMaleAmount = population.getAdultMalePercent();
        adultAmount = population.getAdultPercent();
        adultMaleAmount = population.getAdultMalePercent();
        elderAmount = population.getElderPercent();
        elderMaleAmount = population.getElderMalePercent();
    }

    // Math for how many people in the population a certain group (baby, child, teen, adult, elder)
    private int amountInPopulation(int percent) {
        return (populationSize * percent) / 100;
    }


    // Math for how many people in the group (baby, child, teen, adult, elder) are males
    // We can find women amount by subtracting number of people in group - males
    private int malesInGroup(int group, int males) {
        return (group * males) / 100;
    }

    // Math for how many people in the population a certain group (baby, child, teen, adult, elder) are baby
    public int getBabyCount() {
        return amountInPopulation(babyAmount);
    }

    // Math for how many people in the group (baby, child, teen, adult, elder) are baby males
    public int babyCountMale() {
        return malesInGroup(getBabyCount(), babyMaleAmount);
    }
    // Math for how many people in the population a certain group (baby, child, teen, adult, elder) are child
    public int getChildCount() {
        return amountInPopulation(childAmount);
    }
    // Math for how many people in the group (baby, child, teen, adult, elder) are child males
    public int childCountMale() {
        return malesInGroup(getChildCount(), childMaleAmount);
    }
    // Math for how many people in the population a certain group (baby, child, teen, adult, elder) are teen
    public int getTeenCount() {
        return amountInPopulation(teenAmount);
    }
    // Math for how many people in the group (baby, child, teen, adult, elder) are teen males
    public int teenCountMale() {
        return malesInGroup(getTeenCount(), teenMaleAmount);
    }
    // Math for how many people in the population a certain group (baby, child, teen, adult, elder) are adult
    public int getAdultCount() {
        return amountInPopulation(adultAmount);
    }
    // Math for how many people in the group (baby, child, teen, adult, elder) are adult males
    public int adultCountMale() {
        return malesInGroup(getAdultCount(), adultMaleAmount);
    }
    // Math for how many people in the population a certain group (baby, child, teen, adult, elder) are elder.
    public int getElderCount() {
        return amountInPopulation(elderAmount);
    }
    // Math for how many people in the group (baby, child, teen, adult, elder) are elder males
    public int elderCountMale() {
        return malesInGroup(getElderCount(), elderMaleAmount);
    }
    // Math for total number of males in the population
    public int getTotalMaleCount() {
        return babyCountMale()
                + childCountMale()
                + teenCountMale()
                + adultCountMale()
                + elderCountMale();
    }
}