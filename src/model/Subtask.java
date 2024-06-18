package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private Integer epicId;

    public Subtask(String nameTask, String descriptionTask, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(nameTask, descriptionTask, status, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(int taskId, String nameTask, String descriptionTask, Status status, LocalDateTime startTime, Duration duration, int epicId) {
        super(taskId, nameTask, descriptionTask, status, startTime, duration);
        this.epicId = epicId;

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
                + Subtask.this.startTime + ","
                + Subtask.this.duration + ","
                + Subtask.this.epicId;
    }
}
