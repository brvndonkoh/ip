package Clarawr;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    private static final String FILE_PATH = "./data/clarawr.txt";

    public static void saveTasksToFile(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }

    public ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    tasks.add(Parser.parseTask(line));
                } catch (Exception e) {
                    System.out.println("Warning: Corrupt task data detected. Skipping line.");
                }
            }
        } catch (IOException e) {
            System.out.println("No existing tasks found.");
        }
        return tasks;
    }
}

