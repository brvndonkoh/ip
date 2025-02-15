package Clarawr;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Parses task data from a string and command input into corresponding task objects.
 * Also provides utility methods for parsing commands and deadlines.
 */
public class Parser {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");

    /**
     * Parses a task string into a Task object.
     * Supports different task types: Todo, Deadline, and Event.
     *
     * @param taskData The task data as a string to be parsed.
     * @return A Task object corresponding to the parsed data.
     * @throws Exception If the task data is in an invalid format.
     */
    public static Task parseTask(String taskData) throws Exception {

        boolean isDone = taskData.charAt(4) == 'X';

        if (taskData.startsWith("[T]")) {
            return new Todo(taskData.substring(6), isDone);
        } else if (taskData.startsWith("[D]")) {
            String[] parts = taskData.substring(6).split(" /by ");
            LocalDateTime deadlineDateTime = LocalDateTime.parse(parts[1].trim(), formatter);
            return new Deadline(parts[0], deadlineDateTime, isDone);
        } else if (taskData.startsWith("[E]")) {
            String[] parts = taskData.substring(6).split(" /from ");
            String[] times = parts[1].split(" /to ");
            LocalDateTime from = LocalDateTime.parse(times[0].trim(), formatter);
            LocalDateTime to = LocalDateTime.parse(times[1].trim(), formatter);
            return new Event(parts[0], from, to, isDone);
        }

        throw new Exception("Invalid task data format.");
    }

    /**
     * Parses a command input string into an array of command and arguments.
     * The input string is split into two parts at the first space.
     *
     * @param input The command input string.
     * @return An array of strings where the first element is the command and the second is the argument.
     */
    public static String[] parseCommand(String input) {
        return input.split(" ", 2);
    }

    /**
     * Parses a deadline time string into a LocalDateTime object.
     * The expected format is "yyyy-MM-dd HHmm".
     *
     * @param dateTimeString The deadline string to parse.
     * @return The corresponding LocalDateTime object.
     */
    public static LocalDateTime parseDeadlineTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
