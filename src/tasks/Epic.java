package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> idOfSubtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        super(name,description);
        this.id = id;
    }

    public ArrayList<Integer> getIdOfSubtasks() {
        return idOfSubtasks;
    }


    @Override
    public String toString() {
        return "Название эпика: " + name + '\n' +
                "Описание эпика: " + description + '\n' +
                "ID эпика: " + id + '\n' +
                "Статус эпика: " + status + '\n' +
                "ID подзадач: " + idOfSubtasks + '\n' + '\n';
    }

    @Override
    public int getId() {
        return super.getId();
    }
}
