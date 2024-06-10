package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected int taskId;
    protected String nameTask;
    protected String descriptionTask;
    protected Status status;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    protected Duration duration;

    protected LocalDateTime calculateEndTime() {
        return startTime.plus(duration);
    }

    // public Task() {
    //}

    // public Task(String nameTask) {
    //    this.nameTask = nameTask;
    // }

    public Task(String nameTask, String descriptionTask, Status status, LocalDateTime startTime, Duration duration) {
        this.taskId = 0;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = calculateEndTime();
        this.status = status;
    }

    public Task(int taskId, String nameTask, String descriptionTask, Status status, LocalDateTime startTime, Duration duration) {
        this.taskId = taskId;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = calculateEndTime();
        this.status = status;
    }

    //  public Task(int id, String nameTask, String descriptionTask, Status status) {
    //  }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        endTime = startTime.plus(duration);

    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        return startTime.plusMinutes(duration.toMinutes());
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
        LocalDateTime taskEndTime = LocalDateTime.parse(result[7]);

        switch (taskType) {
            case TASK:
                return new Task(id, name, taskDescription, taskStatus, taskstartTime, taskDuration);
            case SUBTASK:
                return new Subtask(id, name, taskDescription, taskStatus, taskstartTime, taskDuration, Integer.parseInt(result[8]));
            case EPIC:
                return new Epic(id, name, taskDescription, taskstartTime, taskDuration);
        }
        return null;

    }


    @Override
    public String toString() {
        return Task.this.getTaskId() + ","
                + Task.this.getType() + ","
                + Task.this.getNameTask() + ","
                + Task.this.getStatus() + ","
                + Task.this.getDescriptionTask() + ","
                + Task.this.getStartTime() + ","
                + Task.this.duration + ","
                + Task.this.getEndTime()
                + null;

    }
       /* return "Task{" +
                "ID=" + taskId +
                ", nameTask='" + nameTask + '\'' +
                ", descriptionTask='" + descriptionTask + '\'' +
                ", status=" + status +
                '}';
    }*/

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


    /* @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return taskId == task.taskId && Objects.equals(nameTask, task.nameTask) && Objects.equals(descriptionTask, task.descriptionTask) && status == task.status;
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
    }*/


}