package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;
    public HashMap<Integer,Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    public HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public void addTask (Task task) {
        task.setId(id);
        tasks.put(task.getId(),task);
        id++;
    }

    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteTaskUsingId(int id) {
        tasks.remove(id);
    }

    public Task getTaskUsingId(int id) {
        if(tasks.containsKey(id)) {
            return tasks.get(id);
        } else {
            System.out.println("No such task");
            return null;
        }
    }

    public void updateTask(Task task) {
        if(tasks.containsKey(task.getId())) {
            tasks.put(task.getId(),task);
        } else {
            System.out.println("No such task");
        }
    }

    public void addEpic (Epic epic) {
        epic.setId(id);
        epics.put(epic.getId(),epic);
        id++;
    }

    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteEpicUsingId(int id) {
        ArrayList<Integer> idsOfSubtasks = epics.get(id).getSubtasksIds();
        for (Integer idOfSubtask : idsOfSubtasks) {
            subtasks.remove(idOfSubtask);
        }
        epics.remove(id);
    }

    public Epic getEpicUsingId(int id) {
        if(epics.containsKey(id)) {
            return epics.get(id);
        } else {
            System.out.println("No such task");
            return null;
        }
    }

    public void updateEpic(Epic epic) {
        if(epics.containsKey(epic.getId())) {
            ArrayList<Integer> ids = epics.get(epic.getId()).getSubtasksIds();
            for(Integer id : ids) {
                subtasks.remove(id);
            }
            epics.put(epic.getId(),epic);
            changeEpicStatus(epic);
        } else {
            System.out.println("No such task");
        }
    }

    public void addSubtask (Subtask subtask) {
        subtask.setId(id);
        subtasks.put(subtask.getId(),subtask);

        Epic epicToAddId = epics.get(subtask.getEpicId());
        epicToAddId.getSubtasksIds().add(subtask.getId());

        changeEpicStatus(epicToAddId);

        id++;
    }

    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for(Epic epic : epics.values()) {
            epic.getSubtasksIds().clear();
            changeEpicStatus(epic);
        }
    }

    public void deleteSubtaskUsingId(int id) {
        Epic epicToDeleteSubtask = epics.get(subtasks.get(id).getEpicId());
        epicToDeleteSubtask.getSubtasksIds().removeIf(number -> number == id);
        subtasks.remove(id);
        changeEpicStatus(epicToDeleteSubtask);
    }

    public Subtask getSubtaskUsingId(int id) {
        if(subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else {
            System.out.println("No such task");
            return null;
        }
    }

    public void updateSubtask(Subtask subtask) {
        if(subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(),subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
        } else {
            System.out.println("No such task");
        }
    }

    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {

        Epic epicWeNeed = epics.get(epicId);
        ArrayList<Subtask> subtasksWeNeed = new ArrayList<>();
        ArrayList<Integer> subtasksWeNeedIds = epicWeNeed.getSubtasksIds();
        for(Integer id : subtasksWeNeedIds) {
            subtasksWeNeed.add(subtasks.get(id));
        }
        return subtasksWeNeed;
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
