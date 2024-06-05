package service;

import model.Epic;
import model.Subtask;
import model.Task;

public class ClassForTesting {
    FileBackedTaskManager fileBackedTaskManager;

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

}
