package Clarawr;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a description and a deadline time.
 * Inherits from the Task class and provides functionality specific to deadline tasks.
 */
public class Deadline extends Task {

    private final LocalDateTime deadline;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");

    /**
     * Constructs a Deadline task with the given description, deadline time, and completion status.
     *
     * @param description The description of the task.
     * @param deadline The deadline of the task.
     * @param isDone The completion status of the task.
     */
    public Deadline(String description, LocalDateTime deadline, boolean isDone) {
        super(description, TaskType.DEADLINE);
        this.deadline = deadline;
        this.isDone = isDone;
    }

    /**
     * Gets the deadline time of the task.
     *
     * @return The deadline of the task.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Returns a string representation of the deadline task, including the description,
     * completion status, and deadline time.
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        return " [D]" + super.toString() + " by: " + deadline.format(formatter);
    }

    /**
     * Returns a string representation of the deadline task in a format suitable for saving to a file.
     *
     * @return A string representation of the deadline task in file format.
     */
    @Override
    public String toFileString() {
        return "[D]" + super.toFileString() + " /by " + deadline.format(formatter);
    }
}
