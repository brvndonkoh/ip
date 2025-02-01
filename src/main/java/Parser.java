import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {

    public static Task parseTask(String taskData) throws Exception {
        boolean isDone = false;
        if (taskData.charAt(3) == 'X') {
            isDone = true;
            taskData = taskData.substring(0, 3) + taskData.substring(4); // Remove the [X] from the string
        }

        if (taskData.startsWith("[T]")) {
            return new Todo(taskData.substring(3), isDone);
        } else if (taskData.startsWith("[D]")) {
            String[] parts = taskData.substring(3).split(" /by ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");
            LocalDateTime deadlineDateTime = LocalDateTime.parse(parts[1].trim(), formatter);
            return new Deadline(parts[0], deadlineDateTime, isDone);
        } else if (taskData.startsWith("[E]")) {
            String[] parts = taskData.substring(3).split(" /from ");
            String[] times = parts[1].split(" /to ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");
            LocalDateTime from = LocalDateTime.parse(times[0].trim(), formatter);
            LocalDateTime to = LocalDateTime.parse(times[1].trim(), formatter);
            return new Event(parts[0], from, to, isDone);
        }

        throw new Exception("Invalid task data format.");
    }


    public static String[] parseCommand(String input) {
        return input.split(" ", 2);
    }

    public static LocalDateTime parseDeadlineTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
