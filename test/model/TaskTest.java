package model;

import org.junit.jupiter.api.Test;
import service.HistoryManager;
import service.InMemoryHistoryManager;
import service.InMemoryTaskManager;
import service.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void equalsTask() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task1 = new Task(1,"Переезд","В новую квартиру",Status.NEW);
        Task task2 = new Task(1,"Отпуск","В новую квартиру",Status.NEW);

        assertEquals(task1,task2);
    }
}