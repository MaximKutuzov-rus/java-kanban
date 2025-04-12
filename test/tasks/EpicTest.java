package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    private Epic epic1;
    private Epic epic2;

    @BeforeEach
    void setUp() {
        epic1 = new Epic("Description task 1", "Task-1");
        epic2 = new Epic("Description task 2", "Task-2");
    }

    @Test
    void testEquals() {
        epic2.setId(epic1.getId());
        assertEquals(epic1, epic2, "Epics are not equal");
    }

    @Test
    public void addSubtaskTest() {

    }
}