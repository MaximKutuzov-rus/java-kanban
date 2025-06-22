package manager;

import exceptions.ManagerSaveException;
import tasks.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    public File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public void save() {
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8, false))) {
                writer.write("id,type,name,status,description,epic\n");
                List<Task> allTasks = new ArrayList<>(getAllTasks());
                allTasks.addAll(getAllEpics());
                allTasks.addAll(getAllSubtasks());

                for (Task task : allTasks) {
                    writer.write(toString(task) + '\n');
                }
            } catch (IOException exp) {
                throw new ManagerSaveException("Ошибка сохранения в файл");
            }
        } catch (ManagerSaveException exp) {
            System.out.println(exp.getMessage());
        }
    }

    public <T extends Task> String toString(T task) {
        if (task.getType().equals(TaskType.SUBTASK)) {
            Subtask subtask = (Subtask) task;
            return String.format("%d,%s,%s,%s,%s,%d", subtask.getId(), subtask.getType(), subtask.getName(),
                    subtask.getStatus(), subtask.getDescription(), subtask.getEpicId());
        } else {
            return String.format("%d,%s,%s,%s,%s", task.getId(), task.getType(), task.getName(), task.getStatus(),
                    task.getDescription());
        }
    }

    public Task fromString(String string) {
        String[] array = string.split(",");
        Task task;
        switch (array[1]) {
            case "TASK":
                task = new Task(array[2], array[4], Status.valueOf(array[3]), Integer.parseInt(array[0]));
                break;
            case "EPIC":
                task = new Epic(array[2], array[4], Integer.parseInt(array[0]));
                break;
            default:
                task = new Subtask(array[2], array[4], Status.valueOf(array[3]), Integer.parseInt(array[5]),
                        Integer.parseInt(array[0]));
        }
        return task;
    }

    public FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                reader.readLine();
                while (reader.ready()) {
                    Task task = fromString(reader.readLine());
                    if (task.getType().equals(TaskType.TASK)) {
                        Task copyTask = new Task(task.getName(), task.getDescription(), task.getStatus());
                        copyTask.setId(task.getId());
                        manager.tasks.put(copyTask.getId(),copyTask);
                    } else if (task.getType().equals(TaskType.EPIC)) {
                        Epic epic = (Epic) task;
                        Epic copyEpic = new Epic(epic.getName(), epic.getDescription());
                        copyEpic.setId(epic.getId());
                        manager.epics.put(copyEpic.getId(),copyEpic);
                        calculateEpicStatus(copyEpic);
                    } else if (task.getType().equals(TaskType.SUBTASK)) {
                        Subtask subtask = (Subtask) task;
                        Subtask copySubtask = new Subtask(subtask.getName(), subtask.getDescription(),
                                subtask.getStatus(), subtask.getEpicId());
                        copySubtask.setId(subtask.getId());
                        manager.subtasks.put(copySubtask.getId(),copySubtask);
                        Epic epicToAddId = manager.epics.get(copySubtask.getEpicId());
                        epicToAddId.getSubtasksIds().add(copySubtask.getId());
                        calculateEpicStatus(epicToAddId);
                    }
                }
            } catch (IOException exp) {
                throw new ManagerSaveException("Ошибка чтения файла");
            }
        } catch (ManagerSaveException exp) {
            System.out.println(exp.getMessage());
        }
        return manager;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    public static void main(String[] args) {
        try {
            File file = File.createTempFile("file", ".csv");
            FileBackedTaskManager manager = new FileBackedTaskManager(file);

            Task task1 = new Task("Переезд", "В новую квартиру", Status.NEW);
            Task task2 = new Task("Переезд", "В новый дом", Status.NEW);
            Epic epic1 = new Epic("Перевод денег", "Перевести деньги другу");
            Subtask subtask1 = new Subtask("Приложение банка", "Открыть приложение банка",
                    Status.IN_PROGRESS, 3);
            Subtask subtask2 = new Subtask("Открыть вкладку расходов", "Открытие вкладки расходов",
                    Status.NEW, 3);
            Subtask subtask3 = new Subtask("Отправка", "Отправка денег другу", Status.DONE, 3);
            Epic epic2 = new Epic("Пройти курс", "Пройти курс от ЯП");

            manager.addTask(task1);
            manager.addTask(task2);
            manager.addEpic(epic1);
            manager.addSubtask(subtask1);
            manager.addSubtask(subtask2);
            manager.addSubtask(subtask3);
            manager.addEpic(epic2);

            FileBackedTaskManager newManager = manager.loadFromFile(file);

            System.out.println(newManager.getAllTasks());
            System.out.println(newManager.getAllEpics());
            System.out.println(newManager.getAllSubtasks());
        } catch (IOException exp) {
            System.out.println(exp.getMessage());
        }
    }
}
