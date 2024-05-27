package service;

import exceptions.ManagerSaveException;
import model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        this(historyManager, new File("./resources/task.csv"));
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
                task = new Subtask(id, name, taskDescription, taskStatus);
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
                bufferedWriter.write(toString(entry.getValue());
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


} {
        }catch(IOException e1){
        throw new RuntimeException(e1);
        }

        {
        }catch(IOException ioException){
        throw new RuntimeException(ioException);
        }

        {
        }catch(IOException exception){
        throw new RuntimeException(exception);
        }

        {
        }catch(IOException ex){
        throw new RuntimeException(ex);
        }

        {
        }catch(IOException e){
        throw new RuntimeException(e);
        }


        }
