import manager.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Переезд", "В новую квартиру", Status.NEW);
        Task task2 = new Task("Переезд", "В новый дом", Status.NEW);
        Task task3 = new Task("Переезд", "В новую квартиру", Status.IN_PROGRESS,2);
        Epic epic1 = new Epic("Перевод денег", "Перевести деньги другу");
        Subtask subtask1 = new Subtask("Приложение банка", "Открыть приложение банка", Status.IN_PROGRESS, 3);
        Epic epic2 = new Epic("jjjjj", "kkkkk");
        Epic epic3 = new Epic("jjj", "kkk",3);
        Subtask subtask2 = new Subtask("jfldsjf", "jfesjfslf", Status.IN_PROGRESS, 5);
        Subtask subtask3 = new Subtask("hsfhweiufyew", "hewhfefhew", Status.DONE, 5);
        Subtask subtask4 = new Subtask("hsfhweiufyew", "hewhfefhew", Status.NEW, 5,6);

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
    }
}