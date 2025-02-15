package Clarawr;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with a description, start and end times.
 * Inherits from the Task class and provides additional functionality for event-specific properties.
 */
public class Event extends Task {

    protected LocalDateTime from;
    protected LocalDateTime to;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");

    /**
     * Constructs an Event task with the given description, start and end times, and completion status.
     *
     * @param description The description of the event.
     * @param from The start time of the event.
     * @param to The end time of the event.
     * @param isDone The completion status of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to, boolean isDone) {
        super(description, TaskType.EVENT);

        assert description != null && !description.isEmpty() : "Description cannot be null or empty";
        assert from != null : "Start time cannot be null";
        assert to != null : "End time cannot be null";
        assert from.isBefore(to) : "Start time must be before end time";

        this.from = from;
        this.to = to;
        this.isDone = isDone;
    }

    /**
     * Gets the start time of the event.
     *
     * @return The start time of the event.
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Gets the end time of the event.
     *
     * @return The end time of the event.
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Returns a string representation of the event task, including the description,
     * completion status, and start and end times.
     *
     * @return A string representation of the event task.
     */
    @Override
    public String toString() {
        return " [E]" + super.toString() + " from: " + from.format(formatter) + " to: " +
                to.format(formatter) ;
    }

    /**
     * Returns a string representation of the event task in a format suitable for saving to a file.
     *
     * @return A string representation of the event task in file format.
     */
    @Override
    public String toFileString() {
        return "[E]" + super.toFileString() + " /from " + from.format(formatter) + " /to " +
                to.format(formatter);
    }
}
