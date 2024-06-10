package service;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

import static service.ClassForTesting.printAllTasks;
import static service.FileBackedTaskManager.loadFromFile;

public class Main {

    public static final String PATH = "resources/task.csv";


    public static void main(String[] args) {
        Path path = Paths.get("resourses/taskT.csv");
        File file = new File(String.valueOf(path));

        FileBackedTaskManager taskManager = Managers.getDefaultBackedTaskManager();

        Task task1 = new Task("task1", "taskDescription1", Status.NEW,
                LocalDateTime.parse("2021-06-03T13:54"), Duration.ofMinutes(100));
        Task task2 = new Task("task2", "taskDescription2", Status.NEW,
                LocalDateTime.parse("2021-05-03T13:54"), Duration.ofMinutes(100));
        Task task3 = new Task("task3", "taskDescription3", Status.NEW,
                LocalDateTime.parse("2021-06-03T17:54"), Duration.ofMinutes(100));
        Task task4 = new Task("task4", "taskDescription4", Status.NEW,
                LocalDateTime.parse("2022-06-03T17:54"), Duration.ofMinutes(100));

        Subtask subtask1 = new Subtask("subtask1", "subtaskDescription1", Status.NEW,
                LocalDateTime.parse("2020-06-03T13:54"), Duration.ofMinutes(100), 1);
        Subtask subtask2 = new Subtask("subtask2", "subtaskDescription2", Status.NEW,
                LocalDateTime.parse("2020-05-03T13:56"), Duration.ofMinutes(100), 1);
        Subtask subtask3 = new Subtask("subtask3", "subtaskDescription3", Status.NEW,
                LocalDateTime.parse("2020-06-03T20:00"), Duration.ofMinutes(100), 1);

        Epic epic1 = new Epic("epic1", "epicDescription1");
        Epic epic2 = new Epic("epic2", "epicDescription2");
        Epic epic3 = new Epic("epic3", "taskDescription3");

        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.createEpic(epic3);

        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        taskManager.get(task1.getTaskId());
        taskManager.get(task2.getTaskId());
        taskManager.get(task3.getTaskId());

        taskManager.getEpic(epic1.getTaskId());
        taskManager.getEpic(epic2.getTaskId());
        taskManager.getEpic(epic3.getTaskId());

        taskManager.getSubtask(subtask1.getTaskId());
        taskManager.getSubtask(subtask2.getTaskId());
        taskManager.getSubtask(subtask3.getTaskId());

        System.out.println("--------------");

        FileBackedTaskManager restoredManager = new FileBackedTaskManager(file);
        restoredManager = loadFromFile(file);
        printAllTasks(restoredManager);
        System.out.println("restored tasks printed");

        restoredManager.createTask(task4);
        System.out.println("-----------------------");

        printAllTasks(restoredManager);

        restoredManager.get(task3.getTaskId());

        for (Task item : restoredManager.getHistory()) {
            System.out.print(item.getTaskId() + ",");
        }
        System.out.println("\n");
    }


}

        /*Task task1 = manager.createTask(new Task(1, "Таск1", "Описание таск1", Status.NEW));
        Task task2 = manager.createTask(new Task(2, "Таск2", "Описание таск2", Status.NEW));
        Epic epic1 = manager.createEpic(new Epic(3, "Эпик1", "Описание Эпик1", Status.NEW));
        Epic epic2 = manager.createEpic(new Epic(4, "Эпик2", "Описание Эпик2", Status.NEW));
        Subtask subTask1 = manager.createSubtask(new Subtask(5, "Subtask1", "D5", Status.NEW, 3));
        Subtask subTask2 = manager.createSubtask(new Subtask(6, "Subtask2", "D6", Status.NEW, 3));
        Subtask subTask3 = manager.createSubtask(new Subtask(7, "Subtask3", "D7", Status.NEW, 3));

        manager.get(task1.getTaskId());
        manager.get(task2.getTaskId());
        manager.getEpic(epic1.getTaskId());
        manager.getEpic(epic2.getTaskId());
        manager.getSubtask(subTask1.getTaskId());
        manager.getSubtask(subTask2.getTaskId());
        manager.getSubtask(subTask3.getTaskId());

        printAllTasks(manager);
        System.out.println("-------------------------------------------------------------------");

        FileBackedTaskManager restoredManager = new FileBackedTaskManager(file);
        restoredManager = loadFromFile(restoredManager.file);
        printAllTasks(restoredManager);
        System.out.println("restored tasks printed");
        Task task3 = restoredManager.createTask(new Task(3, "task 3 description", "D3", Status.NEW));
        restoredManager.createTask(task3);
        System.out.println("*******************************************************************");


        printAllTasks(restoredManager);
        restoredManager.get(task3.getTaskId());
        for (Task item : restoredManager.getHistory()) {
            System.out.print(item.getTaskId() + ",");
        }
        System.out.println("\n");

        System.out.println("--------print subTaskIds arrays (if any)------------");

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



























