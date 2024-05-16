package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    @Test
    void equalsTask() {
        Task task1 = new Task(1,"Переезд","В новую квартиру",Status.NEW);
        Task task2 = new Task(1,"Отпуск","В новую квартиру",Status.NEW);

        assertEquals(task1,task2);
    }
}