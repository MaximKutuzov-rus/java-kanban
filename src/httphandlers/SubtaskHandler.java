package httphandlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.AddTaskException;
import exceptions.NotFoundException;
import managers.TaskManager;
import tasks.Subtask;

import java.io.IOException;

public class SubtaskHandler extends BaseHttpHandler {
    public SubtaskHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] uri = exchange.getRequestURI().getPath().split("/");
        int uriLength = uri.length;
        switch (method) {
            case "GET":
                if (uriLength == 2) {
                    sendText(exchange, gson.toJson(taskManager.getAllSubtasks()));
                } else if (uriLength == 3) {
                    try {
                        sendText(exchange, gson.toJson(taskManager.getSubtaskById(Integer.parseInt(uri[2]))));
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
                Subtask subtask = fromJsonToTask(exchange, Subtask.class);
                try {
                    if (!subtask.getName().isEmpty() && !subtask.getDescription().isEmpty() && !(subtask.getStatus() == null) &&
                            subtask.getId() == 0 && subtask.getEpicId() != 0) {
                        taskManager.addSubtask(subtask);
                        sendEmptyBody(exchange);
                    } else if (!subtask.getName().isEmpty() && !subtask.getDescription().isEmpty() && !(subtask.getStatus() == null) &&
                            subtask.getId() != 0 && subtask.getEpicId() != 0) {
                        taskManager.updateSubtask(subtask);
                        sendEmptyBody(exchange);
                    } else {
                        sendRequestError(exchange, "Неправильный запрос");
                    }
                } catch (AddTaskException e) {
                    sendHasOverlaps(exchange, e.getMessage());
                } catch (NotFoundException e) {
                    sendNotFound(exchange, e.getMessage());
                }
            case "DELETE":
                if (uriLength == 3) {
                    try {
                        taskManager.getSubtaskById(Integer.parseInt(uri[2])); //чтобы поймать исключение если нет такого элемента
                        taskManager.deleteSubtaskById(Integer.parseInt(uri[2]));
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
