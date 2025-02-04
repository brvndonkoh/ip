package Clarawr;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;

enum TaskType {
    TODO, DEADLINE, EVENT
}

/**
 * The main class for the Clarawr task management application.
 * This class handles the execution of the program, parsing and executing commands,
 * and interacting with the user via the UI.
 */
public class Clarawr {
    private static final Ui ui = new Ui();
    private static final Storage storage = new Storage();
    private static final Parser parser = new Parser();
    private static final TaskList taskList = new TaskList();

    /**
     * The main entry point of the Clarawr application.
     * This method initializes the application, loads tasks from file,
     * processes user commands, and saves tasks back to the file upon exiting.
     *
     * @param args Command line arguments (unused in this implementation).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        taskList.addAll(storage.loadTasksFromFile());

        ui.showWelcomeMessage();

        String instruction = scanner.nextLine();

        while (!instruction.equalsIgnoreCase("bye")) {
            try {
                handleCommand(instruction);
            } catch (ClarawrException e) {
                ui.showErrorMessage(e.getMessage());
            }
            instruction = scanner.nextLine();
        }

        ui.showGoodbyeMessage();
        storage.saveTasksToFile(taskList.getAllTasks());
        scanner.close();
    }

    /**
     * Handles the user input command by delegating to the appropriate method
     * based on the parsed command.
     *
     * @param instruction The user input command.
     * @throws ClarawrException If an error occurs while processing the command.
     */
    private static void handleCommand(String instruction) throws ClarawrException {
        String[] commandParts = parser.parseCommand(instruction);
        String command = commandParts[0].toLowerCase();

		switch (command) {
		case "list":
			ui.showListOfTasks(taskList.getAllTasks());
			break;

		case "todo":
			addTodoTask(commandParts[1]);
			break;

		case "deadline":
			addDeadlineTask(commandParts[1]);
			break;

		case "event":
			addEventTask(commandParts[1]);
			break;

		case "mark":
			markTaskDone(commandParts[1]);
			break;

		case "unmark":
			markTaskUndone(commandParts[1]);
			break;

		case "delete":
		case "remove":
			deleteTask(commandParts[1]);
			break;

		case "listbydate":
			listTasksByDate(commandParts[1]);
			break;

		default:
			ui.showErrorMessage("Sorry, I do not understand your instruction :(");
		}
	}

    /**
     * Adds a new Todo task to the task list.
     *
     * @param description The description of the Todo task.
     * @throws ClarawrException If the description is empty.
     */
    private static void addTodoTask(String description) throws ClarawrException {
        if (description.isEmpty()) {
            throw new ClarawrException("The description of a todo task cannot be empty.");
        }
        Task task = new Todo(description, false);
        taskList.addTask(task);
        ui.showMessage("Got it, I've added this task: " + task);
    }

    /**
     * Adds a new Deadline task to the task list.
     *
     * @param details The description and deadline of the Deadline task in the format "description /by deadline".
     * @throws ClarawrException If the description or deadline is missing.
     */
    private static void addDeadlineTask(String details) throws ClarawrException {
        String[] parts = details.split(" /by ");
        if (parts.length < 2) {
            throw new ClarawrException("The description and deadline of a task cannot be empty.");
        }

        LocalDateTime deadline = parser.parseDeadlineTime(parts[1]);
        Task task = new Deadline(parts[0], deadline, false);
        taskList.addTask(task);
        ui.showMessage("Got it, I've added this task: " + task);
    }

    /**
     * Adds a new Event task to the task list.
     *
     * @param details The description and timing of the Event task in the format "description /from start_time /to end_time".
     * @throws ClarawrException If the description or timings are missing.
     */
    private static void addEventTask(String details) throws ClarawrException {
        String[] parts = details.split(" /from ");
        if (parts.length < 2) {
            throw new ClarawrException("The description and timing of an event cannot be empty.");
        }

        String[] times = parts[1].split(" /to ");
        if (times.length < 2) {
            throw new ClarawrException("The description and both event times cannot be empty.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
        LocalDateTime from = LocalDateTime.parse(times[0].trim(), formatter);
        LocalDateTime to = LocalDateTime.parse(times[1].trim(), formatter);

        Task task = new Event(parts[0], from, to, false);
        taskList.addTask(task);
        ui.showMessage("Got it, I've added this task: " + task);
    }

    /**
     * Marks a task as done by its index.
     *
     * @param indexStr The index of the task to mark as done (1-based index).
     * @throws ClarawrException If the index is invalid or out of range.
     */
    private static void markTaskDone(String indexStr) throws ClarawrException {
        int index = Integer.parseInt(indexStr) - 1;
        taskList.markTaskAsDone(index);
        ui.showMessage("Nice! I've marked this task as done.");
    }

    /**
     * Marks a task as undone by its index.
     *
     * @param indexStr The index of the task to mark as undone (1-based index).
     * @throws ClarawrException If the index is invalid or out of range.
     */
    private static void markTaskUndone(String indexStr) throws ClarawrException {
        int index = Integer.parseInt(indexStr) - 1;
        taskList.markTaskAsUndone(index);
        ui.showMessage("OK, I've marked this task as not done yet.");
    }

    /**
     * Deletes a task by its index.
     *
     * @param indexStr The index of the task to delete (1-based index).
     * @throws ClarawrException If the index is invalid or out of range.
     */
    private static void deleteTask(String indexStr) throws ClarawrException {
        int index = Integer.parseInt(indexStr) - 1;
        Task taskToDelete = taskList.getTask(index);
        taskList.deleteTask(index);
        ui.showMessage("Noted. I've removed this task: " + taskToDelete);
    }

    /**
     * Lists tasks by a specific date.
     *
     * @param dateStr The date to filter tasks by (in the format "yyyy-MM-dd").
     * @throws ClarawrException If the date format is invalid.
     */
    private static void listTasksByDate(String dateStr) throws ClarawrException {
        LocalDate filterDate = LocalDate.parse(dateStr);
        ui.showMessage("Tasks on " + filterDate + ":");

        boolean found = false;
        for (Task task : taskList.getAllTasks()) {
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.getDeadline().toLocalDate().equals(filterDate)) {
                    ui.showMessage(task.toString());
                    found = true;
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.getFrom().toLocalDate().equals(filterDate)) {
                    ui.showMessage(task.toString());
                    found = true;
                }
            }
        }

        if (!found) {
            ui.showMessage("No tasks for this date.");
        }
    }
}
