
// Contagion Model for Contagion Simulation main

//import java.math.;

public class ContagionModel {

    private final String[] groupNames = {"baby", "child", "teen", "adult", "elder"}; // String array for groups

    private int populationSize;

    private int[] groupPercent;      // percent of total population per group
    private int[] groupMalePercent;  // percent of each group that are male
    private int[] vaccinatedCount; // Count vaccinated people

    private int[] healthyCount; // Count healthy people
    private int[] infectedCount; // Count infected people
    private int[] deceasedCount; // Count deceased people
    private int[] recoveredCount; // Count recovered people

    private int vaccinatedPercent;   // percent of total population vaccinated

    // Constructor
    public ContagionModel(PopulationSettings settings) {

        this.populationSize = settings.getPopulationSize();
        this.vaccinatedPercent = settings.getVaccinatedPercent();

        groupCount = new int[groupNames.length];
        maleCount = new int[groupNames.length];

        vaccinatedCount = new int[groupNames.length];
        healthyCount = new int[groupNames.length];
        infectedCount = new int[groupNames.length];
        deceasedCount = new int[groupNames.length];
        recoveredCount = new int[groupNames.length];

        for (int i = 0; i < groupNames.length; i++) {

            groupCount[i] = (int) Math.round(populationSize * settings.getGroupPercent(i) / 100.0); // How many people in group
            maleCount[i] = (int) Math.round(groupCount[i] * settings.getGroupMalePercent(i) / 100.0); // How many males in group

            vaccinatedCount[i] = (int) Math.round(groupCount[i] * vaccinatedPercent / 100.0);

            healthyCount[i] = groupCount[i] - vaccinatedCount[i]; //count healthy = group count - vaccinated already

            infectedCount[i] = 0; // Start infected at 0
            deceasedCount[i] = 0; // Start deceased at 0
            recoveredCount[i] = 0; // Start recovered at 0
        }

    }

    // Method to infect a group
    public void infectGroup (String groupName, int numInfected) {

        int index = getGroupIndex(groupName);

        if (numInfected > healthyCount[index]) { numInfected = healthyCount[index]; }

        infectedCount[index] += numInfected;
        healthyCount[index] -= numInfected;

    }

    // Method to recover group
    public void recoveredGroup (String groupName, int numRecovered) {

        int index = getGroupIndex(groupName);

        if (numRecovered > infectedCount[index]) { numRecovered = infectedCount[index]; }

        recoveredCount[index] += numRecovered;
        infectedCount[index] -= numRecovered;

    }

    //Method for death in group
    public void deceasedGroup (String groupName, int numDeceased) {

        int index = getGroupIndex(groupName);

        if (numDeceased > infectedCount[index]) { numDeceased = infectedCount[index]; }

        deceasedCount[index] += numDeceased;
        infectedCount[index] -= numDeceased;

    }

    // Get index of group
    public int getGroupIndex(String groupName) {
        for (int i = 0; i < groupNames.length; i++) {
            if (groupNames[i].equalsIgnoreCase(groupName));
            return i;
        } else {
            throw new IllegalArgumentException("Invalid group name");
        }
    }

    // Total people in a group
    public int getGroupCount(int index) { return (int) Math.round(populationSize * groupPercent[index] / 100.0); }

    // Male people in a group
    public int getGroupMaleCount(int index) { return (int) Math.round(getGroupCount(index) * groupMalePercent[index] / 100.0); }

    // Female people in a group
    public int getGroupFemaleCount(int index) { return getGroupCount(index) - getGroupMaleCount(index); }

    // Total vaccinated people
    public int getTotalVaccinated() { return (int) Math.round(populationSize * vaccinatedPercent / 100.0); }

    // Vaccinated per group (proportional to group size)
    public int[] getVaccinatedPerGroup() {
        int totalVaccinated = getTotalVaccinated();
        int[] vaccinatedPerGroup = new int[groupNames.length];

        for (int i = 0; i < groupNames.length; i++) {
            vaccinatedPerGroup[i] = (int) Math.round(getGroupCount(i) / (double) populationSize * totalVaccinated);
        }
        return vaccinatedPerGroup;
    }

    // Optional: Vaccinated males per group
    public int getVaccinatedMales(int index) { return (int) Math.round(getGroupMaleCount(index) / (double) getGroupCount(index) * getVaccinatedPerGroup()[index]);}

    // Optional: Vaccinated females per group
    public int getVaccinatedFemales(int index) { return getVaccinatedPerGroup()[index] - getVaccinatedMales(index); }

    // Movement for population
    public int[] movement() {

    }

}