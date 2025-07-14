package managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager>{

    @BeforeEach
    void createManager() {
        tm = new InMemoryTaskManager();
    }

    @Test
    public void haveNoIntersectionsTest() {
        tm.addTask(task3);
        tm.addTask(task4);
        tm.addEpic(epic3);
        tm.addSubtask(subtask5);
        tm.addSubtask(subtask6);
        tm.addSubtask(subtask7);
        tm.addEpic(epic4);
        assertFalse(tm.haveNoIntersections(subtask8));
    }
}