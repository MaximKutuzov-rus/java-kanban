package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;

public interface TaskManager {
    void addTask(Task task);

    Collection<Task> getAllTasks();

    void deleteAllTasks();

    void deleteTaskUsingId(int id);

    Task getTaskUsingId(int id);

    void updateTask(Task task);

    void addEpic(Epic epic);

    Collection<Epic> getAllEpics();

    void deleteAllEpics();

    void deleteEpicUsingId(int id);

    Epic getEpicUsingId(int id);

    void updateEpic(Epic epic);

    void addSubtask(Subtask subtask);

    Collection<Subtask> getAllSubtasks();

    void deleteAllSubtasks();

    void deleteSubtaskUsingId(int id);

    Subtask getSubtaskUsingId(int id);

    void updateSubtask(Subtask subtask);

    ArrayList<Subtask> getSubtasksOfEpic(int epicId);

    ArrayList<Task> getHistory();
}
