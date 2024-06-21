package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.adapters.InstantAdapter;
import http.adapters.LocalDateTimeAdapter;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;

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

    public static Gson getGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }


}
