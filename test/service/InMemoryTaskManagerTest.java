package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    HistoryManager historyManager = new InMemoryHistoryManager();
    TaskManager taskManager = new InMemoryTaskManager(historyManager);

    @Test
    public void shouldCreateTask() {
        Task task = taskManager.createTask(new Task("Первый таск"));
        List<Task> tasks = taskManager.getAllTask();
        assertEquals(1,task.getTaskId());
        assertEquals(List.of(task), tasks);
        Task task2 = taskManager.createTask(new Task("Второй  таск"));
        assertEquals(2,task2.getTaskId());
    }
    @Test
    public void shouldCreateEpic() {
        Epic epic = taskManager.createEpic(new Epic("Эпик","поход в кино",Status.NEW));

        List<Epic> epics = taskManager.getAllEpic();
        assertEquals(1,epic.getTaskId());
        assertEquals(Status.NEW, epic.getStatus());
        assertEquals("поход в кино", epic.getDescriptionTask());
        assertEquals(List.of(epic), epics);
    }
    @Test
    public void shouldCreateSubtask() {
        Epic epic = taskManager.createEpic(new Epic("Переезд"));
        Subtask subtask = taskManager.createSubtask(new Subtask("Собрать коробки",epic));

        List<Subtask> subtasks = taskManager.getAllSubtask();

        assertEquals(epic.getTaskId(), subtask.getEpic().getTaskId());
        assertEquals(List.of(subtask), subtasks);
    }

    @Test
    public void shouldDeleteTask() {
        Task task = taskManager.createTask(new Task("Переезд"));
        taskManager.deleteTask(task.getTaskId());
        assertEquals( new ArrayList<Task>(),taskManager.getAllTask());//сравниваю с пустым списком
    }

    @Test
    public void shouldDeleteEpic() {
        Epic epic = taskManager.createEpic(new Epic("Эпик"));
        taskManager.createSubtask(new Subtask("Собрать коробки",epic));
        taskManager.createSubtask(new Subtask("Расторгнуть договор",epic));

        taskManager.deleteEpic(epic.getTaskId());

        assertEquals(new ArrayList<Epic>(), taskManager.getAllEpic());
        assertEquals(new ArrayList<Subtask>(), taskManager.getAllSubtask());
    }

}