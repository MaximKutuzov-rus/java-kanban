package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.List;

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

        assertEquals(task1, tm.getTaskById(task1.getId()));
    }

    @Test
    void testUniqueIdGeneration() {
        Task task1 = new Task("Task1", "Desc1", Status.NEW);
        Task task2 = new Task("Task2", "Desc2", Status.NEW);
        tm.addTask(task1);
        tm.addTask(task2);
        assertEquals(task1.getId() + 1, task2.getId());
    }

    @Test
    public void addSubtaskTest() {
        Epic epic = new Epic("Epic","New epic");
        tm.addEpic(epic);
        Subtask subtask = new Subtask("Subtask","New subtask",Status.IN_PROGRESS,1);
        tm.addSubtask(subtask);
        assertEquals(List.of(2), epic.getSubtasksIds(), "Don't work");
    }

    @Test
    public void removeSubtaskCheck() {
        Epic epic = new Epic("Epic","New epic");
        tm.addEpic(epic);
        Subtask subtask = new Subtask("Subtask","New subtask",Status.IN_PROGRESS,1);
        tm.addSubtask(subtask);
        tm.deleteSubtaskById(2);
        Assertions.assertFalse(epic.getSubtasksIds().contains(2));
    }
}