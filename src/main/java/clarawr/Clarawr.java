package clarawr;

import javafx.application.Platform;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;

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
		String command = commandParts[0].toLowerCase().trim();

		switch (command) {
		case "hi":
			return "Hello there! I can't find my mommy...";

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
			return "Sorry, I do not understand your instruction *whimpers* ... typo?";
		}
	}

	/**
		* Searches for tasks in the task list that contain the specified keyword in their description.
		* Displays all matching tasks or a message if no matches are found.
		*
		* @param keyword The keyword to search for in task descriptions. Case-insensitive.
		* @return A string message listing matching tasks or indicating no matches.
		* @throws ClarawrException If the keyword is null or empty.
		*/
	private static String findTasksByKeyword(String keyword) throws ClarawrException {
		if (keyword == null || keyword.trim().isEmpty()) {
			throw new ClarawrException("Oops! The keyword cannot be empty. Please enter a valid search term.");
		}

		StringBuilder result = new StringBuilder("Here are the matching tasks:\n");
		boolean isFound = false;

		for (Task task : taskList.getAllTasks()) {
			if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
				result.append(task).append("\n");
				isFound = true;
			}
		}

		if (!isFound) {
			result.append("RAWR YOU WASTED MY TIME NO TASKS FOUND");
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
		assert description != null : "Description cannot be null";
		if (description.isEmpty()) {
			throw new ClarawrException("Please add a description *sniffs* I don't know what you are talking about...");
		}
		Task task = new Todo(description, false);

		if (isDuplicateTask(task)) {
			return "RAWR! You already have the task in your list dummy";
		}

		taskList.addTask(task);
		return "Better feed me as I've added this task: " + task;
	}

	/**
	 * Checks whether the given task is a duplicate in the task list.
	 * A task is considered a duplicate if it already exists in the list of tasks, based on equality.
	 *
	 * @param task The task to be checked for duplication.
	 * @return true if the task is a duplicate (exists in the task list), false otherwise.
	 */
	private static boolean isDuplicateTask(Task task) {
		for (Task existingTask : taskList.getAllTasks()) {
			if (existingTask.equals(task)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a new Deadline task to the task list.
	 *
	 * @param details The description and deadline of the Deadline task in the format "description /by deadline".
	 * @return A message confirming that the Deadline task has been added.
	 * @throws ClarawrException If the description or deadline is missing or malformed.
	 */
	private static String addDeadlineTask(String details) throws ClarawrException {
		assert details != null : "Details cannot be null";

		String[] parts = details.split(" /by ");

		assert parts.length == 2 : "The details should contain both a description and deadline separated by ' /by '";

		if (parts.length < 2) {
			throw new ClarawrException("Tell me the description and deadline please.");
		}

		LocalDateTime deadline = Parser.parseDeadlineTime(parts[1]);
		Task task = new Deadline(parts[0], deadline, false);

		if (isDuplicateTask(task)) {
			return "RAWR! How many times do you want to add it? It is already in the list.";
		}

		taskList.addTask(task);
		return "HEHE, I've added this task: " + task;
	}

	/**
	 * Adds a new Event task to the task list.
	 *
	 * @param details The description and timing of the Event task in the format
	 *                   "description /from start_time /to end_time".
	 * @return A message confirming that the Event task has been added.
	 * @throws ClarawrException If the description or timing information is missing or malformed.
	 */
	private static String addEventTask(String details) throws ClarawrException {
		assert details != null : "Details cannot be null";
		String[] parts = details.split(" /from ");

		assert parts.length == 2 : "The event details should contain both a " +
				"description and a start time separated by ' /from '";

		if (parts.length < 2) {
			throw new ClarawrException("The description and timing of an event cannot be empty.");
		}

		String[] times = parts[1].split(" /to ");

		assert times.length == 2 : "The event should contain both a start time and an end time separated by ' /to '";

		if (times.length < 2) {
			throw new ClarawrException("The description and both event times cannot be empty.");
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
		LocalDateTime from = LocalDateTime.parse(times[0].trim(), formatter);
		LocalDateTime to = LocalDateTime.parse(times[1].trim(), formatter);

		assert from.isBefore(to) : "The start time must be before the end time.";

		Task task = new Event(parts[0], from, to, false);

		if (isDuplicateTask(task)) {
			return "No! Not going to add a duplicate man.";
		}

		taskList.addTask(task);
		return "RAWR! I've added this task: " + task;
	}

	/**
		* Marks a task as done based on the provided index.
		*
		* @param indexStr The index of the task to be marked as done (1-based index).
		* @return A confirmation message indicating the task has been marked as done.
		* @throws ClarawrException If the input is not a valid integer or if the index is out of range.
		*/
	private static String markTaskDone(String indexStr) throws ClarawrException {
		assert indexStr != null : "Index string cannot be null";

		try {
			int index = Integer.parseInt(indexStr) - 1; // Convert string to zero-based index

			if (index < 0 || index >= taskList.getSize()) {
				throw new ClarawrException("Oops! The task number you provided is out of bounds dumdum.");
			}

			taskList.markTaskAsDone(index);
			return "Wow, someone's productive! I've marked this task as done: " + taskList.getTask(index);

		} catch (NumberFormatException e) {
			throw new ClarawrException("Oopsie Daisy! That doesn't seem like a valid task number. Please enter a valid integer.");
		} catch (IndexOutOfBoundsException e) {
			throw new ClarawrException("Oops! The task number you provided is out of bounds.");
		}
	}

	/**
		* Marks a task as undone based on the provided index.
		*
		* @param indexStr The index of the task to be marked as undone, provided as a string.
		* @return A confirmation message indicating the task has been marked as not done.
		* @throws ClarawrException If the input is not a valid integer or if the index is out of bounds.
		*/
	private static String markTaskUndone(String indexStr) throws ClarawrException {
		assert indexStr != null : "Index string cannot be null";

		try {
			int index = Integer.parseInt(indexStr) - 1; // Convert string to integer

			if (index < 0 || index >= taskList.getSize()) {
				throw new ClarawrException("Oops! The task number you provided is out of bounds dummy.");
			}

			taskList.markTaskAsUndone(index);
			return "Why did you even mark it as done in the first place?" +
											" I've marked this task as not done yet: " + taskList.getTask(index);

		} catch (NumberFormatException e) {
			throw new ClarawrException("Oopsie Daisy! That doesn't seem like a valid task number. Please enter a valid integer.");
		} catch (IndexOutOfBoundsException e) {
			throw new ClarawrException("Oops! The task number you provided is out of bounds dummy.");
		}
	}

	/**
		* Deletes a task by its index.
		*
		* @param indexStr The index of the task to delete (1-based index).
		* @return A message confirming that the task has been deleted.
		* @throws ClarawrException If the index is invalid, out of range, or not a number.
		*/
	private static String deleteTask(String indexStr) throws ClarawrException {
		assert indexStr != null : "Index string cannot be null";

		try {
			int index = Integer.parseInt(indexStr) - 1; // Convert to zero-based index

			if (index < 0 || index >= taskList.getSize()) {
				throw new ClarawrException("Oops! The task number you provided is out of bounds dummy check the list lol.");
			}

			Task taskToDelete = taskList.getTask(index);
			taskList.deleteTask(index);
			return "*BURP* I've eaten this task hehe: " + taskToDelete;

		} catch (NumberFormatException e) {
			throw new ClarawrException("Oops! That doesn't seem like a valid task number. Please enter a valid integer.");
		} catch (IndexOutOfBoundsException e) {
			throw new ClarawrException("Oops! The task number you provided is out of bounds... hais.. ");
		}
	}

	/**
	 * Lists tasks by a specific date.
	 *
	 * @param dateStr The date to filter tasks by (in the format "yyyy-MM-dd").
	 * @return A list of tasks that fall on the specified date.
	 * @throws ClarawrException If the date format is invalid.
	 */
	private static String listTasksByDate(String dateStr) throws ClarawrException {
		assert dateStr != null : "Date string cannot be null";

		// Validate format YYYY-MM-DD using regex
		if (!dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
			throw new ClarawrException("BRUH invalid date format. Please use YYYY-MM-DD.");
		}

		LocalDate filterDate;
		try {
			filterDate = LocalDate.parse(dateStr);
		} catch (DateTimeParseException e) {
			throw new ClarawrException("Invalid date input. You dumdum please ensure the date exists (e.g., no 2025-02-30).");
		}

		StringBuilder result = new StringBuilder("Tasks on " + filterDate + ":\n");
		boolean isFound = false;

		for (Task task : taskList.getAllTasks()) {
			if (task instanceof Deadline) {
				Deadline deadline = (Deadline) task;
				if (deadline.getDeadline().toLocalDate().equals(filterDate)) {
					result.append(task).append("\n");
					isFound = true;
				}
			} else if (task instanceof Event) {
				Event event = (Event) task;
				if (event.getFrom().toLocalDate().equals(filterDate)) {
					result.append(task).append("\n");
					isFound = true;
				}
			}
		}

		if (!isFound) {
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