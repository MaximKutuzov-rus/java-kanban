package tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        task1 = new Task("Переезд", "В новую квартиру", Status.NEW, Duration.ofMinutes(240),
                LocalDateTime.of(2025, Month.JULY, 11, 14, 40));
        task2 = new Task("Переезд", "В новый дом", Status.NEW, Duration.ofMinutes(20),
                LocalDateTime.of(2025, Month.JULY, 11, 15, 50));
        task3 = new Task("Переезд", "В новую квартиру", Status.NEW, Duration.ofMinutes(240),
                LocalDateTime.of(2025, Month.JULY, 11, 15, 40));
    }

    @Test
    void setUpEndTime() {
        assertEquals(task1.getEndTime(), task1.startTime.plus(task1.duration));
    }

    @Test
    void equalsCheck() {
        assertNotEquals(task1, task3);
    }

    @Test
    void intersectionCheck() {
        assertTrue(task2.checkIntersection(task3));
    }
}