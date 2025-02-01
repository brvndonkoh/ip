import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private LocalDateTime deadline;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");

    public Deadline(String description, LocalDateTime deadline, boolean isDone) {
        super(description, TaskType.DEADLINE);
        this.deadline = deadline;
        this.isDone = isDone;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return " [D]" + super.toString() + " by: " + deadline.format(formatter);
    }

    @Override
    public String toFileString() {
        return "[D]" + super.toFileString() + " /by " + deadline.format(formatter);
    }
}
