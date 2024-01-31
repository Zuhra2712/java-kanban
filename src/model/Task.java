package model;

import java.util.Objects;

public class Task {
    private int ID;
    private String nameTask;
    private String descriptionTask;
    private Status status;

    public Task(String nameTask) {
        this.nameTask = nameTask;
    }

    public Task(int ID, String nameTask, String descriptionTask, Status status) {
        this.ID = ID;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.status = status;
    }

    public Epic getEpic() {
        return null;
    }

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Task{" +
                "ID=" + ID +
                ", nameTask='" + nameTask + '\'' +
                ", descriptionTask='" + descriptionTask + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return ID == task.ID && Objects.equals(nameTask, task.nameTask) && Objects.equals(descriptionTask, task.descriptionTask) && status == task.status;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (nameTask != null) {
            hash = hash + nameTask.hashCode();
        }
        hash = hash * 31;
        if (descriptionTask != null) {
            hash = hash + descriptionTask.hashCode();
        }
        hash = hash * 31;
        return hash;
    }


}