

// Contagion Model for Contagion Simulation main

/**
 * ContagionModel class - Tracks all statistical data for the simulation.
 * This class maintains counts of healthy, infected, recovered, deceased, and vaccinated
 * individuals for each age group. It provides methods to update these counts as the
 * simulation progresses and to query the current state.
 *
 * @author Codevid-19 Team
 * @version 1.0
 */
public class ContagionModel {

    /** Array of age group names in order: baby, child, teen, adult, elder */
    private final String[] groupNames = {"baby", "child", "teen", "adult", "elder"}; // String array for groups

    /** Total population size */
    private int populationSize;

    /** Array of group percentages (what % of population is in each age group) */
    private int[] groupPercent;      // percent of total population per group

    /** Array of male percentages (what % of each age group is male) */
    private int[] groupMalePercent;  // percent of each group that are male

    /** Array counting vaccinated individuals per age group */
    private int[] vaccinatedCount; // Count vaccinated people

    /** Array counting healthy (non-vaccinated) individuals per age group */
    private int[] healthyCount; // Count healthy people

    /** Array counting infected individuals per age group */
    private int[] infectedCount; // Count infected people

    /** Array counting deceased individuals per age group */
    private int[] deceasedCount; // Count deceased people

    /** Array counting recovered individuals per age group */
    private int[] recoveredCount; // Count recovered people

    /** Overall vaccination percentage for the entire population */
    private int vaccinatedPercent;   // percent of total population vaccinated

    /**
     * Gets the number of healthy individuals in a specific age group.
     *
     * @param i The index of the age group (0=baby, 1=child, 2=teen, 3=adult, 4=elder)
     * @return The count of healthy (non-vaccinated) people in that group
     */
    //methods that return the number of that kind of people (e.g. getHealthyCount returns number of health people)
    public int getHealthyCount(int i)    { return healthyCount[i]; }

    /**
     * Gets the number of infected individuals in a specific age group.
     *
     * @param i The index of the age group
     * @return The count of infected people in that group
     */
    public int getInfectedCount(int i)   { return infectedCount[i]; }

    /**
     * Gets the number of recovered individuals in a specific age group.
     *
     * @param i The index of the age group
     * @return The count of recovered people in that group
     */
    public int getRecoveredCount(int i)  { return recoveredCount[i]; }

    /**
     * Gets the number of deceased individuals in a specific age group.
     *
     * @param i The index of the age group
     * @return The count of deceased people in that group
     */
    public int getDeceasedCount(int i)   { return deceasedCount[i]; }

    /**
     * Gets the number of vaccinated individuals in a specific age group.
     *
     * @param i The index of the age group
     * @return The count of vaccinated people in that group
     */
    public int getVaccinatedCount(int i) { return vaccinatedCount[i]; }

    /**
     * Constructor - Initializes the model with population settings.
     * Calculates the initial distribution of people across age groups,
     * accounting for vaccination percentages.
     *
     * @param settings The PopulationSettings object containing all demographic data
     */
    // Constructor
    public ContagionModel(PopulationSettings settings) {

        //uses the population settings and takes the size and vaccinated percentage
        this.populationSize = settings.getPopulationSize();
        this.vaccinatedPercent = settings.getVaccinatedPercent();

        // Get group percentages from settings
        this.groupPercent = new int[groupNames.length];
        this.groupMalePercent = new int[groupNames.length];

        for (int i = 0; i < groupNames.length; i++) {
            groupPercent[i] = settings.getGroupPercent(i);
            groupMalePercent[i] = settings.getGroupMalePercent(i);
        }

        //creating lists of the all the ages (length 5 in this case)
        int[] groupCount = new int[groupNames.length];
        int[] maleCount = new int[groupNames.length];

        vaccinatedCount = new int[groupNames.length];
        healthyCount = new int[groupNames.length];
        infectedCount = new int[groupNames.length];
        deceasedCount = new int[groupNames.length];
        recoveredCount = new int[groupNames.length];

        for (int i = 0; i < groupNames.length; i++) {

            //gets the amount of males and age groups based on the percentages and population size given
            groupCount[i] = (int) Math.round(populationSize * settings.getGroupPercent(i) / 100.0); // How many people in group
            maleCount[i] = (int) Math.round(groupCount[i] * settings.getGroupMalePercent(i) / 100.0); // How many males in group
            //same thing for vaccinated people
            vaccinatedCount[i] = (int) Math.round(groupCount[i] * vaccinatedPercent / 100.0);
            //healthy non-vaccinated people: total amount of people minus vaccinated already
            healthyCount[i] = groupCount[i] - vaccinatedCount[i];

            infectedCount[i] = 0; // Start infected at 0
            deceasedCount[i] = 0; // Start deceased at 0
            recoveredCount[i] = 0; // Start recovered at 0
        }

    }

    /**
     * Updates the model when new infections occur.
     * Transfers individuals from healthy count to infected count for the specified group.
     *
     * @param groupName The name of the age group ("baby", "child", "teen", "adult", "elder")
     * @param numInfected The number of new infections in that group
     */
    // Method to infect a group, updates the amount of infected people
    public void infectGroup (String groupName, int numInfected) {

        int index = getGroupIndex(groupName);

        if (numInfected > healthyCount[index]) { numInfected = healthyCount[index]; }

        infectedCount[index] += numInfected;
        healthyCount[index] -= numInfected;

    }

    /**
     * Updates the model when individuals recover from infection.
     * Transfers individuals from infected count to recovered count for the specified group.
     *
     * @param groupName The name of the age group
     * @param numRecovered The number of recoveries in that group
     */
    // Method to recover group, updates the amount of recovered people
    public void recoveredGroup (String groupName, int numRecovered) {

        int index = getGroupIndex(groupName);

        if (numRecovered > infectedCount[index]) { numRecovered = infectedCount[index]; }

        recoveredCount[index] += numRecovered;
        infectedCount[index] -= numRecovered;

    }

    /**
     * Updates the model when individuals die from the disease.
     * Transfers individuals from infected count to deceased count for the specified group.
     *
     * @param groupName The name of the age group
     * @param numDeceased The number of deaths in that group
     */
    //Method for death in group, updates the number of deceased people
    public void deceasedGroup (String groupName, int numDeceased) {

        int index = getGroupIndex(groupName);

        if (numDeceased > infectedCount[index]) { numDeceased = infectedCount[index]; }

        deceasedCount[index] += numDeceased;
        infectedCount[index] -= numDeceased;

    }

    /**
     * Gets the array index for a given group name.
     *
     * @param groupName The name of the age group ("baby", "child", "teen", "adult", "elder")
     * @return The index (0-4) corresponding to that group
     * @throws IllegalArgumentException if the group name is not recognized
     */
    // Get index of group in the groupNames list
    public int getGroupIndex(String groupName) {
        for (int i = 0; i < groupNames.length; i++) {
            if (groupNames[i].equalsIgnoreCase(groupName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid group name: " + groupName);
    }

    /**
     * Gets the total number of people in a specific age group.
     *
     * @param index The age group index
     * @return The total count of people in that group
     */
    // Total people in a group
    public int getGroupCount(int index) {
        return (int) Math.round(populationSize * groupPercent[index] / 100.0);
    }

    /**
     * Gets the number of males in a specific age group.
     *
     * @param index The age group index
     * @return The count of males in that group
     */
    // Male people in a group
    public int getGroupMaleCount(int index) {
        return (int) Math.round(getGroupCount(index) * groupMalePercent[index] / 100.0);
    }

    /**
     * Gets the number of females in a specific age group.
     *
     * @param index The age group index
     * @return The count of females in that group
     */
    // Female people in a group
    public int getGroupFemaleCount(int index) {
        return getGroupCount(index) - getGroupMaleCount(index);
    }

    /**
     * Gets the total number of vaccinated individuals across all age groups.
     *
     * @return The total vaccinated count
     */
    // Total vaccinated people
    public int getTotalVaccinated() {
        return (int) Math.round(populationSize * vaccinatedPercent / 100.0);
    }

    /**
     * Calculates the distribution of vaccinated individuals across age groups.
     * Vaccinated people are distributed proportionally to group sizes.
     *
     * @return An array of vaccinated counts per age group
     */
    // Vaccinated per group (proportional to group size)
    public int[] getVaccinatedPerGroup() {
        int totalVaccinated = getTotalVaccinated();
        int[] vaccinatedPerGroup = new int[groupNames.length];

        for (int i = 0; i < groupNames.length; i++) {
            vaccinatedPerGroup[i] = (int) Math.round(getGroupCount(i) / (double) populationSize * totalVaccinated);
        }
        return vaccinatedPerGroup;
    }

    /**
     * Gets the number of vaccinated males in a specific age group.
     *
     * @param index The age group index
     * @return The count of vaccinated males in that group
     */
    // Optional: Vaccinated males per group
    public int getVaccinatedMales(int index) {
        return (int) Math.round(getGroupMaleCount(index) / (double) getGroupCount(index) * getVaccinatedPerGroup()[index]);
    }

    /**
     * Gets the number of vaccinated females in a specific age group.
     *
     * @param index The age group index
     * @return The count of vaccinated females in that group
     */
    // Optional: Vaccinated females per group
    public int getVaccinatedFemales(int index) {
        return getVaccinatedPerGroup()[index] - getVaccinatedMales(index);
    }

}