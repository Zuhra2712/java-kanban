package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class inMemoryHistoryManagerTest {
    @Test
    public void shouldReturnHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task1 = taskManager.createTask(new Task("Первый таск"));
        Task task2 = taskManager.createTask(new Task("Второй  таск"));
        Task epic1 = taskManager.createEpic(new Epic("Первый эпик"));
        Task sub = taskManager.createSubtask(new Subtask("Первый сабтаск",new Epic("Эпик")));

        taskManager.getEpic(epic1.getTaskId());
        List<Task> history = historyManager.history();
        Assertions.assertEquals(1,history.size());
         taskManager.get(task2.getTaskId());
         history = historyManager.history();
         Assertions.assertEquals(2,history.size(),"должен увеличиться");
         Assertions.assertEquals(List.of(epic1,task2), historyManager.history());

        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(epic1.getTaskId());
        taskManager.get(task2.getTaskId());//добавляем 11 и проверяем , что он станет последним в списке

        history = historyManager.history();

        Assertions.assertEquals(10,history.size(),"должен увеличиться");
        System.out.println(history.get(9)); //распечатываем элемент под индексом 9
    }
    @Test
    public void shouldAddTasksToHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task1 = taskManager.createTask(new Task("Первый таск"));
        Task task2 = taskManager.createTask(new Task("Второй таск"));
        Task task3 = taskManager.createTask(new Task("Третий таск"));

        historyManager.addTaskToHistory(task1);
        historyManager.addTaskToHistory(task2);
        historyManager.addTaskToHistory(task3);
       // List<Task> realHistory = historyManager.history();
      //  System.out.println(realHistory);
      Assertions.assertEquals(List.of(task1,task2,task3), historyManager.history());
    }
}