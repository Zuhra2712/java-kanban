package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void createTask(Task task);


    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    List<Task> getHistory();

    List<Task> getAllTask();

    List<Epic> getAllEpic();

    List<Subtask> getAllSubtask();

    void deleteTask(int id);

    void deleteSubtask(int id);

    void deleteEpic(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    Task get(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    ArrayList<Subtask> getEpicSubtask(Epic epic);

    List<Task> getPrioritizedTasks();


    void calculateStatus(int epicId);
}
