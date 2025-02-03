package Clarawr;

/**
 * Represents a generic task with a description, completion status, and type.
 * Subclasses must specify the type of task (e.g., Todo, Deadline, Event).
 */
abstract class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    /**
     * Constructs a new task with a description and task type.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     * @param type The type of the task (e.g., Todo, Deadline, Event).
     */
    public Task(String description, TaskType type) {
        this.description = description;
        this.isDone = false;
        this.type = type;
    }

    /**
     * Returns the status icon of the task.
     * "X" represents a completed task, and a space represents an incomplete task.
     *
     * @return The status icon of the task, either "X" or a space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task.
     * The string includes the task status icon and the description.
     *
     * @return A string representation of the task.
     */
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    /**
     * Returns a string representation of the task in a format suitable for file storage.
     *
     * @return A string representing the task, formatted for file storage.
     */
    public String toFileString() {
        return (isDone ? "X" : " ") + description;
    }

    /**
     * Returns the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the task is completed.
     *
     * @return True if the task is done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }
}
