package Clarawr;

import java.util.ArrayList;

/**
 * Provides a method for displaying messages to the user, including
 * task list.
 */
public class Ui {

    /**
     * Displays the list of tasks to the user.
     * If the task list is empty, a message indicating so is displayed.
     *
     * @param tasks The list of tasks to display.
     * @return A formatted string representation of the tasks in the list,
     *         or a message indicating that no tasks are available.
     */
    public String showListOfTasks(ArrayList<Task> tasks) {

        assert tasks != null : "Task list cannot be null";

        if (tasks.isEmpty()) {
            return "No tasks in your list.";
        }

        StringBuilder response = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            response.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }

        return response.toString().trim();
    }
}
