// Final Project for CS 142: Contagion Simulation
// Codevid-19 Group: Daniel, Kenzuki, Helen, Kushal

import java.util.Scanner;

public class ContagionSimulationMain {


    public static void main (String args[]) {

        // Intro for our Project
        System.out.println("Welcome to Codevid-19 final project for CS 142");
        System.out.println("Our project will focus on simulating a disease spread");
        System.out.println("The user will be able to control how deadly and contagious the disease is along with the population for the simulation");

        // Create Scanner for user input
        Scanner input = new Scanner(System.in);
        System.out.print("Name of your disease? "); // User can name their disease
        String userDiseaseName = input.nextLine();

        String userInput = "";

        while (!userInput.equals("quit")) {

            boolean validAnswerDeadly = false;

            while (!validAnswerDeadly) {
                System.out.println("How deadly do you want " + userDiseaseName + "? (Enter a percentage 1 - 100). Type \"quit\" to stop.");
                String userDiseaseDeadly = input.nextLine();
                int deadlyPercentage;
                try {
                    deadlyPercentage = Integer.parseInt(userDiseaseDeadly);
                    System.out.println("Deadliness is set to " + deadlyPercentage + "%");
                    validAnswerDeadly = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input. Please enter a whole number");
                }
            }

            boolean validAnswerContagious = false;

            while (!validAnswerContagious) {
                System.out.println("How contagious do you want " + userDiseaseName + "? (Enter a percentage 1 - 100). Type \"quit\" to stop.");
                String userDiseaseContagious = input.nextLine();
                int infectiousPercentage;
                try {
                    infectiousPercentage = Integer.parseInt(userDiseaseContagious);
                    System.out.println("How contagious is set to " + infectiousPercentage + "%");
                    validAnswerContagious = true;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Input. Please enter a whole number");
                }

            }
        }

    }

}
