package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void name() {
        Epic epic = new Epic(1, "переезд", "в новую квартиру", Status.NEW);
        Epic epicExpected = new Epic(1, "переезд", "в новую квартиру", Status.DONE);
        assertEqualsTask(epicExpected,epic,"Эпики должны быть равны");
    }
    private  static void assertEqualsTask(Task expected, Task actual, String message){
        assertEquals(expected.getTaskId(),actual.getTaskId(),"+ id");
        assertEquals(expected.getNameTask(),actual.getNameTask(),"+ nameTask");
    }

}