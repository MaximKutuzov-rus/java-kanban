package tm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.util.List;

class InMemoryHistoryManagerTest {

    InMemoryHistoryManager hm;
    Task task1;
    Task task2;
    Task task3;
    Task task4;

    @BeforeEach
    void createHM() {
        hm = new InMemoryHistoryManager();
        task1 = new Task("Task","New task", Status.IN_PROGRESS, 2);
        task2 = new Task("Task","New task", Status.IN_PROGRESS, 3);
        task3 = new Task("Task","New task", Status.IN_PROGRESS, 4);
        task4 = new Task("Task","New task", Status.IN_PROGRESS, 5);

    }

    @Test
    void linkChangeCheck() {
        hm.add(task1);
        Assertions.assertEquals(task1, hm.getTail().data, "Don't work");
    }

    @Test
    void removeCheckIfOne() {
        hm.add(task1);
        hm.remove(task1.getId());
        Assertions.assertNull(hm.getTail());
        Assertions.assertNull(hm.getHead());
    }

    @Test
    void removeCheckFromCenter() {
        hm.add(task1);
        hm.add(task3);
        hm.add(task4);
        hm.add(task2);
        hm.remove(task3.getId());
        Assertions.assertEquals(List.of(task2,task4,task1), hm.getHistory());
    }

    @Test
    void removeCheckFromEnd() {
        hm.add(task1);
        hm.add(task3);
        hm.add(task4);
        hm.add(task2);
        hm.remove(task1.getId());
        Assertions.assertEquals(List.of(task2,task4,task3), hm.getHistory());
    }

    @Test
    void removeCheckFromStart() {
        hm.add(task1);
        hm.add(task3);
        hm.add(task4);
        hm.add(task2);
        hm.remove(task2.getId());
        Assertions.assertEquals(List.of(task4,task3,task1), hm.getHistory());
    }

    @Test
    void doubleCallCheck() {
        hm.add(task1);
        hm.add(task3);
        hm.add(task4);
        hm.add(task2);
        hm.add(task3);
        Assertions.assertEquals(List.of(task3,task2,task4,task1), hm.getHistory());
    }

    @Test
    void emptyHistoryCheck() {
        Assertions.assertTrue(hm.getHistory().isEmpty());
    }

    @Test
    void changeNodeInMapCheck() {
        hm.add(task1);
        hm.add(task2);
        hm.add(task3);
        hm.add(task4);
        InMemoryHistoryManager.Node oldNode = hm.getNodeMap().get(task3.getId());
        hm.add(task3);
        InMemoryHistoryManager.Node newNode = hm.getNodeMap().get(task3.getId());
        Assertions.assertNotEquals(oldNode,newNode);
    }

    @Test
    void getHistoryCheck() {
        hm.add(task3);
        hm.add(task2);
        hm.add(task1);
        hm.add(task4);
        Assertions.assertEquals(List.of(task4,task1,task2,task3), hm.getHistory());
    }
}