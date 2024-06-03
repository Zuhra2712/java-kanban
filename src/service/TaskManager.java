package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    Task createTask(Task task);


    Epic createEpic(Epic epic);

    Subtask createSubtask(Subtask subtask);

    List<Task> getHistory();

    List<Task> getAllTask();

    List<Epic> getAllEpic();

    List<Subtask> getAllSubtask();

    void deleteTask(int id);

    void deleteSubtask(int id);

    void deleteEpic(int id);

    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    Subtask updateSubtask(Subtask subtask);

    Task get(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    ArrayList<Subtask> getEpicSubtask(Epic epic);


    default void deleteAllTasks() {

    }

    default void deleteAllSubtasks() {


    }

    default void deleteAllEpics() {

    }

    void calculateStatus(int epicId);
}
