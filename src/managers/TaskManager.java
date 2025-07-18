package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.Collection;
import java.util.List;


public interface TaskManager {
    void addTask(Task task);

    Collection<Task> getAllTasks();

    void deleteAllTasks();

    void deleteTaskById(int id);

    Task getTaskById(int id);

    void updateTask(Task task);

    void addEpic(Epic epic);

    Collection<Epic> getAllEpics();

    void deleteAllEpics();

    void deleteEpicById(int id);

    Epic getEpicById(int id);

    void updateEpic(Epic epic);

    void addSubtask(Subtask subtask);

    Collection<Subtask> getAllSubtasks();

    void deleteAllSubtasks();

    void deleteSubtaskById(int id);

    Subtask getSubtaskById(int id);

    void updateSubtask(Subtask subtask);

    List<Subtask> getSubtasksOfEpic(int epicId);

    List<Task> getHistory();
}
