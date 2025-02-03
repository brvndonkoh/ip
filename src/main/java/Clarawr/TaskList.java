package Clarawr;

import java.util.ArrayList;

/**
 * Represents a list of tasks, providing methods to add, delete, and modify tasks.
 * This class manages task operations and ensures tasks are saved to a file immediately after modifications.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the task list.
     * After adding, the task list is saved to a file.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        tasks.add(task);
        Storage.saveTasksToFile(tasks);
    }

    /**
     * Marks the specified task as done.
     * After marking, the task list is saved to a file.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTaskAsDone(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        Storage.saveTasksToFile(tasks);  // Save tasks immediately after modification
    }

    /**
     * Marks the specified task as undone.
     * After marking, the task list is saved to a file.
     *
     * @param index The index of the task to mark as undone.
     */
    public void markTaskAsUndone(int index) {
        Task task = tasks.get(index);
        task.markUndone();
        Storage.saveTasksToFile(tasks);  // Save tasks immediately after modification
    }

    /**
     * Deletes the specified task from the task list.
     * After deleting, the task list is saved to a file.
     *
     * @param index The index of the task to delete.
     */
    public void deleteTask(int index) {
        tasks.remove(index);
        Storage.saveTasksToFile(tasks);
    }

    /**
     * Retrieves a task at a specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Retrieves all tasks in the task list.
     *
     * @return An ArrayList of all tasks in the task list.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Adds all tasks from another list to this task list.
     *
     * @param tasks The list of tasks to add.
     */
    public void addAll(ArrayList<Task> tasks) {
        this.tasks.addAll(tasks);
    }
}
