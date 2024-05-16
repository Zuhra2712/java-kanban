package service;

import model.Epic;
import model.Subtask;
import model.Task;

public class Main {

    public static void main(String[] args) {
        HistoryManager historyManager = new InMemoryHistoryManager();
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

























    }


