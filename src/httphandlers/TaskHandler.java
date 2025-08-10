package httphandlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.AddTaskException;
import exceptions.NotFoundException;
import managers.TaskManager;
import tasks.Task;

import java.io.IOException;

public class TaskHandler extends BaseHttpHandler {
    public TaskHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException{
        String method = exchange.getRequestMethod();
        String[] uri = exchange.getRequestURI().getPath().split("/");
        int uriLength = uri.length;
        switch (method) {
            case "GET":
                if (uriLength == 2) {
                    if (!taskManager.getAllTasks().isEmpty()) {
                        sendText(exchange, gson.toJson(taskManager.getAllTasks()));
                    } else {
                        sendText(exchange,"");
                    }
                } else if (uriLength == 3) {
                    try {
                        sendText(exchange, gson.toJson(taskManager.getTaskById(Integer.parseInt(uri[2]))));
                    } catch (NumberFormatException e) {
                        sendRequestError(exchange,
                                "Ошибка в формате запроса, вторым в пути запроса должно вводиться целое число");
                    } catch (NotFoundException e) {
                        sendNotFound(exchange, e.getMessage());
                    }
                } else {
                    sendRequestError(exchange, "Обработка такого запроса не предусмотрена");
                }
            case "POST":
                Task task = fromJsonToTask(exchange, Task.class);
                try {
                    if (!task.getName().isEmpty() && !task.getDescription().isEmpty() && !(task.getStatus() == null) &&
                        task.getId() == 0) {
                        taskManager.addTask(task);
                        sendEmptyBody(exchange);
                    } else if (!task.getName().isEmpty() && !task.getDescription().isEmpty() && !(task.getStatus() == null) &&
                            task.getId() != 0) {
                        taskManager.updateTask(task);
                        sendEmptyBody(exchange);
                    } else {
                        sendRequestError(exchange, "Неправильный запрос");
                    }
                } catch (AddTaskException e) {
                    sendHasOverlaps(exchange, e.getMessage());
                } catch (NotFoundException exp) {
                    sendNotFound(exchange, exp.getMessage());
                }
            case "DELETE":
                if (uriLength == 3) {
                    try {
                        taskManager.getTaskById(Integer.parseInt(uri[2])); //чтобы поймать исключение если нет такого элемента
                        taskManager.deleteTaskById(Integer.parseInt(uri[2]));
                        sendText(exchange, "");
                    } catch (NumberFormatException e) {
                        sendRequestError(exchange,
                                "Ошибка в формате запроса, вторым в пути запроса должно вводиться целое число");
                    } catch (NotFoundException exp) {
                        sendNotFound(exchange, exp.getMessage());
                    }
                } else {
                    sendRequestError(exchange, "Обработка такого запроса не предусмотрена");
                }
            default:
                sendRequestError(exchange, "Некорректный метод запроса");
        }
    }
}
