package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

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

    public Subtask(String name, String description, Status status, int epicId, int id, Duration duration,
                   LocalDateTime startTime) {
        this(name, description, status, epicId, id);
        this.duration = duration;
        this.startTime = startTime;
    }

    public Subtask(String name, String description, Status status, int epicId, Duration duration,
                   LocalDateTime startTime) {
        this(name, description, status, epicId);
        this.duration = duration;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        if (duration != null && startTime != null) {
            return '\n' +
                    "Название подзадачи: " + name + '\n' +
                    "Описание подзадачи: " + description + '\n' +
                    "ID подзадачи: " + id + '\n' +
                    "Статус подзадачи: " + status + '\n' +
                    "ID эпика: " + epicId + '\n' +
                    "Время старта выполнения подзадачи: " + startTime.format(formatter) + '\n' +
                    "Время окончания выполнения задачи: " + getEndTime().format(formatter) + '\n' +
                    "Продолжительность выполнения задачи: " + duration.toHours() + " ч. " + duration.toMinutesPart() +
                    " мин." + '\n' + '\n';
        } else {
            return '\n' +
                    "Название подзадачи: " + name + '\n' +
                    "Описание подзадачи: " + description + '\n' +
                    "ID подзадачи: " + id + '\n' +
                    "Статус подзадачи: " + status + '\n' +
                    "ID эпика: " + epicId + '\n' +
                    '\n';
        }
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}
