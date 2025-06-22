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
    public void removeSubtaskCheck() {
        Epic epic = new Epic("Epic","New epic");
        tm.addEpic(epic);
        Subtask subtask = new Subtask("Subtask","New subtask",Status.IN_PROGRESS,1);
        tm.addSubtask(subtask);
        tm.deleteSubtaskById(2);
        Assertions.assertFalse(epic.getSubtasksIds().contains(2));
    }
}