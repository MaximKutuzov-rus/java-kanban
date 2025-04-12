package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int id = 1;
    public HashMap<Integer,Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void addTask(Task task) {
        task.setId(id);
        tasks.put(task.getId(),task);
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
    public void deleteTaskUsingId(int id) {
        tasks.remove(id);
    }

    @Override
    public Task getTaskUsingId(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return tasks.get(id);
    }

    @Override
    public void updateTask(Task task) {
        if(tasks.containsKey(task.getId())) {
            tasks.put(task.getId(),task);
        }
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(id);
        epics.put(epic.getId(),epic);
        id++;
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
    public void deleteEpicUsingId(int id) {
        ArrayList<Integer> idsOfSubtasks = epics.get(id).getSubtasksIds();
        for (Integer idOfSubtask : idsOfSubtasks) {
            subtasks.remove(idOfSubtask);
        }
        epics.remove(id);
    }

    @Override
    public Epic getEpicUsingId(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epics.get(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        if(epics.containsKey(epic.getId())) {
            ArrayList<Integer> ids = epics.get(epic.getId()).getSubtasksIds();
            for(Integer id : ids) {
                subtasks.remove(id);
            }
            epics.put(epic.getId(),epic);
            changeEpicStatus(epic);
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        subtask.setId(id);
        subtasks.put(subtask.getId(),subtask);

        Epic epicToAddId = epics.get(subtask.getEpicId());
        epicToAddId.getSubtasksIds().add(subtask.getId());

        changeEpicStatus(epicToAddId);

        id++;
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for(Epic epic : epics.values()) {
            epic.getSubtasksIds().clear();
            changeEpicStatus(epic);
        }
    }

    @Override
    public void deleteSubtaskUsingId(int id) {
        Epic epicToDeleteSubtask = epics.get(subtasks.get(id).getEpicId());
        epicToDeleteSubtask.getSubtasksIds().removeIf(number -> number == id);
        subtasks.remove(id);
        changeEpicStatus(epicToDeleteSubtask);
    }

    @Override
    public Subtask getSubtaskUsingId(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtasks.get(id);
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if(subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(),subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {

        Epic epicWeNeed = epics.get(epicId);
        ArrayList<Subtask> subtasksWeNeed = new ArrayList<>();
        ArrayList<Integer> subtasksWeNeedIds = epicWeNeed.getSubtasksIds();
        for(Integer id : subtasksWeNeedIds) {
            subtasksWeNeed.add(subtasks.get(id));
        }
        return subtasksWeNeed;
    }

    @Override
    public ArrayList<Task> getHistory() {
        return (ArrayList<Task>) historyManager.getHistory();
    }

    public void changeEpicStatus(Epic epic) {
        ArrayList<Integer> idsOfSubtasks = epic.getSubtasksIds();
        ArrayList<Subtask> subtasksInEpic = new ArrayList<>();
        for(Integer id : idsOfSubtasks) {
            subtasksInEpic.add(subtasks.get(id));
        }

        ArrayList<Status> statusesOfSubtasks = new ArrayList<>();

        for(Subtask subtask : subtasksInEpic) {
            statusesOfSubtasks.add(subtask.getStatus());
        }

        if(subtasksInEpic.isEmpty()) {
            epic.setStatus(Status.NEW);
        }

        int statusesSum = 0;

        for(Status status : statusesOfSubtasks) {
            if(status.equals(Status.NEW)) {
                statusesSum += 0;
            } else if(status.equals(Status.IN_PROGRESS)) {
                statusesSum += 1;
            } else if(status.equals((Status.DONE))) {
                statusesSum += 2;
            }
        }

        if(statusesSum == 0) {
            epic.setStatus(Status.NEW);
        } else if(statusesSum == 2*statusesOfSubtasks.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }


    }
}
