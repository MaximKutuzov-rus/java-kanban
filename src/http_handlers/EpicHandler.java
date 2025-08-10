package http_handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;

public class EpicHandler extends BaseHttpHandler{
    public EpicHandler(TaskManager taskManager, Gson gson) {
        super(taskManager,gson);
    }

    @Override
    public void handle(HttpExchange exchange) {

    }
}
