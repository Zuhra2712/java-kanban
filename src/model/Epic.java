package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final List<Integer> subtasks;
    private LocalDateTime endTime;

    public Epic(int id, String name, String description) {
        super.taskId = id;
        super.nameTask = name;
        super.status = Status.NEW;
        super.descriptionTask = description;
        super.startTime = getStartTime();
        subtasks = new ArrayList<>();


    }


    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime localDateTime) {
        this.endTime = localDateTime;
    }

    public void addSubtask(int id) {
        subtasks.add(id);
    }

    public void addSubTask(Subtask subtask) {
        subtasks.add(subtask.getTaskId());

    }

    public List<Integer> getSubtasksId() {
        return subtasks;
    }

    public void removeSubtask(Integer id) {
        subtasks.remove(id);
    }

    public void removeAllSubtasks() {
        subtasks.clear();
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }


    @Override
    public String toString() {
        return Epic.this.getTaskId() + ","
                + Epic.this.getType() + ","
                + Epic.this.getNameTask() + ","
                + Epic.this.getStatus() + ","
                + Epic.this.getDescriptionTask() + ","
                + Epic.this.startTime + ","
                + Epic.this.duration;

    }


}