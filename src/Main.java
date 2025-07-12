import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task1 = new Task("Переезд", "В новую квартиру", Status.NEW);
        Task task2 = new Task("Переезд", "В новый дом", Status.NEW);
        Epic epic1 = new Epic("Перевод денег", "Перевести деньги другу");
        Subtask subtask1 = new Subtask("Приложение банка", "Открыть приложение банка", Status.IN_PROGRESS, 3);
        Subtask subtask2 = new Subtask("Открыть вкладку расходов", "Открытие вкладки расходов", Status.NEW, 3);
        Subtask subtask3 = new Subtask("Отправка", "Отправка денег другу", Status.DONE, 3);
        Epic epic2 = new Epic("Пройти курс", "Пройти курс от ЯП");

        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        taskManager.addEpic(epic2);
//        taskManager.getTaskById(2);
//
//        taskManager.getSubtaskById(5);
//
//        taskManager.getEpicById(7);
//
//        taskManager.getTaskById(1);
//
//        taskManager.getSubtaskById(4);
//
//        taskManager.getTaskById(1);
//
//        taskManager.getSubtaskById(6);
//
//        taskManager.getEpicById(3);
//
//        taskManager.getEpicById(7);

//        taskManager.deleteSubtaskById(5);
//
//        taskManager.deleteEpicById(3);

        taskManager.deleteAllSubtasks();

//        System.out.println(taskManager.getHistory());

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());
    }
}