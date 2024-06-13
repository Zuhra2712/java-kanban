package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {
    HistoryManager historyManager = new InMemoryHistoryManager();
    TaskManager taskManager = new InMemoryTaskManager(historyManager);

    @Test
    public void shouldCreateTask() {
        Task task = taskManager.createTask(new Task("task1", "taskDescription1", Status.NEW,
                LocalDateTime.parse("2021-06-03T13:54"), Duration.ofMinutes(100)));
        List<Task> tasks = taskManager.getAllTask();
        assertEquals(1,task.getTaskId());
        assertEquals(List.of(task), tasks);
        Task task2 = taskManager.createTask(new Task("task2", "taskDescription2", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100)));
        assertEquals(2,task2.getTaskId());
    }
    @Test
    public void shouldCreateEpic() {
        Epic epic = (Epic) taskManager.createEpic(new Epic("epic1", "d1"));

        List<Epic> epics = taskManager.getAllEpic();
        assertEquals(1,epic.getTaskId());
        assertEquals(Status.NEW, epic.getStatus());
        assertEquals("поход в кино", epic.getDescriptionTask());
        assertEquals(List.of(epic), epics);
    }
    @Test
    public void shouldCreateSubtask() {
        Epic epic = (Epic) taskManager.createEpic(new Epic("epic1", "d1"));
        Subtask subtask = (Subtask) taskManager.createSubtask(new Subtask("subtask1", "subtaskDescription1", Status.NEW,
                LocalDateTime.parse("2020-06-03T13:54"), Duration.ofMinutes(100), 1));

        List<Subtask> subtasks = taskManager.getAllSubtask();

        assertEquals(epic.getTaskId(), subtask.getEpicId());
        assertEquals(List.of(subtask), subtasks);
    }

    @Test
    public void shouldDeleteTask() {
        Task task = taskManager.createTask(new Epic("epic1", "epicDescription1"));
        taskManager.deleteTask(task.getTaskId());
        assertEquals( new ArrayList<Task>(),taskManager.getAllTask());//сравниваю с пустым списком
    }

    @Test
    public void shouldDeleteEpic() {
        Epic epic = (Epic) taskManager.createEpic(new Epic("epic1", "epicDescription1"));
        taskManager.createSubtask(new Subtask("Sub1", "D1", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100), 1));
        taskManager.createSubtask(new Subtask("Sub1", "D1", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100), 2));

        taskManager.deleteEpic(epic.getTaskId());

        assertEquals(new ArrayList<Epic>(), taskManager.getAllEpic());
        assertEquals(new ArrayList<Subtask>(), taskManager.getAllSubtask());
    }

}