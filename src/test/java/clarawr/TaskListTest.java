package clarawr;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void checkGetAllTask() {
        Task toDoTask = new Todo("eat", false);
        String deadlineTaskInput = "Submit CS2103 Assignment /by 2025-12-12 1200";
        String[] parts = deadlineTaskInput.split(" /by ");
        String dateTime = parts[1].substring(0, 10) + " " + parts[1].substring(11, 13) + ":" + parts[1].substring(13); // Add a colon between hours and minutes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime deadline = LocalDateTime.parse(dateTime, formatter);
        Task deadlineTask = new Deadline(parts[0], deadline, false);
        deadlineTask.markAsDone();

        TaskList taskList = new TaskList();
        taskList.addTask(toDoTask);
        taskList.addTask(deadlineTask);
        ArrayList<Task> allTasks = taskList.getAllTasks();

        // Verify that the task list contains the correct tasks
        assertEquals(2, allTasks.size()); // There should be 2 tasks

        // Verify that the Todo task is the first one and is not done
        assertEquals("eat", allTasks.get(0).getDescription());
        assertEquals(false, allTasks.get(0).isDone());

        // Verify that the Deadline task is the second one and is marked as done
        assertEquals("Submit CS2103 Assignment", allTasks.get(1).getDescription());
        assertEquals(true, allTasks.get(1).isDone());

    }

}
