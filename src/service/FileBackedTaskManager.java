package service;

import exceptions.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileBackedTaskManager extends InMemoryTaskManager {

    public final File file;

    private final String header = "id,type,name,status,description,epic"; // Заголовок таблицы


    public FileBackedTaskManager() {
        this(Managers.getDefaultHistory());
    }

    public FileBackedTaskManager(HistoryManager historyManager1, File file) {
        super(historyManager1);
        this.file = file;
    }

    public FileBackedTaskManager(HistoryManager historyManager) {
        this(historyManager, new File("./resources/task2.csv"));
    }

    public FileBackedTaskManager(File file) {
        this(Managers.getDefaultHistory(), file);
    }

    //сохраняем задачу в строку
    private String toString(Task task) {
        return task.getTaskId() + "," + task.getType() + "," + task.getNameTask() + "," + task.getStatus() + "," + task.getDescriptionTask() + "," + task.getEpicId();
    }

    // создаем задачу из строки
    private static Task taskFromString(String value) {
        final String[] result = value.split(",");
        int id = Integer.parseInt(result[0]);
        TaskType taskType = TaskType.valueOf(result[1].trim());
        String name = result[3];
        Status taskStatus = Status.valueOf(result[3].trim());
        String taskDescription = result[4];

        Task task = null;
        switch (taskType) {
            case TASK:
                task = new Task(id, name, taskDescription, taskStatus);
                break;
            case SUBTASK:
                final Integer epicId = Integer.parseInt(result[5]);
                task = new Subtask(id, name, taskDescription, taskStatus, epicId);
                break;
            case EPIC:
                task = new Epic(id, name, taskDescription, taskStatus);
                break;
        }
        return task;

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

        return idHistory.toString();
    }

    //сохраняем задачи
    private void save() {
        try (final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            bufferedWriter.write(header);
            bufferedWriter.newLine();
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                bufferedWriter.write(toString(entry.getValue()));
                bufferedWriter.newLine();
            }
            for (Map.Entry<Integer, Subtask> entry : subTasks.entrySet()) {
                bufferedWriter.write(toString(entry.getValue()));
                bufferedWriter.newLine();

            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                bufferedWriter.write(toString(entry.getValue()));
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
        Integer taskId = id;
        TaskType taskType = task.getType();

        switch (taskType) {
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

                if (task.getTaskId() > lastTaskId) {
                    lastTaskId = task.getTaskId();
                }
                manager.restoreId(lastTaskId);

            }


            for (Map.Entry<Integer, Subtask> subTask : manager.subTasks.entrySet()) {
                Integer epicId = subTask.getValue().getEpicId();
                manager.epics.get(epicId).addSubTask(subTask.getValue());
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось прочитать файл");
        }
        return manager;
    }

    public static void printAllTasks(FileBackedTaskManager fileBackedTaskManager) {
        for (Task task : fileBackedTaskManager.getAllTask()) {
            System.out.println(task);

        }
        for (Subtask subtask : fileBackedTaskManager.getAllSubtask()) {
            System.out.println(subtask);

        }
        for (Epic epic : fileBackedTaskManager.getAllEpic()) {
            System.out.println(epic);
        }
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
        //     save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = super.getEpic(id);
        //  save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        //  save();
        return subtask;
    }

    @Override
    public Task createTask(Task task) {
        super.createTask(task);
        //save();
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        super.createEpic(epic);
        //  save();
        return epic;
    }

    @Override
    public Subtask createSubtask(Subtask newSubtask) {
        super.createSubtask(newSubtask);
        //   save();
        return newSubtask;
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
    public Task updateTask(Task task) {
        super.updateTask(task);
        save();
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
        return subtask;
    }
}






