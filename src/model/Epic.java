package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String nameTask, ArrayList<Subtask> subtasks) {
        super(nameTask);
        this.subtasks = subtasks;
    }

    public Epic(int ID, String nameTask, String descriptionTask, Status status, ArrayList<Subtask> subtasks) {
        super(ID, nameTask, descriptionTask, status);
        this.subtasks = subtasks;
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void addSubTask(Subtask subtask) {
        subtasks.add(subtask);

    }

    public void removeSubTask(Subtask subtask) {
        subtasks.remove(subtask);
    }


    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = (ArrayList<Subtask>) subtasks;
    }


}
