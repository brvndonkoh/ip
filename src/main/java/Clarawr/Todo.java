package Clarawr;

/**
 * Represents a task of type TODO.
 * Inherits from the {@link Task} class and provides specific implementations
 * for the task type and its related behaviors.
 */
public class Todo extends Task {

    /**
     * Constructs a new Todo task.
     *
     * @param description A brief description of the task.
     * @param isDone A boolean indicating whether the task is completed.
     */
    public Todo (String description, boolean isDone) {
        super(description, TaskType.TODO);
        this.isDone = isDone;
    }

    /**
     * Returns a string representation of the Todo task.
     *
     * @return A string representing the Todo task, including its completion status.
     */
    @Override
    public String toString() {
        return " [T]" + super.toString();
    }

    /**
     * Returns a string representation of the Todo task in a format suitable for file storage.
     *
     * @return A string representation of the Todo task formatted for file storage.
     */
    @Override
    public String toFileString() {
        return "[T]" + super.toFileString();
    }
}
