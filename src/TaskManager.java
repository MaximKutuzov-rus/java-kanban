import Tasks.Epic;
import Tasks.Statuses;
import Tasks.Subtask;
import Tasks.Task;

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

    public Object printAllTasks() {
        return tasks.values();
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteTaskUsingId(int id) {
        tasks.remove(id);
    }

    public Object getTaskUsingId(int id) {
        if(tasks.containsKey(id)) {
            return tasks.get(id);
        } else {
            return "No such task";
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

    public Object printAllEpics() {
        return epics.values();
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

    public Object getEpicUsingId(int id) {
        if(epics.containsKey(id)) {
            return epics.get(id);
        } else {
            return "No such task";
        }
    }

    public void updateEpic(Epic epic) {
        ArrayList<Integer> ids = epics.get(epic.getId()).getIdOfSubtasks();
        for(Integer id : ids) {
            subtasks.remove(id);
        }
        if(epics.containsKey(epic.getId())) {
            epics.put(epic.getId(),epic);
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

    public Object printAllSubtasks() {
       return subtasks.values();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for(Epic epic : epics.values()) {
            epic.getIdOfSubtasks().clear();
        }
    }

    public void deleteSubtaskUsingId(int id) {
        ArrayList<Integer> idsOfEpic = epics.get(subtasks.get(id).getIdOfEpic()).getIdOfSubtasks();
        for(Integer idInEpic : idsOfEpic) {
            if(idInEpic == id) {
                idsOfEpic.remove(idInEpic);
            }
        }
        subtasks.remove(id);
    }

    public Object getSubtaskUsingId(int id) {
        if(subtasks.containsKey(id)) {
            return subtasks.get(id);
        } else {
            return "No such task";
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

    public Object getSubtasksOfEpic(int epicId) {

        Epic epicWeNeed = epics.get(epicId);
        ArrayList<Integer> idsWeNeed = epicWeNeed.getIdOfSubtasks();
        for(Integer id : idsWeNeed) {
            return subtasks.get(id);
        }
        return 0;
    }

    public void changeEpicStatus(Epic epic) {
        ArrayList<Integer> idsOfSubtasks = epic.getIdOfSubtasks();
        ArrayList<Subtask> subtasksInEpic = new ArrayList<>();
        for(Integer id : idsOfSubtasks) {
            subtasksInEpic.add(subtasks.get(id));
        }

        ArrayList<Statuses> statusesOfSubtasks = new ArrayList<>();

        for(Subtask subtask : subtasksInEpic) {
            statusesOfSubtasks.add(subtask.getStatus());
        }

        if(subtasksInEpic.isEmpty()) {
            epic.setStatus(Statuses.NEW);
        }

        int statusesSum = 0;

        for(Statuses status : statusesOfSubtasks) {
            if(status.equals(Statuses.NEW)) {
                statusesSum += 0;
            } else if(status.equals(Statuses.IN_PROGRESS)) {
                statusesSum += 1;
            } else if(status.equals((Statuses.DONE))) {
                statusesSum += 2;
            }
        }

        if(statusesSum == 0) {
            epic.setStatus(Statuses.NEW);
        } else if(statusesSum == 2*statusesOfSubtasks.size()) {
            epic.setStatus(Statuses.DONE);
        } else {
            epic.setStatus(Statuses.IN_PROGRESS);
        }


    }
}
