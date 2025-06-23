package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    protected int id = 1;
    protected final HashMap<Integer,Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void addTask(Task task) {
        Task copyTask = new Task(task.getName(), task.getDescription(), task.getStatus());
        copyTask.setId(id);
        tasks.put(copyTask.getId(),copyTask);
        id++;
    }

    @Override
    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return tasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        Task copyTask = new Task(task.getName(), task.getDescription(), task.getStatus(), task.getId());
        if (tasks.containsKey(copyTask.getId())) {
            tasks.put(copyTask.getId(),copyTask);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        Epic copyEpic = new Epic(epic.getName(), epic.getDescription());
        copyEpic.setId(id);
        epics.put(copyEpic.getId(),copyEpic);
        id++;
        calculateEpicStatus(copyEpic);
    }

    @Override
    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteEpicById(int id) {
        ArrayList<Integer> idsOfSubtasks = epics.get(id).getSubtasksIds();
        for (Integer idOfSubtask : idsOfSubtasks) {
            subtasks.remove(idOfSubtask);
            historyManager.remove(idOfSubtask);
        }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epics.get(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic copyEpic = new Epic(epic.getName(), epic.getDescription(), epic.getId());
        if (epics.containsKey(copyEpic.getId())) {
            ArrayList<Integer> ids = epics.get(copyEpic.getId()).getSubtasksIds();
            for (Integer id : ids) {
                subtasks.remove(id);
            }
            epics.put(copyEpic.getId(),copyEpic);
            calculateEpicStatus(copyEpic);
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        Subtask copySubtask = new Subtask(subtask.getName(), subtask.getDescription(), subtask.getStatus(),
                subtask.getEpicId());
        copySubtask.setId(id);
        subtasks.put(copySubtask.getId(),copySubtask);

        Epic epicToAddId = epics.get(copySubtask.getEpicId());
        epicToAddId.getSubtasksIds().add(copySubtask.getId());

        calculateEpicStatus(epicToAddId);

        id++;
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasksIds().clear();
            calculateEpicStatus(epic);
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Epic epicToDeleteSubtask = epics.get(subtasks.get(id).getEpicId());
        epicToDeleteSubtask.getSubtasksIds().removeIf(number -> number == id);
        subtasks.remove(id);
        calculateEpicStatus(epicToDeleteSubtask);
        historyManager.remove(id);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtasks.get(id);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Subtask copySubtask = new Subtask(subtask.getName(), subtask.getDescription(), subtask.getStatus(),
                subtask.getEpicId(), subtask.getId());
        if (subtasks.containsKey(copySubtask.getId())) {
            subtasks.put(copySubtask.getId(),copySubtask);
            calculateEpicStatus(epics.get(copySubtask.getEpicId()));
        }
    }

    @Override
    public List<Subtask> getSubtasksOfEpic(int epicId) {

        Epic epicWeNeed = epics.get(epicId);
        ArrayList<Subtask> subtasksWeNeed = new ArrayList<>();
        ArrayList<Integer> subtasksWeNeedIds = epicWeNeed.getSubtasksIds();
        for (Integer id : subtasksWeNeedIds) {
            subtasksWeNeed.add(subtasks.get(id));
        }
        return subtasksWeNeed;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public void calculateEpicStatus(Epic epic) {
        ArrayList<Integer> idsOfSubtasks = epic.getSubtasksIds();
        ArrayList<Subtask> subtasksInEpic = new ArrayList<>();
        for (Integer id : idsOfSubtasks) {
            subtasksInEpic.add(subtasks.get(id));
        }

        ArrayList<Status> statusesOfSubtasks = new ArrayList<>();

        for (Subtask subtask : subtasksInEpic) {
            statusesOfSubtasks.add(subtask.getStatus());
        }

        if (subtasksInEpic.isEmpty()) {
            epic.setStatus(Status.NEW);
        }

        int statusesSum = 0;

        for (Status status : statusesOfSubtasks) {
            if (status.equals(Status.NEW)) {
                statusesSum += 0;
            } else if (status.equals(Status.IN_PROGRESS)) {
                statusesSum += 1;
                break;
            } else if (status.equals((Status.DONE))) {
                statusesSum += 2;
            }
        }

        if (statusesSum == 0) {
            epic.setStatus(Status.NEW);
        } else if (statusesSum == 2 * statusesOfSubtasks.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }


    }
}
