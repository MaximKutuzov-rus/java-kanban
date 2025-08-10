package httphandlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler {
    public HistoryHandler(TaskManager taskManager, Gson gson) {
        super(taskManager, gson);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if (method.equals("GET")) {
            if (!taskManager.getHistory().isEmpty()) {
                sendText(exchange, gson.toJson(taskManager.getHistory()));
            } else {
                sendText(exchange, "");
            }
        } else {
            sendRequestError(exchange, "Некорректный метод запроса");
        }
    }
}
