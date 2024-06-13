package model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    @Test
    void equalsTask() {
        Task task1 = new Task(1, "epic1", "переезд", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100));
        Task task2 = new Task(1, "epic1", "переезд", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100));

        assertEquals(task1,task2);
    }
}