package Clarawr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TodoTest {
    @Test
    public void checkToStringTest() {
        Task todoTask = new Todo("eat", false);
        assertEquals(" [T][ ] eat",
                todoTask.toString());
    }
}
