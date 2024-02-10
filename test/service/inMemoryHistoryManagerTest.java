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
    public void returnHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task1 = taskManager.createTask(new Task("Первый таск"));
        Task task2 = taskManager.createTask(new Task("Второй  таск"));
        Task epic1 = taskManager.createEpic(new Epic("Первый эпик"));
        Task sub = taskManager.createSubtask(new Subtask("Первый сабтаск",new Epic("Эпик")));

        taskManager.getEpic(epic1.getTaskId());
        List<Task> history = historyManager.history();

        Assertions.assertEquals(1,history.size());

       taskManager.get(sub.getTaskId());
       historyManager.history();


     Assertions.assertEquals(2,history.size(),"должен увеличиться");




    }

    @Test
    void addToHistoru() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task1 = new Task("Первый таск");
        Task task2 = new Task(task1.getTaskId(),"Второй таск","Описание", Status.DONE);
        taskManager.get(task1.getTaskId());
        taskManager.get(task2.getTaskId());
        List<Task> history = historyManager.history();
        Assertions.assertEquals("Второй таск",history.get(1));


    }
}