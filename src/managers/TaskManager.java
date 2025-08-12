package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.List;
import java.util.Set;


public interface TaskManager {
    void addTask(Task task);

    List<Task> getAllTasks();

    void deleteAllTasks();

    void deleteTaskById(int id);

    Task getTaskById(int id);

    void updateTask(Task task);

    void addEpic(Epic epic);

    List<Epic> getAllEpics();

    void deleteAllEpics();

    void deleteEpicById(int id);

    Epic getEpicById(int id);

    void updateEpic(Epic epic);

    void addSubtask(Subtask subtask);

    List<Subtask> getAllSubtasks();

    void deleteAllSubtasks();

    void deleteSubtaskById(int id);

    Subtask getSubtaskById(int id);

    void updateSubtask(Subtask subtask);

    List<Subtask> getSubtasksOfEpic(int epicId);

    List<Task> getHistory();

    Set<Task> getPrioritizedTasks();
}
