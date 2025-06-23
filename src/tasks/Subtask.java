package tasks;

public class Subtask extends Task {
    protected int epicId;

    public Subtask(String name, String description, Status status, int epicId) {
        super(name,description);
        this.epicId = epicId;
        this.status = status;
    }

    public Subtask(String name, String description, Status status, int epicId, int id) {
        this(name,description,status, epicId);
        this.id = id;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return  "Название подзадачи: " + name + '\n' +
                "Описание подзадачи: " + description + '\n' +
                "ID подзадачи: " + id + '\n' +
                "Статус подзадачи: " + status + '\n' +
                "ID эпика: " + epicId + '\n' + '\n';
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }
}
