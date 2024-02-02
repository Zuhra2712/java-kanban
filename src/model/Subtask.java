package model;

public class Subtask  extends Task {
     private  Epic epic;

    public Subtask(String nameTask, Epic epic) {
        super(nameTask);
        this.epic = epic;
    }

    public Subtask(int taskId, String nameTask, String descriptionTask, Status status) {
        super(taskId, nameTask, descriptionTask, status);
    }
    public Subtask(String nameTask, String descriptionTask, Status status){

    }

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }


}
