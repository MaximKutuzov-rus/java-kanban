package manager;

import tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> history = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        if (history.size() == 10) {
            history.removeFirst();
        }
        Task copyTask = new Task(task.getName(), task.getDescription(), task.getStatus());
        copyTask.setId(task.getId());
        history.addLast(copyTask);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
