
// Contagion Model for Contagion Simulation main

public class ContagionModel {

    private final String[] groupNames = {"baby", "child", "teen", "adult", "elder"}; // String array for groups

    private int populationSize;

    private int[] groupPercent;      // percent of total population per group
    private int[] groupMalePercent;  // percent of each group that are male
    private int vaccinatedPercent;   // percent of total population vaccinated

    // Constructor
    public ContagionModel(int populationSize, int[] groupPercent, int[] groupMalePercent, int vaccinatedPercent) {
        this.populationSize = populationSize;
        this.groupPercent = groupPercent;
        this.groupMalePercent = groupMalePercent;
        this.vaccinatedPercent = vaccinatedPercent;
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

}