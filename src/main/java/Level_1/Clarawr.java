package Level_1;
import java.util.Scanner;

public class Clarawr {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! I'm Clarawr\n"
                + "What can I do for you?");

        String instruction = scanner.nextLine();

        while (!instruction.equalsIgnoreCase("bye")) { // Check for "bye" (case-insensitive)
            System.out.println(instruction); // Repeat the input
            instruction = scanner.nextLine(); // Read the next input
        }

        System.out.println("Bye. Hope to see you again soon!");
        scanner.close(); // Close the scanner
    }
}
