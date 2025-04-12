package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private HistoryManager historyManager;

    @BeforeEach
    void setUpBeforeClass() throws Exception {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void add() {
        historyManager.add(new Task("Task description", "Task-1"));
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "History shouldn't be null");
        assertFalse(history.isEmpty(), "History shouldn't be empty");
    }

    @Test
    void getHistory() {
        assertNotNull(historyManager.getHistory(), "History shouldn't be null");
    }
}