package Clarawr;

import java.io.*;

import java.util.ArrayList;

/**
 * Handles saving and loading tasks to and from a file.
 * The file path is hardcoded, and tasks are stored in a text file.
 */
public class Storage {
    private static final String FILE_PATH = "C:\\Users\\user\\CS2103_IP\\ip\\src\\data\\clarawr.txt";
    ;

    /**
     * Saves a list of tasks to a file.
     * Each task is written in a format suitable for later loading.
     *
     * @param tasks The list of tasks to save to the file.
     */
    public static void saveTasksToFile(ArrayList<Task> tasks) {

        assert tasks != null : "Tasks list cannot be null";

        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            assert file.exists() || file.createNewFile() : "File path is not valid or file could not be created.";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (Task task : tasks) {

                    assert task.toFileString() != null && !task.toFileString().isEmpty() : "Task cannot be saved in an invalid format";
                    writer.write(task.toFileString());
                    writer.newLine();
                }
                writer.flush();
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

                assert line != null && !line.trim().isEmpty() : "Task data line is null or empty";

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

