package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {
    protected String name;
    protected String description;
    protected int id;
    protected Status status;
    protected Duration duration;
    protected LocalDateTime startTime;
    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");



    public Task(String name, String description, Status status, int id) {
        this(name,description,status);
        this.id = id;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task(String name, String description, Status status) {
        this(name, description);
        this.status = status;

    }

    public Task(String name, String description, Status status, int id, Duration duration, LocalDateTime startTime) {
        this(name, description, status, id);
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this(name, description, status);
        this.duration = duration;
        this.startTime = startTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public TaskType getType() {
        return TaskType.TASK;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public boolean checkIntersection(Task task) {
        if (this.getStartTime() == null || task.getStartTime() == null) {
            return false;
        } else if (this.getStartTime().isBefore(task.getStartTime()) && this.getEndTime().isAfter(task.getStartTime())) {
            return true;
        } else if (this.getStartTime().isAfter(task.getStartTime()) && task.getEndTime().isAfter(this.getStartTime())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        if(duration != null && startTime != null) {
            return  '\n' +
                    "Название задачи: " + name + '\n' +
                    "Описание задачи: " + description + '\n' +
                    "ID задачи: " + id + '\n' +
                    "Статус задачи: " + status + '\n' +
                    "Время старта выполнения задачи: " + startTime.format(formatter) + '\n' +
                    "Время окончания выполнения задачи: " + getEndTime().format(formatter) + '\n' +
                    "Продолжительность выполнения задачи: " + duration.toHours() + " ч. " + duration.toMinutesPart() +
                    " мин." + '\n' + '\n';
        } else {
            return  '\n' +
                    "Название задачи: " + name + '\n' +
                    "Описание задачи: " + description + '\n' +
                    "ID задачи: " + id + '\n' +
                    "Статус задачи: " + status + '\n' +
                    '\n';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) &&
                status == task.status && Objects.equals(duration, task.duration) && Objects.equals(startTime,
                task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, duration, startTime);
    }
}
