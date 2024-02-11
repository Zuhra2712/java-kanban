package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    List<Task> history = new ArrayList<>();
    @Override
    public void addTaskToHistory(Task task) {
        if (history.isEmpty()){
            history.add(task);
        } else if (history.size() >= 10) {
            history.remove(0);
            history.add(task);
        }else {
            history.add(task);
        }
    }

    @Override
    public List<Task> history() {
        return new ArrayList<>(history);
    }
}
