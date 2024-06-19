package service;

import exceptions.TaskValidationException;
import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int nextId = 1;
    protected HashMap<Integer, Task> tasks;
    protected HashMap<Integer, Epic> epics;
    protected HashMap<Integer, Subtask> subTasks;
    protected final Set<Task> prioritizedTasks;
    protected final HistoryManager historyManager;
    public static final Comparator<Task> COMPARE_TASK_BY_START_TIME = Comparator.comparing(Task::getStartTime);


    public InMemoryTaskManager(HistoryManager historyManager) {
        epics = new HashMap<>();
        subTasks = new HashMap<>();
        tasks = new HashMap<>();

        this.historyManager = Managers.getDefaultHistory();
        prioritizedTasks = new TreeSet<>(COMPARE_TASK_BY_START_TIME);
    }


    protected void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        Status status = calculateEpicStatus(epicId);
        epic.setStatus(status);
    }

    public void updateEpicTime(int epicId) {
        List<Integer> subtaskIds = epics.get(epicId).getSubtasksId();//берем список подзадач эпика
        if (subtaskIds.isEmpty()) { // если список пустой устанавливаем нулевые значения для эпика
            epics.get(epicId).setDuration(null);
            epics.get(epicId).setStartTime(null);
            epics.get(epicId).setEndTime(null);
            return;
        }
        LocalDateTime epicStartTime = LocalDateTime.now();
        LocalDateTime epicEndTime = LocalDateTime.now();
        Duration epicDuration = Duration.ZERO;
        for (Integer subtaskId : subtaskIds) {
            Subtask subtask = subTasks.get(subtaskId);
            LocalDateTime subtaskStartTime = subtask.getStartTime();
            LocalDateTime subtaskEndTime = subtask.getEndTime();
            if (subtaskStartTime != null) {
                if (epicStartTime == LocalDateTime.now() || subtaskStartTime.isBefore(epicStartTime)) {
                    epicStartTime = subtaskStartTime;
                }
                epicDuration = epicDuration.plus(subTasks.get(subtaskId).getDuration());
            }
            if (subtaskEndTime != null) {
                if (epicEndTime == LocalDateTime.now() || subtaskEndTime.isAfter(epicEndTime)) {
                    epicEndTime = subtaskEndTime;
                }
            }
        }
        if (epicStartTime != LocalDateTime.now()) {
            epics.get(epicId).setStartTime(epicStartTime);
            epics.get(epicId).setEndTime(epicEndTime);
            epics.get(epicId).setDuration(epicDuration);
        } else {
            epics.get(epicId).setStartTime(null);
            epics.get(epicId).setEndTime(null);
            epics.get(epicId).setDuration(null);

        }
    }

    public void checkValidation(Task newTask) {
        List<Task> prioritizedTasks = getPrioritizedTasks();
        for (Task existTask : prioritizedTasks) {
            if (newTask.getStartTime() == null || existTask.getStartTime() == null) {
                return;
            }
            if (Objects.equals(newTask.getTaskId(), existTask.getTaskId())) {
                continue;
            }
            if ((newTask.getEndTime().isBefore(existTask.getStartTime())) ||
                    (newTask.getStartTime().isAfter(existTask.getEndTime()))) {
                continue;
            }
            throw new TaskValidationException("Время выполнения задачи пересекается со временем уже существующей " +
                    "задачи. Выберите другую дату.");
        }
    }

    protected void updateId(int id) {
        if (nextId <= id) {
            nextId = id + 1;
        }
    }

    @Override
    public Task createTask(Task task) {
        if (Objects.isNull(task))
            return task;
        checkValidation(task);
        if (task.getTaskId() == 0) {
            task.setTaskId(nextId);
            nextId++;
        }
        tasks.put(task.getTaskId(), task);
        prioritizedTasks.add(task);


        return task;
    }

    @Override
    public Task createEpic(Epic epic) {
        if (Objects.isNull(epic))
            return null;

        if (epic.getTaskId() == 0) {
            epic.setTaskId(nextId);
            nextId++;
        }

        epics.put(epic.getTaskId(), epic);
        return epic;
    }

    @Override
    public Task createSubtask(Subtask subtask) {
        if (Objects.isNull(subtask))
            return null;
        checkValidation(subtask);
        if (subtask.getTaskId() == 0) {
            subtask.setTaskId(nextId);
            nextId++;
        }
        subTasks.put(subtask.getTaskId(), subtask);

        int epicId = subtask.getEpicId();

        Epic epic = epics.get(epicId);
        epic.addSubtask(subtask.getTaskId());

        updateEpic(epicId);
        prioritizedTasks.add(subtask);
        return null;
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
        if (Objects.isNull(tasks.get(id))) {
            return;
        }
        prioritizedTasks.remove(tasks.get(id));
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtask(int id) {
        if (Objects.isNull(subTasks.get(id))) {
            return;
        }
        Subtask removeSubTask = subTasks.get(id);
        int epic = removeSubTask.getEpicId();
        Epic epicSaved = epics.get(epic);
        epicSaved.getSubtasksId().remove(id);
        prioritizedTasks.remove(removeSubTask);

    }

    @Override
    public void deleteEpic(int id) {
        if (Objects.isNull(epics.get(id))) {
            return;
        }
        Epic epicDelete = epics.remove(id);
        List<Integer> subtasksId = epicDelete.getSubtasksId();
        for (Integer subtaskId : subtasksId) {
            prioritizedTasks.remove(subTasks.get(subtaskId));
            historyManager.remove(subtaskId);
            subTasks.remove(subtaskId);
        }


    }


    @Override
    public void updateTask(Task task) {
        int id = task.getTaskId();
        checkValidation(task);
        prioritizedTasks.remove(tasks.get(id));
        tasks.put(task.getTaskId(), task);
        prioritizedTasks.add(task);
    }


    private void updateEpic(int epicId) {
        updateEpicTime(epicId);
        updateEpicStatus(epicId);

    }

    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getTaskId());
        if (saved == null) {
            return;
        }
        saved.setNameTask(epic.getNameTask());
        epics.put(epic.getTaskId(), saved);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        int id = subtask.getTaskId();
        checkValidation(subtask);
        prioritizedTasks.remove(subTasks.get(id));
        subTasks.put(id, subtask);
        int epicId = subTasks.get(id).getEpicId();
        updateEpic(epicId);
        prioritizedTasks.add(subtask);

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
        return new ArrayList<>(subTasks.values());

    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    @Override
    public int addNewEpic(Epic epic) {
        return 0;
    }

    @Override
    public int addNewSubtask(Subtask subtask) {
        return 0;
    }

    @Override
    public int addNewTask(Task task) {
        return 0;
    }


    public void deleteAllTasks() {
        for (Integer i : tasks.keySet()) {
            prioritizedTasks.remove(tasks.get(i));
            historyManager.remove(i);

        }
        tasks.clear();

    }


    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.removeAllSubtasks();
            epic.setStatus(Status.NEW);
            epic.setStartTime(LocalDateTime.now());
            epic.setDuration(Duration.ZERO);
        }
        for (Integer i : subTasks.keySet()) {
            prioritizedTasks.remove(subTasks.get(i));
            historyManager.remove(i);

        }
        subTasks.clear();


    }

    public void deleteAllEpics() {
        for (int subtaskId : subTasks.keySet()) {
            prioritizedTasks.remove(subTasks.get(subtaskId));
            historyManager.remove(subtaskId);
        }


        for (Integer i : epics.keySet()) {
            historyManager.remove(i);

        }
        epics.clear();
        subTasks.clear();

    }


    protected Status calculateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic.getSubtasksId().isEmpty())
            return Status.NEW;

        if (epic.getSubtasksId()
                .stream()
                .map(subTasks::get)
                .map(Task::getStatus)
                .allMatch((status) -> status.equals(Status.NEW)))
            return Status.NEW;

        if (epic.getSubtasksId()
                .stream()
                .map(subTasks::get)
                .map(Task::getStatus)
                .allMatch((status) -> status.equals(Status.DONE)))
            return Status.DONE;

        return Status.IN_PROGRESS;
    }


    protected void restoreId(int id) {
        this.nextId = id;
    }


}

