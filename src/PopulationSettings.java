
/*
 * POPULATION SETTINGS CLASS - Stores all demographic information
 * This is a simple data holder class
 * Created based on user input in the main class
 */

public class PopulationSettings {

    private int populationSize;

    private final String[] groupNames = {"baby", "child", "teen", "adult", "elder"}; //String array for groups

    private int[] groupPercent;      // percentage of total population per group
    private int[] groupMalePercent;  // percentage of each group that are male
    private int vaccinatedPercent;   // percentage of total population vaccinated

    // Constructor
    public PopulationSettings(int populationSize) {
        this.populationSize = populationSize;
        this.groupPercent = new int[groupNames.length];
        this.groupMalePercent = new int[groupNames.length];
    }


    // Getters
    public int getPopulationSize() {
        return populationSize;
    }

    public int getVaccinatedPercent() {
        return vaccinatedPercent;
    }

    public int getGroupPercent(int index) {
        if (index < 0 || index >= groupPercent.length) throw new IndexOutOfBoundsException("Invalid group index");
        return groupPercent[index];
    }

    public int getGroupMalePercent(int index) {
        if (index < 0 || index >= groupMalePercent.length) throw new IndexOutOfBoundsException("Invalid group index");
        return groupMalePercent[index];
    }

    public int getGroupPercent(String groupName) {
        for (int i = 0; i < groupNames.length; i++) {
            if (groupNames[i].equalsIgnoreCase(groupName)) return groupPercent[i];
        }
        throw new IllegalArgumentException("Unknown group: " + groupName);
    }

    public int getGroupMalePercent(String groupName) {
        for (int i = 0; i < groupNames.length; i++) {
            if (groupNames[i].equalsIgnoreCase(groupName)) return groupMalePercent[i];
        }
        throw new IllegalArgumentException("Unknown group: " + groupName);
    }

    public String[] getGroupNames() {
        return groupNames.clone(); // returns a copy to prevent external modification
    }

    // Setters

    public void setGroupPercent(int index, int percent) {
        if (index < 0 || index >= groupPercent.length) throw new IndexOutOfBoundsException("Invalid group index");
        groupPercent[index] = percent;
    }

    public void setGroupMalePercent(int index, int percent) {
        if (index < 0 || index >= groupMalePercent.length) throw new IndexOutOfBoundsException("Invalid group index");
        groupMalePercent[index] = percent;
    }

    public void setGroupPercent(String groupName, int percent) {
        for (int i = 0; i < groupNames.length; i++) {
            if (groupNames[i].equalsIgnoreCase(groupName)) {
                groupPercent[i] = percent;
                return;
            }
        }
        throw new IllegalArgumentException("Unknown group: " + groupName);
    }

    public void setGroupMalePercent(String groupName, int percent) {
        for (int i = 0; i < groupNames.length; i++) {
            if (groupNames[i].equalsIgnoreCase(groupName)) {
                groupMalePercent[i] = percent;
                return;
            }
        }
        throw new IllegalArgumentException("Unknown group: " + groupName);
    }

    public void setVaccinatedPercent(int percent) {
        this.vaccinatedPercent = percent;
    }

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
