package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import static managers.InMemoryTaskManager.formatter;

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
                    "Продолжительность выполнения задачи: " + duration.toHours() + " ч. " + duration.toMinutesPart() +
                    " мин." + '\n' + '\n';
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksIds, epic.subtasksIds) && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksIds, endTime);
    }
}
