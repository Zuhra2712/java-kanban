package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
     private HashMap<Integer, Task> tasks = new HashMap<>();
     private HashMap<Integer, Epic> epics = new HashMap<>();
     private HashMap<Integer, Subtask> subTasks = new HashMap<>();
     private int seq = 0;

    private int generateId() {
        return ++seq;
    }

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    public Task createTask(Task task) {
        task.setTaskId(generateId());
        tasks.put(task.getTaskId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setTaskId(generateId());
        epics.put(epic.getTaskId(), epic);
        calculateStatus(epic);
        return epic;
    }

    public Subtask createSubtask(Subtask subtask) {
        subtask.setTaskId(generateId());
        subTasks.put(subtask.getTaskId(), subtask);
        Epic epic = epics.get(subtask.getEpic().getTaskId());
      //  epic.addSubTask(subtask);
      //  calculateStatus(epic);
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
        Epic epicSaved = epics.get(epic.getTaskId());
        epicSaved.getSubtasks().remove(removeSubTask);
        calculateStatus(epicSaved);
    }

    public void deleteEpic(int id) {
        Epic epicDelete = epics.get(id);
        ArrayList<Subtask> subtasksDelete = epics.get(epicDelete.getTaskId()).getSubtasks();
        subtasksDelete.clear();
        epics.remove(epicDelete.getTaskId());
    }


    public void updateTask(Task task) {
        Task taskNew = tasks.get(task.getTaskId());
        taskNew.setNameTask(task.getNameTask());
        taskNew.setStatus(task.getStatus());
        taskNew.setDescriptionTask(task.getDescriptionTask());

    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getTaskId());
        if (saved == null) {
            return;
        }
        saved.setNameTask(epic.getNameTask());
        epics.put(epic.getTaskId(),saved);

    }

    public void updateSubtask(Subtask subtask){
        Subtask updateSubTask = subTasks.get(subtask.getTaskId());
        if(updateSubTask == null){
            return;
        }
        Epic epic = updateSubTask.getEpic();
        epic.getSubtasks().remove(updateSubTask);
        Epic epicResult = epics.get(epic.getTaskId());
        epicResult.getSubtasks().add(subtask.getTaskId(),subtask);
        calculateStatus(epic);
        calculateStatus(epicResult);
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
        return epic.getSubtasks();

    }

    public void calculateStatus(Epic epic) {
        ArrayList<Subtask> subtaskArrayList = epic.getSubtasks();
        if (subtaskArrayList.isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        for (Subtask subtask : subtaskArrayList) {
            Status statusSubtask = subtask.getStatus();
            ArrayList<Status> result = new ArrayList<>(subtaskArrayList.size());
            result.add(statusSubtask);
            if( (!result.contains(Status.NEW)) && (!result.contains(Status.IN_PROGRESS))){
                epic.setStatus(Status.DONE);
            } else if ((!result.contains(Status.IN_PROGRESS))&&(!result.contains(Status.DONE)) ){
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);

            }
        }
    }


}

