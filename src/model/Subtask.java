package model;

public class Subtask  extends Task {
    private Integer epicId;

    public Subtask(String nameTask, int epicId) {
        super(nameTask);
        this.epicId = epicId;
    }


    public Subtask(int taskId, String nameTask, String descriptionTask, Status status, int epicId) {
        super(taskId, nameTask, descriptionTask, status);
        this.epicId = epicId;

    }
    public Subtask(String nameTask, String descriptionTask, Status status){

    }

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return Subtask.this.getTaskId() + ","
                + Subtask.this.getType() + ","
                + Subtask.this.getNameTask() + ","
                + Subtask.this.getStatus() + ","
                + Subtask.this.getDescriptionTask() + ","
                + Subtask.this.epicId;
    }
}
