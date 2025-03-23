package Tasks;

import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected Statuses status;

    public Task(String name,String description,Statuses status,int id) {
        this(name,description,status);
        this.id = id;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name,String description,Statuses status) {
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
}
