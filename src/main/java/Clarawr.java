import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Clarawr {
    static ArrayList<Task> tasks = new ArrayList<>();
    private static final String DIRECTORY_PATH = "./data";
    private static final String FILE_PATH = "./data/clarawr.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        loadTasksFromFile();

        System.out.println("Hello! I'm Clarawr\n"
                + "What can I do for you?");

        String instruction = scanner.nextLine();

        while (!instruction.equalsIgnoreCase("bye")) {
            try {
                if (instruction.equalsIgnoreCase("list")) {
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks in your list.");
                    } else {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + "." + tasks.get(i));
                        }
                    }
                } else if (instruction.startsWith("todo")) {
                    String description = instruction.substring(4).trim();
                    if (description.isEmpty()) {
                        throw new ClarawrException("The description of a todo task cannot be empty.");
                    }
                    tasks.add(new Todo(description));
                    System.out.println("Got it, I've added this task: ");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    saveTasksToFile();
                } else if (instruction.startsWith("deadline")) {
                    String[] parts = instruction.substring(8).split(" /by ");
                    if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                        throw new ClarawrException("The description and deadline of a task cannot be empty.");
                    }
                    tasks.add(new Deadline(parts[0], parts[1]));
                    System.out.println("Got it, I've added this task: ");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    saveTasksToFile();
                } else if (instruction.startsWith("event")) {
                    String[] parts = instruction.substring(5).split(" /from ");
                    if (parts.length < 2) {
                        throw new ClarawrException("The description and timing of an event cannot be empty.");
                    }
                    String[] times = parts[1].split(" /to ");
                    if (times.length < 2 || parts[0].isEmpty() || times[0].isEmpty() || times[1].isEmpty()) {
                        throw new ClarawrException("The description and both event times cannot be empty.");
                    }
                    tasks.add(new Event(parts[0], times[0], times[1]));
                    System.out.println("Got it, I've added this task: ");
                    System.out.println("  " + tasks.get(tasks.size() - 1));
                    System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                    saveTasksToFile();
                } else if (instruction.startsWith("mark ")) {
                    int index = Integer.parseInt(instruction.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks.get(index));
                        saveTasksToFile();
                    } else {
                        System.out.println("Invalid task number! Please try again.");
                    }
                } else if (instruction.startsWith("unmark ")) {
                    int index = Integer.parseInt(instruction.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        tasks.get(index).markUndone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks.get(index));
                        saveTasksToFile();
                    } else {
                        System.out.println("Invalid task number! Please try again.");
                    }
                } else if (instruction.startsWith("delete ") || instruction.startsWith("remove ")) {
                    int index = Integer.parseInt(instruction.split(" ")[1]) - 1;
                    if (index >= 0 && index < tasks.size()) {
                        Task taskToDelete = tasks.remove(index);
                        System.out.println("Noted. I've removed this task: ");
                        System.out.println("  " + taskToDelete);
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        saveTasksToFile();
                    } else {
                        System.out.println("Invalid task number! Please try again.");
                    }
                } else {
                    System.out.println("Sorry I do not understand your instruction :(");
                }
            } catch (ClarawrException e) {
                System.out.println(e.getMessage());
            }
            instruction = scanner.nextLine();
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }

    private static void saveTasksToFile() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (Task task : tasks) {
                bufferedWriter.write(task.toFileString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks to file.");
        }
    }

    private static void loadTasksFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    // Assuming you have a way to parse each line into a Task object
                    try {
                        if (line.startsWith("[T]")) {
                            tasks.add(new Todo(line.substring(4)));
                        } else if (line.startsWith("[D]")) {
                            String[] parts = line.substring(4).split(" /by ");
                            tasks.add(new Deadline(parts[0], parts[1]));
                        } else if (line.startsWith("[E]")) {
                            String[] parts = line.substring(4).split(" /from ");
                            String[] times = parts[1].split(" /to ");
                            tasks.add(new Event(parts[0], times[0], times[1]));
                        }
                    } catch (Exception e) {
                        System.out.println("Warning: Corrupt task data detected. Skipping line.");
                    }
                }
                bufferedReader.close();
            }
        } catch (IOException e) {
            System.out.println("No existing tasks found.");
        }
    }
}

