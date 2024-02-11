package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void name() {
        Epic epic = new Epic("переезд","в новую квартиру", Status.NEW);
        Epic epicExpected = new Epic("переезд","в новую квартиру", Status.DONE);
        assertEqualsTask(epicExpected,epic,"Эпики должны быть равны");
    }
    private  static void assertEqualsTask(Task expected, Task actual, String message){
        assertEquals(expected.getTaskId(),actual.getTaskId(),"+ id");
        assertEquals(expected.getNameTask(),actual.getNameTask(),"+ nameTask");
    }

}