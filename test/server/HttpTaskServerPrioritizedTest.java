package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerPrioritizedTest {
    TaskManager taskManager = HttpTaskServer.createTestManager();
    HttpTaskServer taskServer = new HttpTaskServer(taskManager);
    Gson gson = taskServer.getGson();

    public HttpTaskServerPrioritizedTest() throws IOException {}

    @BeforeEach
    void startServer() {
        taskServer.start();
    }

    @AfterEach
    void stopServer() {
        taskServer.stop();
    }

    @Test
    void getTasksTest() throws IOException, InterruptedException {
        URI uri = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        class TaskListTypeToken extends TypeToken<List<Task>> {}

        List<Task> tasks = gson.fromJson(response.body(), new TaskListTypeToken().getType());

        Task expectedTask1 = taskManager.getPrioritizedTasks().stream().toList().getFirst();
        Task expectedTask2 = taskManager.getPrioritizedTasks().stream().toList().getLast();

        Task actualTask1 = tasks.getFirst();
        Task actualTask2 = tasks.getLast();

        assertEquals(expectedTask1.getId(), actualTask1.getId());
        assertEquals(expectedTask1.getName(), actualTask1.getName());
        assertEquals(expectedTask1.getDescription(), actualTask1.getDescription());
        assertEquals(expectedTask1.getStatus(), actualTask1.getStatus());
        assertEquals(expectedTask1.getStartTime(), actualTask1.getStartTime());
        assertEquals(expectedTask1.getDuration(), actualTask1.getDuration());

        assertEquals(expectedTask2.getId(), actualTask2.getId());
        assertEquals(expectedTask2.getName(), actualTask2.getName());
        assertEquals(expectedTask2.getDescription(), actualTask2.getDescription());
        assertEquals(expectedTask2.getStatus(), actualTask2.getStatus());
        assertEquals(expectedTask2.getStartTime(), actualTask2.getStartTime());
        assertEquals(expectedTask2.getDuration(), actualTask2.getDuration());

        assertEquals(200, response.statusCode());
    }
}