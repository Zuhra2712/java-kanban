package model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void name() {
        Epic epic = new Epic(1, "epic1", "d1", Status.NEW, LocalDateTime.parse("2021-06-03T17:54"), Duration.ofMinutes(100));
        Epic epicExpected = new Epic(1, "epic1", "d1", Status.NEW, LocalDateTime.parse("2021-06-03T17:54"), Duration.ofMinutes(100));
        assertEqualsTask(epicExpected,epic,"Эпики должны быть равны");
    }
    private  static void assertEqualsTask(Task expected, Task actual, String message){
        assertEquals(expected.getTaskId(),actual.getTaskId(),"+ id");
        assertEquals(expected.getNameTask(),actual.getNameTask(),"+ nameTask");
    }

}