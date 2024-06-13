package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class inMemoryHistoryManagerTest {
    @Test
    void add() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        Task task1 = new Task(2, "task1", "d1", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100));
        inMemoryHistoryManager.addTaskToHistory(task1);
        final List<Task> history = inMemoryHistoryManager.gethistory();
        assertNotNull(history, "История пустая");
        assertEquals(1, history.size(), "История не пустая");
    }

    @Test
    void remove() {
        InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
        Task task1 = new Task(2, "task1", "d1", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100));
        inMemoryHistoryManager.addTaskToHistory(task1);
        List<Task> history = inMemoryHistoryManager.gethistory();
        history.remove(task1);
        assertTrue(history.isEmpty(), "История пустая");

    }





    @Test
    public void shouldReturnHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task1 = taskManager.createTask(new Task(1, "task", "переезд", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100)));
        Task task2 = taskManager.createTask(new Task(2, "task2", "переезд", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100)));
        Task epic1 = taskManager.createEpic(new Epic("epic1", "d1"));
        Task sub = taskManager.createSubtask(new Subtask("subtask1", "subtaskDescription1", Status.NEW,
                LocalDateTime.parse("2020-06-03T13:54"), Duration.ofMinutes(100), 1));

        taskManager.getEpic(epic1.getTaskId());
        List<Task> history = historyManager.gethistory();
        Assertions.assertEquals(1,history.size());
         taskManager.get(task2.getTaskId());
        history = historyManager.gethistory();
         Assertions.assertEquals(2,history.size(),"должен увеличиться");
        Assertions.assertEquals(List.of(epic1, task2), historyManager.gethistory());

        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(task2.getTaskId());//добавляем 11 и проверяем , что он станет последним в списке

        history = historyManager.gethistory();

        Assertions.assertEquals(10,history.size(),"должен увеличиться");
        System.out.println(history.get(9)); //распечатываем элемент под индексом 9
    }
    @Test
    public void shouldAddTasksToHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task1 = taskManager.createTask(new Task("task1", "taskDescription1", Status.NEW,
                LocalDateTime.parse("2021-06-03T13:54"), Duration.ofMinutes(100)));
        Task task2 = taskManager.createTask(new Task("task2", "taskDescription2", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100)));
        Task task3 = taskManager.createTask(new Task("task3", "taskDescription3", Status.NEW,
                LocalDateTime.parse("2021-06-03T17:54"), Duration.ofMinutes(100)));

        historyManager.addTaskToHistory(task1);
        historyManager.addTaskToHistory(task2);
        historyManager.addTaskToHistory(task3);
       // List<Task> realHistory = historyManager.history();
      //  System.out.println(realHistory);
        Assertions.assertEquals(List.of(task1, task2, task3), historyManager.gethistory());
    }
}