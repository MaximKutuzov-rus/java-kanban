package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    InMemoryTaskManager tm;

    @BeforeEach
    void createTask() {
        tm = new InMemoryTaskManager();
    }

    @Test
    void testNoIdConflicts() {
        Task task1 = new Task("Task1", "Desc1", Status.NEW);
        tm.addTask(task1);

        assertEquals(task1, tm.getTaskUsingId(task1.getId()));
    }


    @Test
    void testUniqueIdGeneration() {
        Task task1 = new Task("Task1", "Desc1", Status.NEW);
        Task task2 = new Task("Task2", "Desc2", Status.NEW);
        tm.addTask(task1);
        tm.addTask(task2);
        assertEquals(task1.getId() + 1, task2.getId());
    }
}