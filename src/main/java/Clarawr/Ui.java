package Clarawr;

import java.util.ArrayList;

/**
 * Provides methods for displaying messages to the user, including
 * welcome, goodbye, task list, and error messages.
 */
public class Ui {

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcomeMessage() {
        System.out.println("Hello! I'm Clarawr.Clarawr\nWhat can I do for you?");
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays the list of tasks to the user.
     * If the task list is empty, a message indicating so is displayed.
     *
     * @param tasks The list of tasks to display.
     */
    public void showListOfTasks(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in your list.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
    }

    /**
     * Displays a custom message to the user.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage The error message to display.
     */
    public void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
}
