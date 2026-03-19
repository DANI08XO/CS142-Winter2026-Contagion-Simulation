// Class for user to save file

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DataSaver {

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

}
