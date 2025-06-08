package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = new Task("Description task 1", "Task-1");
        task2 = new Task("Description task 1", "Task-1");
    }

    @Test
    void testEquals() {
        task2.setId(task1.getId());
        assertEquals(task1, task2, "Tasks are not equal");
    }
}