import java.util.Scanner;

public class Clarawr {
    static int pointer = 0;
    static Task[] tasks = new Task[100];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Clarawr\n"
                + "What can I do for you?");

        String instruction = scanner.nextLine();

        while (!instruction.equalsIgnoreCase("bye")) {
            try {
                if (instruction.equalsIgnoreCase("list")) {
                    if (pointer == 0) {
                        System.out.println("No tasks in your list.");
                    } else {
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < pointer; i++) {
                            System.out.println((i + 1) + "." + tasks[i]);
                        }
                    }
                } else if (instruction.startsWith("todo")) {
                    String description = instruction.substring(4);
                    if (description.isEmpty()) {
                        throw new ClarawrException("The description of a todo task cannot be empty.");
                    }
                    tasks[pointer] = new Todo(description);
                    pointer++;
                    System.out.println("Got it, I've added this task: ");
                    System.out.println("  " + tasks[pointer - 1]);
                    System.out.println("Now you have " + pointer + " tasks in the list.");
                } else if (instruction.startsWith("deadline")) {
                    String[] parts = instruction.substring(8).split(" /by ");
                    if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                        throw new ClarawrException("The description and deadline of a task cannot be empty.");
                    }
                    tasks[pointer] = new Deadline(parts[0], parts[1]);
                    pointer++;
                    System.out.println("Got it, I've added this task: ");
                    System.out.println("  " + tasks[pointer - 1]);
                    System.out.println("Now you have " + pointer + " tasks in the list.");
                } else if (instruction.startsWith("event")) {
                    String[] parts = instruction.substring(5).split(" /from ");
                    if (parts.length < 2) {
                        throw new ClarawrException("The description and timing of an event cannot be empty.");
                    }
                    String[] times = parts[1].split(" /to ");
                    if (times.length < 2 || parts[0].isEmpty() || times[0].isEmpty() || times[1].isEmpty()) {
                        throw new ClarawrException("The description and both event times cannot be empty.");
                    }
                    tasks[pointer] = new Event(parts[0], times[0], times[1]);
                    pointer++;
                    System.out.println("Got it, I've added this task: ");
                    System.out.println("  " + tasks[pointer - 1]);
                    System.out.println("Now you have " + pointer + " tasks in the list.");
                } else if (instruction.startsWith("mark ")) {
                    int index = Integer.parseInt(instruction.split(" ")[1]) - 1;
                    if (index >= 0 && index < pointer) {
                        tasks[index].markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println("  " + tasks[index]);
                    } else {
                        System.out.println("Invalid task number! Please try again.");
                    }
                } else if (instruction.startsWith("unmark ")) {
                    int index = Integer.parseInt(instruction.split(" ")[1]) - 1;
                    if (index >= 0 && index < pointer) {
                        tasks[index].markUndone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println("  " + tasks[index]);
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
}
