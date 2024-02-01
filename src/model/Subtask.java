package model;

public class Subtask  extends Task {
     private  Epic epic;

    public Subtask(String nameTask, Epic epic) {
        super(nameTask);
        this.epic = epic;
    }

    public Subtask(int ID, String nameTask, String descriptionTask, Status status, Epic epic) {
        super(ID, nameTask, descriptionTask, status);
        this.epic = epic;
    }


    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }


}
