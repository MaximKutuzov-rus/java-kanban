package tasks;

public class Subtask extends Task {
    protected final int idOfEpic;

    public Subtask(String name, String description, Status status, int idOfEpic) {
        super(name,description);
        this.idOfEpic = idOfEpic;
        this.status = status;
    }

    public Subtask(String name, String description, Status status, int idOfEpic, int id) {
        this(name,description,status,idOfEpic);
        this.id = id;
    }

    public int getIdOfEpic() {
        return idOfEpic;
    }

    @Override
    public String toString() {
        return "Название подзадачи: " + name + '\n' +
                "Описание подзадачи: " + description + '\n' +
                "ID подзадачи: " + id + '\n' +
                "Статус подзадачи: " + status + '\n' +
                "ID эпика: " + idOfEpic + '\n' + '\n';
    }
}
