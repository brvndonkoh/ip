package Clarawr;

import java.util.ArrayList;

public class Ui {
    public void showWelcomeMessage() {
        System.out.println("Hello! I'm Clarawr\nWhat can I do for you?");
    }

    public void showGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

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

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }
}
