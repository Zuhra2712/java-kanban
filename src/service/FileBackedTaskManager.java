package service;

import exceptions.ManagerSaveException;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static model.Task.taskFromString;

public class FileBackedTaskManager extends InMemoryTaskManager {
    protected final File file;

    private final static String HEADER = "id,type,name,status,description,startTime,duration,endTime,epic"; // Заголовок таблицы

    public FileBackedTaskManager() {
        this(Managers.getDefaultHistory());
    }

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    public FileBackedTaskManager(HistoryManager historyManager) {
        this(historyManager, new File("resourses/taskT.csv"));


    }

    public FileBackedTaskManager(File file) {
        this(Managers.getDefaultHistory(), file);
    }


    //восстановить менеджер истории из таблицы
    private static List<Integer> historyFromString(String value) {
        final String[] ids = value.split(",");
        List<Integer> history = new ArrayList<>();
        for (String id : ids) {
            history.add(Integer.valueOf(id));
        }
        return history;
    }

    //сохранить менеджер истории в таблицу
    private static String historyToString(HistoryManager historyManager) {
        final List<Task> taskHistory = historyManager.gethistory();
        StringBuilder idHistory = new StringBuilder();


        for (Task task : taskHistory) {
            idHistory.append(task.getTaskId()).append(",");

        }
        // return idHistory.substring(0, idHistory.length() - 2);
        return idHistory.toString().trim();


    }

    //сохраняем задачи
    private void save() {


        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            bufferedWriter.write(HEADER);
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                bufferedWriter.write(entry.getValue().toString());
                bufferedWriter.newLine();
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                bufferedWriter.write(entry.getValue().toString());
                bufferedWriter.newLine();
            }
            for (Map.Entry<Integer, Subtask> entry : subTasks.entrySet()) {
                bufferedWriter.write(entry.getValue().toString());
                bufferedWriter.newLine();

            }

            bufferedWriter.write("\n");
            bufferedWriter.write(historyToString(historyManager));


        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось записать файл");
        }
    }

    private void addHistory(List<Integer> historyIds) { //Добавлено
        for (Integer id : historyIds) {
            historyManager.addTaskToHistory(get(id));
        }
    }

    private void addTask(Task task, int id) {

        switch (task.getType()) {
            case TASK:
                tasks.put(id, task);
                break;
            case SUBTASK:
                subTasks.put(id, (Subtask) task);
                break;
            case EPIC:
                epics.put(id, (Epic) task);
                break;
        }

    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        int lastTaskId = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(manager.file))) {
            bufferedReader.readLine();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                if (line.isBlank()) {
                    String historyLine = bufferedReader.readLine();
                    manager.addHistory(historyFromString(historyLine));
                    break;
                }
                Task task = taskFromString(line);

                manager.addTask(task, task.getTaskId());

                lastTaskId = Math.max(task.getTaskId(), lastTaskId);

                manager.restoreId(lastTaskId);

            }


            for (Map.Entry<Integer, Subtask> subTask : manager.subTasks.entrySet()) {
                Integer epicId = subTask.getValue().getEpicId();
                manager.epics.get(epicId).addSubTask(subTask.getValue());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось прочитать файл");
        } catch (NullPointerException e) {
            System.out.println("Не могу найти ошибку о нулевом эпик");
        }
        return manager;
    }


    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Task get(int id) {
        Task task = super.get(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        save();
        return task;
    }

    @Override
    public Task createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return null;
    }

    @Override
    public Task createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
        return null;
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }
}

