package Clarawr;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

/**
 * Handles saving and loading tasks to and from a file.
 * The file path is hardcoded, and tasks are stored in a text file.
 */
public class Storage {
    private static final String FILE_PATH = "C:\\Users\\user\\CS2103_IP\\ip\\src\\data\\clarawr.txt";

    /**
     * Saves a list of tasks to a file.
     * Each task is written in a format suitable for later loading.
     *
     * @param tasks The list of tasks to save to the file.
     */
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

    /**
     * Loads tasks from a file.
     * Each line in the file is parsed into a task object.
     * If corrupt data is encountered, the line is skipped.
     *
     * @return A list of tasks loaded from the file.
     */
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

