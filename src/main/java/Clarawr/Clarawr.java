package Clarawr;

import javafx.application.Platform;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

import java.util.Scanner;

enum TaskType {
    TODO, DEADLINE, EVENT
}

/**
 * The main class for the Clarawr task management application.
 * This class handles the execution of the program, parsing and executing commands,
 * and interacting with the user via the user interface (UI).
 */
public class Clarawr {
	private static final Ui ui = new Ui();
	private static final Storage storage = new Storage();
	private static final Parser parser = new Parser();
	private static final TaskList taskList = new TaskList();

	/**
	 * Constructs the Clarawr task management application.
	 * Loads tasks from saved ile during initialisation.
	 */
	public Clarawr() {
		taskList.addAll(storage.loadTasksFromFile());
	}

	/**
	 * Handles the user input command by delegating to the appropriate method
	 * based on the parsed command.
	 *
	 * @param instruction The user input command to process.
	 * @return A response message based on the command processed.
	 * @throws ClarawrException If an error occurs while processing the command.
	 */
	private static String handleCommand(String instruction) throws ClarawrException {
		String[] commandParts = parser.parseCommand(instruction);
		String command = commandParts[0].toLowerCase();

		switch (command) {
		case "list":
			return ui.showListOfTasks(taskList.getAllTasks());

		case "todo":
			return addTodoTask(commandParts[1]);

		case "deadline":
			return addDeadlineTask(commandParts[1]);

		case "event":
			return addEventTask(commandParts[1]);

		case "mark":
			return markTaskDone(commandParts[1]);

		case "unmark":
			return markTaskUndone(commandParts[1]);

		case "delete":
		case "remove":
			return deleteTask(commandParts[1]);

		case "listbydate":
			return listTasksByDate(commandParts[1]);

		case "find":
			return findTasksByKeyword(commandParts[1]);

		case "bye":
			Storage.saveTasksToFile(taskList.getAllTasks());
			Platform.exit();

		default:
			return "Sorry, I do not understand your instruction :(";
		}
	}

	/**
	 * Searches for tasks in the task list that contain the specified keyword in their description.
	 * Displays all matching tasks or a message if no matches are found.
	 *
	 * @param keyword The keyword to search for in task descriptions. Case-insensitive.
	 * @return A string message listing matching tasks or indicating no matches.
	 */
	private static String findTasksByKeyword(String keyword) {
		StringBuilder result = new StringBuilder("Here are the matching tasks:\n");

		boolean found = false;
		for (Task task : taskList.getAllTasks()) {
			if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
				result.append(task).append("\n");
				found = true;
			}
		}

		if (!found) {
			result.append("No matching tasks found.");
		}

		return result.toString();
	}

	/**
	 * Adds a new Todo task to the task list.
	 *
	 * @param description The description of the Todo task.
	 * @return A message confirming that the Todo task has been added.
	 * @throws ClarawrException If the description is empty.
	 */
	private static String addTodoTask(String description) throws ClarawrException {
		if (description.isEmpty()) {
			throw new ClarawrException("The description of a todo task cannot be empty.");
		}
		Task task = new Todo(description, false);
		taskList.addTask(task);
		return "Got it, I've added this task: " + task;
	}

	/**
	 * Adds a new Deadline task to the task list.
	 *
	 * @param details The description and deadline of the Deadline task in the format "description /by deadline".
	 * @return A message confirming that the Deadline task has been added.
	 * @throws ClarawrException If the description or deadline is missing or malformed.
	 */
	private static String addDeadlineTask(String details) throws ClarawrException {
		String[] parts = details.split(" /by ");
		if (parts.length < 2) {
			throw new ClarawrException("The description and deadline of a task cannot be empty.");
		}

		LocalDateTime deadline = parser.parseDeadlineTime(parts[1]);
		Task task = new Deadline(parts[0], deadline, false);
		taskList.addTask(task);
		return "Got it, I've added this task: " + task;
	}

	/**
	 * Adds a new Event task to the task list.
	 *
	 * @param details The description and timing of the Event task in the format "description /from start_time /to end_time".
	 * @return A message confirming that the Event task has been added.
	 * @throws ClarawrException If the description or timing information is missing or malformed.
	 */
	private static String addEventTask(String details) throws ClarawrException {
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
		return "Got it, I've added this task: " + task;
	}

	/**
	 * Marks a task as done by its index.
	 *
	 * @param indexStr The index of the task to mark as done (1-based index).
	 * @return A message confirming that the task has been marked as done.
	 * @throws ClarawrException If the index is invalid or out of range.
	 */
	private static String markTaskDone(String indexStr) throws ClarawrException {
		int index = Integer.parseInt(indexStr) - 1;
		taskList.markTaskAsDone(index);
		return "Nice! I've marked this task as done.";
	}

	/**
	 * Marks a task as undone by its index.
	 *
	 * @param indexStr The index of the task to mark as undone (1-based index).
	 * @return A message confirming that the task has been marked as undone.
	 * @throws ClarawrException If the index is invalid or out of range.
	 */
	private static String markTaskUndone(String indexStr) throws ClarawrException {
		int index = Integer.parseInt(indexStr) - 1;
		taskList.markTaskAsUndone(index);
		return "OK, I've marked this task as not done yet.";
	}

	/**
	 * Deletes a task by its index.
	 *
	 * @param indexStr The index of the task to delete (1-based index).
	 * @return A message confirming that the task has been deleted.
	 * @throws ClarawrException If the index is invalid or out of range.
	 */
	private static String deleteTask(String indexStr) throws ClarawrException {
		int index = Integer.parseInt(indexStr) - 1;
		Task taskToDelete = taskList.getTask(index);
		taskList.deleteTask(index);
		return "Noted. I've removed this task: " + taskToDelete;
	}

	/**
	 * Lists tasks by a specific date.
	 *
	 * @param dateStr The date to filter tasks by (in the format "yyyy-MM-dd").
	 * @return A list of tasks that fall on the specified date.
	 * @throws ClarawrException If the date format is invalid.
	 */
	private static String listTasksByDate(String dateStr) throws ClarawrException {
		LocalDate filterDate = LocalDate.parse(dateStr);
		StringBuilder result = new StringBuilder("Tasks on " + filterDate + ":\n");

		boolean found = false;
		for (Task task : taskList.getAllTasks()) {
			if (task instanceof Deadline) {
				Deadline deadline = (Deadline) task;
				if (deadline.getDeadline().toLocalDate().equals(filterDate)) {
					result.append(task).append("\n");
					found = true;
				}
			} else if (task instanceof Event) {
				Event event = (Event) task;
				if (event.getFrom().toLocalDate().equals(filterDate)) {
					result.append(task).append("\n");
					found = true;
				}
			}
		}

		if (!found) {
			result.append("No tasks for this date.");
		}

		return result.toString();
	}

	/**
	 * Returns the response for the user input command.
	 *
	 * @param input The user input command to process.
	 * @return The response message based on the processed command.
	 */
	public String getResponse(String input) {
		try {
			return handleCommand(input);
		} catch (ClarawrException e) {
			return "Error: " + e.getMessage();
		}
	}
}