// Person class extends Entity, represents a default person

import java.awt.*;

public class Person extends Entity {

    private int x; // X position
    private int y; // Y position
    private boolean male; // boolean if male or not
    private int age // persons age (baby, child, teen, adult, or elder);
    private boolean believer // boolean if person believes in the disease or not;
    private Color color; // Color of person
    private String status; // Status of person (healthy, infected, infected with symptoms, recovered, dead)

    // Constructor
    public person (int x, int y) {
        this.x = x;
        this.y = y;
        color;
        status;
        believer;
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
        this.x = newX;
        this.y = newY;
    }
    public boolean isMale(){
        return male;
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


