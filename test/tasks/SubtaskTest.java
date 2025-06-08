package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    private Subtask subtask1;
    private Subtask subtask2;

    @BeforeEach
    void setUp() {
        subtask1 = new Subtask("Description task 1", "Task-1", Status.NEW,3);
        subtask2 = new Subtask("Description task 1", "Task-1",Status.NEW,3);
    }

    @Test
    void testEquals() {
        subtask2.setId(subtask1.getId());
        assertEquals(subtask1, subtask2, "Subtasks are not equal");
    }

}