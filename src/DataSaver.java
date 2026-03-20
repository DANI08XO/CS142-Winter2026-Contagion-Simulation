

import java.io.*;
import java.util.*;

/**
 * DataSaver class - Handles saving and loading data to/from text files.
 * Provides methods for saving simulation results and saving/loading parameters.
 *
 * @author Codevid-19 Team
 * @version 1.0
 */
public class DataSaver {

    /**
     * Saves a list of integer data to a text file with step-by-step format.
     * Used for saving simulation results over time.
     *
     * @param fileName The name of the file to save to
     * @param data A List of Integer values representing data points over time
     */
    public static void save (String fileName, List<Integer> data) {

        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < data.size(); i++) {
                writer.write("Step " + i + ": " + data.get(i) + "\n");
            }
            System.out.println("File saved!");
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    /**
     * Saves simulation parameters (settings) to a text file.
     * This method saves the disease name, deadliness, contagiousness,
     * population size, and all demographic percentages.
     *
     * @param fileName The name of the file to save to
     * @param diseaseName The name of the disease
     * @param deadly The deadliness percentage (1-100)
     * @param contagious The contagiousness percentage (1-100)
     * @param populationSize The total population size
     * @param settings The PopulationSettings object containing all demographic data
     */
    public static void saveParameters(String fileName, String diseaseName,
                                      int deadly, int contagious, int populationSize,
                                      PopulationSettings settings) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("DISEASE_NAME=" + diseaseName);
            writer.println("DEADLINESS=" + deadly);
            writer.println("CONTAGIOUS=" + contagious);
            writer.println("POPULATION=" + populationSize);
            writer.println("BABY_PERCENT=" + settings.getBabyPercent());
            writer.println("CHILD_PERCENT=" + settings.getChildPercent());
            writer.println("TEEN_PERCENT=" + settings.getTeenPercent());
            writer.println("ADULT_PERCENT=" + settings.getAdultPercent());
            writer.println("ELDER_PERCENT=" + settings.getElderPercent());
            writer.println("BABY_MALE=" + settings.getBabyMalePercent());
            writer.println("CHILD_MALE=" + settings.getChildMalePercent());
            writer.println("TEEN_MALE=" + settings.getTeenMalePercent());
            writer.println("ADULT_MALE=" + settings.getAdultMalePercent());
            writer.println("ELDER_MALE=" + settings.getElderMalePercent());
            writer.println("VACCINATED=" + settings.getVaccinatedPercent());
            System.out.println("✅ Parameters saved to " + fileName);
        } catch (IOException e) {
            System.err.println("❌ Error saving parameters: " + e.getMessage());
        }
    }

    /**
     * Loads simulation parameters from a previously saved text file.
     * Reads the key=value pairs and returns a ParametersData object
     * containing all the loaded settings.
     *
     * @param fileName The name of the file to load from
     * @return ParametersData object containing all loaded settings, or null if loading failed
     */
    public static ParametersData loadParameters(String fileName) {
        ParametersData data = new ParametersData();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;

                String key = parts[0].trim();
                String value = parts[1].trim();

                // Fill in the data object based on the key
                switch (key) {
                    case "DISEASE_NAME":
                        data.diseaseName = value;
                        break;
                    case "DEADLINESS":
                        data.deadly = Integer.parseInt(value);
                        break;
                    case "CONTAGIOUS":
                        data.contagious = Integer.parseInt(value);
                        break;
                    case "POPULATION":
                        data.populationSize = Integer.parseInt(value);
                        break;
                    case "BABY_PERCENT":
                        data.babyPercent = Integer.parseInt(value);
                        break;
                    case "CHILD_PERCENT":
                        data.childPercent = Integer.parseInt(value);
                        break;
                    case "TEEN_PERCENT":
                        data.teenPercent = Integer.parseInt(value);
                        break;
                    case "ADULT_PERCENT":
                        data.adultPercent = Integer.parseInt(value);
                        break;
                    case "ELDER_PERCENT":
                        data.elderPercent = Integer.parseInt(value);
                        break;
                    case "BABY_MALE":
                        data.babyMalePercent = Integer.parseInt(value);
                        break;
                    case "CHILD_MALE":
                        data.childMalePercent = Integer.parseInt(value);
                        break;
                    case "TEEN_MALE":
                        data.teenMalePercent = Integer.parseInt(value);
                        break;
                    case "ADULT_MALE":
                        data.adultMalePercent = Integer.parseInt(value);
                        break;
                    case "ELDER_MALE":
                        data.elderMalePercent = Integer.parseInt(value);
                        break;
                    case "VACCINATED":
                        data.vaccinatedPercent = Integer.parseInt(value);
                        break;
                    default:
                        System.out.println("Unknown key in file: " + key);
                }
            }
            System.out.println("✅ Parameters loaded from " + fileName);
        } catch (IOException e) {
            System.err.println("❌ Error loading parameters: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.err.println("❌ Error parsing number: " + e.getMessage());
            return null;
        }

        return data;
    }

    /**
     * ParametersData class - A simple data container for loaded parameters.
     * This inner class holds all the values read from a saved parameters file.
     */
    public static class ParametersData {
        /** The name of the disease */
        public String diseaseName = "";

        /** Deadliness percentage (1-100) */
        public int deadly = 5;

        /** Contagiousness percentage (1-100) */
        public int contagious = 30;

        /** Total population size */
        public int populationSize = 100;

        /** Percentage of population that are babies */
        public int babyPercent = 20;

        /** Percentage of population that are children */
        public int childPercent = 20;

        /** Percentage of population that are teens */
        public int teenPercent = 20;

        /** Percentage of population that are adults */
        public int adultPercent = 20;

        /** Percentage of population that are elders (calculated, not entered directly) */
        public int elderPercent = 20;

        /** Percentage of babies that are male */
        public int babyMalePercent = 50;

        /** Percentage of children that are male */
        public int childMalePercent = 50;

        /** Percentage of teens that are male */
        public int teenMalePercent = 50;

        /** Percentage of adults that are male */
        public int adultMalePercent = 50;

        /** Percentage of elders that are male */
        public int elderMalePercent = 50;

        /** Percentage of total population that is vaccinated */
        public int vaccinatedPercent = 0;

        /**
         * Returns a string representation of all loaded parameters.
         *
         * @return Formatted string with all parameters
         */
        @Override
        public String toString() {
            return "Disease: " + diseaseName + "\n" +
                    "Deadliness: " + deadly + "%\n" +
                    "Contagiousness: " + contagious + "%\n" +
                    "Population: " + populationSize + "\n" +
                    "Babies: " + babyPercent + "% (Male: " + babyMalePercent + "%)\n" +
                    "Children: " + childPercent + "% (Male: " + childMalePercent + "%)\n" +
                    "Teens: " + teenPercent + "% (Male: " + teenMalePercent + "%)\n" +
                    "Adults: " + adultPercent + "% (Male: " + adultMalePercent + "%)\n" +
                    "Elders: " + elderPercent + "% (Male: " + elderMalePercent + "%)\n" +
                    "Vaccinated: " + vaccinatedPercent + "%";
        }
    }
}