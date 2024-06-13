package model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private final List<Integer> subtasks;

    public Epic(String title, String description) {
        super(title, description, Status.NEW, LocalDateTime.now().withNano(0), Duration.ofMinutes(0));
        subtasks = new ArrayList<>();
    }

    public Epic(int id, String name, String description) {
        super(id, name, description, Status.NEW, LocalDateTime.now().withNano(0), Duration.ofMinutes(0));
        subtasks = new ArrayList<>();
    }

    public Epic(int id, String name, String description, LocalDateTime startTime, Duration duration) {
        super(id, name, description, Status.NEW, startTime, duration);
        subtasks = new ArrayList<>();
    }

    public Epic(int id, String name, String description, Status status, List<Integer> subtasks) {
        super();
        this.subtasks = subtasks;
    }

    public void setEndTime(LocalDateTime localDateTime) {
        this.endTime = localDateTime;
    }

    public void addSubtask(int id) {
        subtasks.add(id);
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


    /* @Override
     public LocalDateTime getEndTime() {
         LocalDateTime result = null;
         for (Subtask subtask : subtasks) {
            endTime =subtask.getEndTimeSubtask();
            result = result.plusMinutes(endTime.getMinute());

         }
         return result;
     }

     public void setEndTime(LocalDateTime endTime) {
         this.endTime = endTime;
     }


   //  public Epic(String nameTask, ArrayList<Subtask> subtasks) {
  //       super(nameTask);
   //      this.subtasks = subtasks;
   //  }

  //   public Epic(int ID, String nameTask, String descriptionTask, Status status) {
 //        this.setTaskId(ID);
  //       this.setNameTask(nameTask);
  //       this.setDescriptionTask(descriptionTask);
 //        this.setStatus(status);
     }

  //   public Epic(String name) {
   //      this.setNameTask(name);
  //   }

  //   public Epic(int ID, String nameTask, String descriptionTask, Status status, ArrayList<Subtask> subtasks) {
  //       super(ID, nameTask, descriptionTask, status);
  //       this.subtasks = subtasks;
 //    }

   //  public Epic(String nameTask, String descriptionTask, Status status, ArrayList<Subtask> subtasks) {
   //  }

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

     public TaskType getType() {
         return TaskType.EPIC;
     }
 */
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

    public void addSubTask(Subtask subtask) {
        subtasks.add(subtask.getTaskId());

    }
}
