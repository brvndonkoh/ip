import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
        Storage.saveTasksToFile(tasks);
    }

    public void markTaskAsDone(int index) {
        Task task = tasks.get(index);
        task.markAsDone();
        Storage.saveTasksToFile(tasks);  // Save tasks immediately after modification
    }

    public void markTaskAsUndone(int index) {
        Task task = tasks.get(index);
        task.markUndone();
        Storage.saveTasksToFile(tasks);  // Save tasks immediately after modification
    }


    public void deleteTask(int index) {
        tasks.remove(index);
        Storage.saveTasksToFile(tasks);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    public void addAll(ArrayList<Task> tasks) {
        this.tasks.addAll(tasks);
    }
}
