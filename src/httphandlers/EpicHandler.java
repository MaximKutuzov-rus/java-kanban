package httphandlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import exceptions.AddTaskException;
import exceptions.NotFoundException;
import managers.TaskManager;
import tasks.Epic;

import java.io.IOException;

public class EpicHandler extends BaseHttpHandler {
    public EpicHandler(TaskManager taskManager, Gson gson) {
        super(taskManager,gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] uri = exchange.getRequestURI().getPath().split("/");
        int uriLength = uri.length;
        switch (method) {
            case "GET":
                if (uriLength == 2) {
                    if (!taskManager.getAllEpics().isEmpty()) {
                        sendText(exchange, gson.toJson(taskManager.getAllEpics()));
                    } else {
                        sendText(exchange, "");
                    }
                } else if (uriLength == 3) {
                    try {
                        sendText(exchange, gson.toJson(taskManager.getEpicById(Integer.parseInt(uri[2]))));
                    } catch (NumberFormatException e) {
                        sendRequestError(exchange,
                                "Ошибка в формате запроса, вторым в пути запроса должно вводиться целое число");
                    } catch (NotFoundException e) {
                        sendNotFound(exchange, e.getMessage());
                    }
                } else if (uriLength == 4 && uri[3].equals("subtasks")) {
                    try {
                        if (!taskManager.getSubtasksOfEpic(Integer.parseInt(uri[2])).isEmpty()) {
                            sendText(exchange, gson.toJson(taskManager.getSubtasksOfEpic(Integer.parseInt(uri[2]))));
                        } else {
                            sendText(exchange, "");
                        }
                    } catch (NumberFormatException e) {
                        sendRequestError(exchange, "Неправильный запрос");
                    } catch (NotFoundException e) {
                        sendRequestError(exchange, e.getMessage());
                    }
                } else {
                    sendRequestError(exchange, "Обработка такого запроса не предусмотрена");
                }
            case "POST":
                Epic epic = fromJsonToTask(exchange, Epic.class);
                try {
                    if (!epic.getName().isEmpty() && !epic.getDescription().isEmpty() && epic.getId() == 0) {
                        taskManager.addEpic(epic);
                        sendEmptyBody(exchange);
                    } else if (!epic.getName().isEmpty() && !epic.getDescription().isEmpty() && epic.getId() != 0) {
                        taskManager.updateEpic(epic);
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
                        taskManager.getEpicById(Integer.parseInt(uri[2])); //чтобы поймать исключение если нет такого элемента
                        taskManager.deleteEpicById(Integer.parseInt(uri[2]));
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
