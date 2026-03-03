// Person class extends Entity, represents a default person
import java.util.*;
import java.awt.*;

public class Person extends Entity {

    public String sex;          // "MALE" or "FEMALE"
    public String ageGroup;     // "baby", "child", "teen", "adult", "elder"
    public String belief;       // "BELIEVER" or "NON-BELIEVER"
    public String status;       // "healthy", "infected", "recovered", "deceased"
    public Color color;

    public boolean isVaccinated;
    public boolean isQuarantined;
    public int quarantineDaysLeft;
    public int daysInfected;
    public boolean hasSymptoms;
    public Random rand;

    // Constructor
    public Person(int row, int col, String sex, String ageGroup, String belief) {
        super(row, col);           // Entity sets this.row, this.col, this.size
        this.sex = sex;
        this.ageGroup = ageGroup;
        this.belief = belief;
        this.color = Color.WHITE;
        this.status = "healthy";
        this.isVaccinated = false;
        this.isQuarantined = false;
        this.quarantineDaysLeft = 0;
        this.daysInfected = 0;
        this.hasSymptoms = false;
    }




        // Return color of person
    public Color getColor() {
        return color;
    }

    // Return status of person
    public String getStatus() {
        return status;
    }
    public void SetLocation(int newX, int newY){
        this.row = newX;
        this.col = newY;
    }
    public boolean isMale(){
        return sex.equals("male");
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public void move(int gridRows, int gridCols) {
        int[] dirs = {-1, 0, 1};
        row += dirs[(int)(Math.random() * 3)];
        col += dirs[(int)(Math.random() * 3)];
        // Clamp to grid
        row = Math.max(0, Math.min(gridRows - 1, row));
        col = Math.max(0, Math.min(gridCols - 1, col));
    }

/*
    Public Entity(int x, int y)  ; // Constructor
    Public void setLocation(int x, int y) //sets the location of the entity
    Public Color getColor() //returns the color of the entity
    Public String getStatus() //returns the status (healthy, infected, recovered, deceased)
    Public int getAge() //returns the age of the entity
    Public boolean getBeliever() //returns if they believe in disease or not
    Public void recovery() //goes through the process of recovering from disease, changes color from sick to healthy color, chance to die based on age, changes non-believers to believers, takes 7 “days” to recover
    Public void postContact() // chooses a random number from 1-3, takes that many days to show symptoms, changes color slowly from healthy to sick, called only after they come in contact with an infected
    Public Direction getMove() // copy of getMove() in critters.java
*/

}


