package service;

import java.io.File;

public class Managers {
    public static FileBackedTaskManager getFileBackedTaskManager() {
        return new FileBackedTaskManager(new File("resourses/taskT.csv"));
    }

    public static TaskManager getDefaultTaskManager() {

        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager();
    }

    public static FileBackedTaskManager getDefaultBackedTaskManager() {

        return new FileBackedTaskManager();
    }


}
