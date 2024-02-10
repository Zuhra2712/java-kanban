package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> history = new ArrayList<>();
    @Override
    public void addTaskToHistory(Task task) {
        if (history.isEmpty()){
            history = new ArrayList<>();
            history.add(task);
        } else if (history.size() >= 10) {
            history.remove(0);
            history.add(task);
        }
    }

    @Override
    public List<Task> history() {
        return history;
    }
}
