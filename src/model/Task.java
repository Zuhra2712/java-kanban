package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected int taskId;
    protected String nameTask;
    protected String descriptionTask;
    protected Status status;
    protected LocalDateTime startTime;
    protected Duration duration;

    public Task() {

    }

    public Task(String nameTask, String descriptionTask, Status status, LocalDateTime startTime, Duration duration) {
        this.taskId = 0;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.startTime = startTime;
        this.duration = duration;
        this.status = status;
    }

    public Task(int taskId, String nameTask, String descriptionTask, Status status, LocalDateTime startTime, Duration duration) {
        this.taskId = taskId;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.startTime = startTime;
        this.duration = duration;
        this.status = status;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime != null) {
            return startTime.plus(duration);
        } else {
            System.out.println("Отсутвует стартовое время");
        }
        return null;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;

    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }


    public TaskType getType() {
        return TaskType.TASK;
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

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public static Task taskFromString(String value) {
        final String[] result = value.split(",");
        int id = Integer.parseInt(result[0]);
        TaskType taskType = TaskType.valueOf(result[1].trim());
        String name = result[2];
        Status taskStatus = Status.valueOf(result[3].trim());
        String taskDescription = result[4];
        LocalDateTime taskstartTime = LocalDateTime.parse(result[5]);
        Duration taskDuration = Duration.parse(result[6]);

        switch (taskType) {
            case TASK:
                return new Task(id, name, taskDescription, taskStatus, taskstartTime, taskDuration);
            case SUBTASK:
                return new Subtask(id, name, taskDescription, taskStatus, taskstartTime, taskDuration, Integer.parseInt(result[8]));
            case EPIC:
                return new Epic(id, name, taskDescription);
        }
        return null;

    }


    // или тут надо было с Optional?


    @Override
    public String toString() {
        return Task.this.getTaskId() + ","
                + Task.this.getType() + ","
                + Task.this.getNameTask() + ","
                + Task.this.getStatus() + ","
                + Task.this.getDescriptionTask() + ","
                + Task.this.getStartTime() + ","
                + Task.this.duration;

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Task task = (Task) object;

        return this.nameTask.equals(task.nameTask)
                && this.descriptionTask.equals(task.descriptionTask)
                && this.status.equals(task.status)
                && this.startTime.equals(task.startTime)
                && this.duration.equals(task.duration);
    }

    @Override
    public int hashCode() {
        return taskId;
    }


}