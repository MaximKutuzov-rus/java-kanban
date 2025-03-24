package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
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

    public void getAllTasks() {
        System.out.println(tasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteTaskUsingId(int id) {
        tasks.remove(id);
    }

    public void getTaskUsingId(int id) {
        if(tasks.containsKey(id)) {
            System.out.println(tasks.get(id));
        } else {
            System.out.println("No such task");
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

    public void getAllEpics() {
        System.out.println(epics.values());
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteEpicUsingId(int id) {
        ArrayList<Integer> idsOfSubtasks = epics.get(id).getIdOfSubtasks();
        for (Integer idOfSubtask : idsOfSubtasks) {
            subtasks.remove(idOfSubtask);
        }
        epics.remove(id);
    }

    public void getEpicUsingId(int id) {
        if(epics.containsKey(id)) {
            System.out.println(epics.get(id));
        } else {
            System.out.println("No such task");
        }
    }

    public void updateEpic(Epic epic) {
        if(epics.containsKey(epic.getId())) {
            ArrayList<Integer> ids = epics.get(epic.getId()).getIdOfSubtasks();
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

        Epic epicToAddId = epics.get(subtask.getIdOfEpic());
        epicToAddId.getIdOfSubtasks().add(subtask.getId());

        changeEpicStatus(epicToAddId);

        id++;
    }

    public void getAllSubtasks() {
        System.out.println(subtasks.values());
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for(Epic epic : epics.values()) {
            epic.getIdOfSubtasks().clear();
            changeEpicStatus(epic);
        }
    }

    public void deleteSubtaskUsingId(int id) {
        Epic epicToDeleteSubtask = epics.get(subtasks.get(id).getIdOfEpic());
        epicToDeleteSubtask.getIdOfSubtasks().removeIf(number -> number == id);
        subtasks.remove(id);
        changeEpicStatus(epicToDeleteSubtask);
    }

    public void getSubtaskUsingId(int id) {
        if(subtasks.containsKey(id)) {
            System.out.println(subtasks.get(id));
        } else {
            System.out.println("No such task");
        }
    }

    public void updateSubtask(Subtask subtask) {
        if(subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(),subtask);
            changeEpicStatus(epics.get(subtask.getIdOfEpic()));
        } else {
            System.out.println("No such task");
        }
    }

    public void getSubtasksOfEpic(int epicId) {

        Epic epicWeNeed = epics.get(epicId);
        ArrayList<Integer> idsWeNeed = epicWeNeed.getIdOfSubtasks();
        for(Integer id : idsWeNeed) {
            System.out.println(subtasks.get(id));
        }
    }

    public void changeEpicStatus(Epic epic) {
        ArrayList<Integer> idsOfSubtasks = epic.getIdOfSubtasks();
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
