package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, Subtask> subTasks;
    int seq = 0;

    private int generateId() {
        return ++seq;
    }

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    public TaskManager(HashMap<Integer, Task> tasks, HashMap<Integer, Epic> epics, HashMap<Integer, Subtask> subTasks) {
        this.tasks = tasks;
        this.epics = epics;
        this.subTasks = subTasks;
    }


    public Task createTask(Task task) {
        task.setID(generateId());
        tasks.put(task.getID(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setID(generateId());
        epics.put(epic.getID(), epic);
        return epic;
    }

    public Subtask createSubtask(Subtask subtask) {
        subtask.setID(generateId());
        subTasks.put(subtask.getID(), subtask);
        Epic epic = epics.get(subtask.getEpic().getID());
        epic.addSubTask(subtask);
        calculateStatus(epic);
        return subtask;
    }

    public List<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtask() {
        return new ArrayList<>(subTasks.values());
    }


    public void deleteTask(int id) {
        Task task = tasks.get(id);
        tasks.remove(id, task);
    }

    public void deleteSubtask(int id) {
        Subtask removeSubTask = subTasks.get(id);
        Epic epic = removeSubTask.getEpic();
        Epic epicSaved = epics.get(epic.getID());
        epicSaved.getSubtasks().remove(removeSubTask);
        calculateStatus(epicSaved);
    }

    public void deleteEpic(int id) {
        Epic epic = epics.get(id);
        ArrayList<Subtask> deleteSubTask = (ArrayList<Subtask>) epic.getSubtasks();
        deleteSubTask.clear();
        epics.remove(id, epic);
    }


    public void updateTask(Task task) {
        Task taskNew = tasks.get(task.getID());
        taskNew.setNameTask(task.getNameTask());
        taskNew.setStatus(task.getStatus());
        taskNew.setDescriptionTask(task.getDescriptionTask());

    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getID());
        if ((saved == null)) {
            return;
        }
        saved.setSubtasks(epic.getSubtasks());
        saved.setDescriptionTask(epic.getDescriptionTask());
        calculateStatus(saved);
    }

    public void updateSubtask(Subtask subtask){
        Subtask subtask1 = subTasks.get(subtask.getID());
        if(subtask1 == null){
            return;
        }
        Epic epic = subtask1.getEpic();
        ArrayList<Subtask> newSubtask = (ArrayList<Subtask>) epic.getSubtasks();
        newSubtask.add(subtask1);
        epic.setSubtasks(newSubtask);
        calculateStatus(epic);
    }

    public Task get(int id) {
        return tasks.get(id);
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public Subtask getSubtask(int id) {
        return subTasks.get(id);
    }

    public ArrayList<Subtask> getEpicSubtask(Epic epic){
        ArrayList<Subtask> result = (ArrayList<Subtask>) epic.getSubtasks();
        return result;

    }

    public void calculateStatus(Epic epic) {
        List<Subtask> subtaskArrayList = epic.getSubtasks();
        if (subtaskArrayList.isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        for (Subtask subtask : subtaskArrayList) {
            Status statusSubtask = subtask.getStatus();
            ArrayList<Status> result = new ArrayList<>(subtaskArrayList.size());
            result.add(statusSubtask);
            if (result.contains(Status.NEW)) {
                epic.setStatus(Status.NEW);
            } else if (result.contains(Status.IN_PROGRESS)) {
                epic.setStatus(Status.IN_PROGRESS);
            } else {
                epic.setStatus(Status.DONE);

            }
        }
    }

}

