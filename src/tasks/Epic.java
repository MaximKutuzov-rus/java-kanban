package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subtasksIds = new ArrayList<>();
    protected LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        this(name,description);
        this.id = id;
    }

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }


    @Override
    public String toString() {
        if (endTime != null && startTime != null) {
            return '\n' +
                    "Название эпика: " + name + '\n' +
                    "Описание эпика: " + description + '\n' +
                    "ID эпика: " + id + '\n' +
                    "Статус эпика: " + status + '\n' +
                    "ID подзадач: " + subtasksIds + '\n' +
                    "Время начала выполнения эпика: " + startTime.format(formatter) + '\n' +
                    "Время окончания выполнения эпика: " + endTime.format(formatter) + '\n' +
                    '\n';
        } else {
            return  '\n' +
                    "Название эпика: " + name + '\n' +
                    "Описание эпика: " + description + '\n' +
                    "ID эпика: " + id + '\n' +
                    "Статус эпика: " + status + '\n' +
                    "ID подзадач: " + subtasksIds + '\n' +
                    '\n';
        }
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
