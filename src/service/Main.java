package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.createTask(new Task("Переезд"));
        System.out.println("Create task: " + task);

        Task taskFromManager = taskManager.get(task.getTaskId());
        System.out.println("Get task: " + taskFromManager);

        taskFromManager.setNameTask("Изменили название задачи");
        taskManager.updateTask(taskFromManager);
        System.out.println("Update task:" + taskFromManager);

        taskManager.deleteTask(taskFromManager.getTaskId());
        System.out.println("Delete:" + task);

    }
}
