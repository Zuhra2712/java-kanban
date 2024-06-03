package model;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Subtask> subtasks = new ArrayList<>();

    public Epic(String nameTask, ArrayList<Subtask> subtasks) {
        super(nameTask);
        this.subtasks = subtasks;
    }

    public Epic(int ID, String nameTask, String descriptionTask, Status status) {
        this.setNameTask(nameTask);
        this.setDescriptionTask(descriptionTask);
        this.setStatus(status);
    }

    public Epic(String name) {
        this.setNameTask(name);
    }

    public Epic(int ID, String nameTask, String descriptionTask, Status status, ArrayList<Subtask> subtasks) {
        super(ID, nameTask, descriptionTask, status);
        this.subtasks = subtasks;
    }

    public Epic(String nameTask, String descriptionTask, Status status, ArrayList<Subtask> subtasks) {
    }

    public ArrayList<Subtask> getSubtasks() {

        return new ArrayList<>(subtasks);
    }

    public void addSubTask(Subtask subtask) {
        subtasks.add(subtask);

    }

    public void removeSubTask(Subtask subtask) {
        subtasks.remove(subtask);
    }


    public void setSubtasks(ArrayList<Subtask> subtasks) {
        this.subtasks = subtasks;
    }


    public void removeAllSubtasks() {
        subtasks.clear();
    }


}
