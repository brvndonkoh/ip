import java.util.Scanner;

public class Clarawr {
    static int pointer = 0;
    static Task[] tasks = new Task[100]; //Array to store Task objects

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello! I'm Clarawr\n"
                + "What can I do for you?");

        String instruction = scanner.nextLine();

        while (!instruction.equalsIgnoreCase("bye")) {
            if (instruction.equalsIgnoreCase("list")) {
                // Print the instructions in the array
                if (pointer == 0) {
                    System.out.println("No instructions added yet.");
                } else {
                    System.out.println("Here are the tasks in your list: ");
                    for (int i = 0; i < pointer; i++) {
                        System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].description);
                    }
                }
            } else if (instruction.split(" ")[0].equals("mark")) {
                int selecter = Integer.parseInt(instruction.split(" ")[1]) - 1;
                if (selecter >= 0 && selecter < pointer) {
                    tasks[selecter].isDone = true;
                    System.out.println("Nice! I've marked this task as done: \n"
                            + "[" + tasks[selecter].getStatusIcon() + "] " + tasks[selecter].description);
                } else {
                    System.out.println("Task does not exist! Please try again.");
                }

            } else if (instruction.split(" ")[0].equals("unmark")) {
                int selecter = Integer.parseInt(instruction.split(" ")[1]) - 1;
                if (selecter >= 0 && selecter < pointer) {
                    tasks[selecter].isDone = false;
                    System.out.println("OK, I've marked this task as not done yet: \n"
                            + "[" + tasks[selecter].getStatusIcon() + "] " + tasks[selecter].description);
                } else {
                    System.out.println("Task does not exist! Please try again.");
                }
            } else {
                System.out.println("added: " + instruction); // Repeat the input
                tasks[pointer] = new Task(instruction); // Add the instruction to the array
                pointer++; // Move the pointer to the next index
            }

            instruction = scanner.nextLine(); // Read the next input
        }
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close(); // Close the scanner
    }
}
