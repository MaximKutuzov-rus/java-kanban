package tasks;

public class Subtask extends Task {
    protected final int epicId;

    public Subtask(String name, String description, Status status, int EpicId) {
        super(name,description);
        this.epicId = EpicId;
        this.status = status;
    }

    public Subtask(String name, String description, Status status, int EpicId, int id) {
        this(name,description,status, EpicId);
        this.id = id;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Название подзадачи: " + name + '\n' +
                "Описание подзадачи: " + description + '\n' +
                "ID подзадачи: " + id + '\n' +
                "Статус подзадачи: " + status + '\n' +
                "ID эпика: " + epicId + '\n' + '\n';
    }
}
