import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy HH:mm");

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description, TaskType.EVENT);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return " [E]" + super.toString() + " from: " + from.format(formatter) + " to: " +
                to.format(formatter) ;
    }

    @Override
    public String toFileString() {
        return "[E]" + super.toFileString() + " /from " + from.format(formatter) + " /to " +
                to.format(formatter);
    }
}
