import java.util.Scanner;

public class Clarawr {
    static int pointer = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] instructions = new String[100]; // Array to store instructions

        System.out.println("Hello! I'm Clarawr\n"
                + "What can I do for you?");

        String instruction = scanner.nextLine();

        while (!instruction.equalsIgnoreCase("bye")) { // Check for "bye" (case-insensitive)
            if (instruction.equalsIgnoreCase("list")) {
                // Print the instructions in the array
                if (pointer == 0) {
                    System.out.println("No instructions added yet.");
                } else {
                    for (int i = 0; i < pointer; i++) {
                        System.out.println((i + 1) + ". " + instructions[i]);
                    }
                }
            } else {
                System.out.println("added: " + instruction); // Repeat the input
                instructions[pointer] = instruction; // Add the instruction to the array
                pointer++; // Move the pointer to the next index
            }

            instruction = scanner.nextLine(); // Read the next input
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close(); // Close the scanner
    }
}
