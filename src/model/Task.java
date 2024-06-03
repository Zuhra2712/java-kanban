package model;

public class Task {
    protected int taskId;
    protected String nameTask;
    protected String descriptionTask;
    protected Status status;

    public Task() {
    }

    public Task(String nameTask) {
        this.nameTask = nameTask;
    }

    public Task(int taskId, String nameTask, String descriptionTask, Status status) {
        this.taskId = taskId;
        this.nameTask = nameTask;
        this.descriptionTask = descriptionTask;
        this.status = status;
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

        switch (taskType) {
            case TASK:
                return new Task(id, name, taskDescription, taskStatus);
            case SUBTASK:
                return new Subtask(id, name, taskDescription, taskStatus, Integer.parseInt(result[5]));
            case EPIC:
                return new Epic(id, name, taskDescription, taskStatus);
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

        return taskId == task.taskId;
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