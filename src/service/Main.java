package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static service.FileBackedTaskManager.loadFromFile;
import static service.FileBackedTaskManager.printAllTasks;

public class Main {

    public static void main(String[] args) {
        Path path = Paths.get("./resources/.csv");
        File file = new File(String.valueOf(path));

        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        Task task1 = manager.createTask(new Task(1, "task 1 description", "D1", Status.NEW));
        Task task2 = manager.createTask(new Task(2, "task 2 description", "D2", Status.NEW));
        Epic epic1 = manager.createEpic(new Epic(1, "Epic1", "D3", Status.NEW));
        Epic epic2 = manager.createEpic(new Epic(2, "Epic2", "D4", Status.NEW));
        Subtask subTask1 = manager.createSubtask(new Subtask(1, "Subtask1", "D5", Status.NEW, 1));
        Subtask subTask2 = manager.createSubtask(new Subtask(1, "Subtask2", "D6", Status.NEW, 1));
        Subtask subTask3 = manager.createSubtask(new Subtask(1, "Subtask3", "D7", Status.NEW, 1));

        manager.get(task1.getTaskId());
        manager.get(task2.getTaskId());
        manager.getEpic(epic1.getTaskId());
        manager.getEpic(epic2.getTaskId());
        manager.getSubtask(subTask1.getTaskId());
        manager.getSubtask(subTask2.getTaskId());
        manager.getSubtask(subTask3.getTaskId());

        printAllTasks(manager);
        System.out.println("--------------");

        FileBackedTaskManager restoredManager = new FileBackedTaskManager(file);
        restoredManager = loadFromFile(restoredManager.file);
        printAllTasks(restoredManager);
        System.out.println("restored tasks printed");
        Task task3 = restoredManager.createTask(new Task(3, "task 3 description", "D3", Status.NEW));
        restoredManager.createTask(task3);
        System.out.println("-----------------------");


        printAllTasks(restoredManager);
        restoredManager.get(task3.getTaskId());
        for (Task item : restoredManager.getHistory()) {
            System.out.print(item.getTaskId() + ",");
        }
        System.out.println("\n");

     /*   System.out.println("--------print subTaskIds arrays (if any)------------");
        for (Map.Entry<Integer, Epic> item : restoredManager.epics.entrySet()) {
            System.out.println(item.getValue().getNameTask() + ", " + item.getValue().getTaskId());
            for (Integer id : item.getValue().getEpicId()) {
                System.out.println(id);


        }*/
    }
}









      /*  HistoryManager historyManager = new InMemoryHistoryManager();
        TaskManager taskManager = new InMemoryTaskManager(historyManager);
        Task task = taskManager.createTask(new Task("Переезд"));
        System.out.println(task);//если без этой строчки ,то некорректно присваивается id для Task
        Task task1 = taskManager.createTask(new Task("Закончить учебу"));
        System.out.println(task1);
        Task task2= taskManager.createTask(new Task("Сходить в поход"));
        System.out.println(task2);
        Task task3 = taskManager.createSubtask(new Subtask("Написать  договор", 1));
        Task task4 = taskManager.createSubtask(new Subtask("Подписать договор", 2));
        Task task5 = taskManager.createSubtask(new Subtask("Отправить договор", 3));
        Task task6 = taskManager.createEpic(new Epic("Первый эпик"));
        Task task7 = taskManager.createEpic(new Epic("Второй эпик"));
        Task task8 = taskManager.createEpic(new Epic("Третий эпик"));


            System.out.println("Задачи:");
            for (Task taskk : taskManager.getAllTask()) {
                System.out.println(task);
            }
            System.out.println("Эпики:");
            for (Task epic : taskManager.getAllEpic()) {
                System.out.println(epic);

           //     for (Task taskk : taskManager.getEpicSubtask(epic.getTaskId())) {
            //        System.out.println("--> " + task);
            //    }
            }
            System.out.println("Подзадачи:");
            for (Task subtask : taskManager.getAllSubtask()) {
                System.out.println(subtask);
            }

            System.out.println("История:");
            for (Task taskk : taskManager.getHistory()) {
                System.out.println(task);
            }
        System.out.println("-----------------------------------------------------");

        System.out.println("Create task: " + task1);
        System.out.println("Create task: " + task2);


        Task taskFromManager = taskManager.get(task.getTaskId());
        System.out.println("Get task: " + taskFromManager);

        taskFromManager.setNameTask("Изменили название задачи");
        taskManager.updateTask(taskFromManager);
        System.out.println("Update task:" + taskFromManager);

        taskManager.deleteTask(taskFromManager.getTaskId());
        System.out.println("Delete:" + task);


    }
*/



























