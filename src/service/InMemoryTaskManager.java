package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subTasks = new HashMap<>();

    protected final HistoryManager historyManager;
    private int seq = 0;

    private int generateId() {
        return ++seq;
    }


    public InMemoryTaskManager(HistoryManager historyManager1) {
        this.historyManager = historyManager1;
        this.tasks = new HashMap<>();
        this.subTasks = new HashMap<>();
        this.epics = new HashMap<>();
    }

    @Override
    public Task createTask(Task task) {
        task.setTaskId(generateId());
        tasks.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        if (epic == null) {
            return null;
        }
        epic.setTaskId(generateId());
        epics.put(epic.getTaskId(), epic);
        calculateStatus(epic.getTaskId());
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask subtask) {
        subtask.setTaskId(generateId());
        subTasks.put(subtask.getTaskId(), subtask);
        int epic = epics.get(subtask.getEpicId()).getTaskId();
        Epic epic1 = epics.get(epic);
        epic1.addSubTask(subtask);
        calculateStatus(subtask.getEpicId());
        return subtask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.gethistory();
    }


    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtask() {
        return new ArrayList<>(subTasks.values());
    }


    @Override
    public void deleteTask(int id) {
        Task task = tasks.get(id);
        tasks.remove(id, task);
    }

    @Override
    public void deleteSubtask(int id) {
        Subtask removeSubTask = subTasks.get(id);
        int epic = removeSubTask.getEpicId();
        Epic epicSaved = epics.get(epic);
        epicSaved.getSubtasks().remove(removeSubTask);
        calculateStatus(epic);
    }

    @Override
    public void deleteEpic(int id) {
        Epic epicDelete = epics.get(id);
        ArrayList<Subtask> subtasksDelete = epics.get(epicDelete.getTaskId()).getSubtasks();
        subtasksDelete.clear();
        epics.remove(epicDelete.getTaskId());
    }


    @Override
    public Task updateTask(Task task) {
        Task taskNew = tasks.get(task.getTaskId());
        taskNew.setNameTask(task.getNameTask());
        taskNew.setStatus(task.getStatus());
        taskNew.setDescriptionTask(task.getDescriptionTask());
        return task;

    }

    @Override
    public Epic updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getTaskId());
        if (saved == null) {
            return null;
        }
        saved.setNameTask(epic.getNameTask());
        epics.put(epic.getTaskId(), saved);
        return epic;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        Subtask updateSubTask = subTasks.get(subtask.getTaskId());
        if (updateSubTask == null) {
            return null;
        }

        return subtask;
    }

    @Override
    public Task get(int id) {
        historyManager.addTaskToHistory(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.addTaskToHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        historyManager.addTaskToHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public ArrayList<Subtask> getEpicSubtask(Epic epic) {
        return epic.getSubtasks();

    }

    @Override
    public void calculateStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Subtask> subtaskArrayList = epic.getSubtasks();
        if (subtaskArrayList.isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        for (Subtask subtask : subtaskArrayList) {
            Status statusSubtask = subtask.getStatus();
            ArrayList<Status> result = new ArrayList<>(subtaskArrayList.size());
            result.add(statusSubtask);
            if ((!result.contains(Status.NEW)) && (!result.contains(Status.IN_PROGRESS))) {
                epic.setStatus(Status.DONE);
            } else if ((!result.contains(Status.IN_PROGRESS)) && (!result.contains(Status.DONE))) {
                epic.setStatus(Status.NEW);
            } else {
                epic.setStatus(Status.IN_PROGRESS);

            }
        }
    }


}

