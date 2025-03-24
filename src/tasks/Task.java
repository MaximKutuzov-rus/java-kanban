package tasks;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected Status status;

    public Task(String name, String description, Status status, int id) {
        this(name,description,status);
        this.id = id;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, Status status) {
        this(name,description);
        this.status = status;

    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Название задачи: " + name + '\n' +
                "Описание задачи: " + description + '\n' +
                "ID задачи: " + id + '\n' +
                "Статус задачи: " + status + '\n' + '\n';

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
