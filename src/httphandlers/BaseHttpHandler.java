package httphandlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler implements HttpHandler {
    protected final Gson gson;
    protected TaskManager taskManager;

    BaseHttpHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    protected void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "applications/json;charset=utf-8");
        h.sendResponseHeaders(200, 0);
        h.getResponseBody().write(resp);
        h.close();
    }

    protected void sendRequestError(HttpExchange exchange, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
        exchange.sendResponseHeaders(400, 0);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
        exchange.sendResponseHeaders(404, 0);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected void sendEmptyBody(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(201, 0);
        exchange.close();
    }

    protected void sendHasOverlaps(HttpExchange exchange, String text) throws IOException{
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "text/plain;charset=utf-8");
        exchange.sendResponseHeaders(406, 0);
        exchange.getResponseBody().write(resp);
        exchange.close();
    }

    protected <T extends Task> T fromJsonToTask(HttpExchange exchange, Class<T> taskClass) throws IOException {
        try (InputStream inputStream = exchange.getRequestBody()) {
            String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return gson.fromJson(body, taskClass);
        }
    }
}
