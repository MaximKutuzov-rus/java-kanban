package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Subtask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerSubtasksTest {
    TaskManager taskManager = HttpTaskServer.createTestManager();
    HttpTaskServer taskServer = new HttpTaskServer(taskManager);
    Gson gson = taskServer.getGson();

    public HttpTaskServerSubtasksTest() throws IOException {}

    @BeforeEach
    void startServer() {
        taskServer.start();
    }

    @AfterEach
    void stopServer() {
        taskServer.stop();
    }

    @Test
    void getSubtasksTest() throws IOException, InterruptedException {
        URI uri = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        class TaskListTypeToken extends TypeToken<List<Subtask>> {}

        List<Subtask> subtasks = gson.fromJson(response.body(), new TaskListTypeToken().getType());

        Subtask expectedSubtask1 = taskManager.getAllSubtasks().getFirst();
        Subtask expectedSubtask2 = taskManager.getAllSubtasks().getLast();

        Subtask actualSubtask1 = subtasks.getFirst();
        Subtask actualSubtask2 = subtasks.getLast();

        assertEquals(expectedSubtask1.getId(), actualSubtask1.getId());
        assertEquals(expectedSubtask1.getName(), actualSubtask1.getName());
        assertEquals(expectedSubtask1.getDescription(), actualSubtask1.getDescription());
        assertEquals(expectedSubtask1.getType(), actualSubtask1.getType());
        assertEquals(expectedSubtask1.getStatus(), actualSubtask1.getStatus());
        assertEquals(expectedSubtask1.getStartTime(), actualSubtask1.getStartTime());
        assertEquals(expectedSubtask1.getDuration(), actualSubtask1.getDuration());
        assertEquals(expectedSubtask1.getEpicId(), actualSubtask1.getEpicId());

        assertEquals(expectedSubtask2.getId(), actualSubtask2.getId());
        assertEquals(expectedSubtask2.getName(), actualSubtask2.getName());
        assertEquals(expectedSubtask2.getDescription(), actualSubtask2.getDescription());
        assertEquals(expectedSubtask2.getType(), actualSubtask2.getType());
        assertEquals(expectedSubtask2.getStatus(), actualSubtask2.getStatus());
        assertEquals(expectedSubtask2.getStartTime(), actualSubtask2.getStartTime());
        assertEquals(expectedSubtask2.getDuration(), actualSubtask2.getDuration());
        assertEquals(expectedSubtask2.getEpicId(), actualSubtask2.getEpicId());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getTaskByIdTestAndNotFoundException() throws IOException, InterruptedException{
        URI uri1 = URI.create("http://localhost:8080/subtasks/4");
        URI uri2 = URI.create("http://localhost:8080/subtasks/12");

        HttpRequest request1 = HttpRequest.newBuilder()
                .GET()
                .uri(uri1)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();
        HttpRequest request2 = HttpRequest.newBuilder()
                .GET()
                .uri(uri2)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        Subtask subtask = gson.fromJson(response1.body(), Subtask.class);

        assertEquals(taskManager.getSubtaskById(4), subtask);
        assertEquals(200, response1.statusCode());
        assertEquals(404, response2.statusCode());
    }

    @Test
    void postAndDeleteTaskTest() throws IOException, InterruptedException{
        URI uri = URI.create("http://localhost:8080/subtasks");
        Subtask subtask = new Subtask("Приложение банка", "Открыть приложение банка", Status.DONE, 3);
        String jsonTask = gson.toJson(subtask);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask))
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals("Приложение банка", taskManager.getSubtaskById(9).getName());

        URI uri2 = URI.create("http://localhost:8080/subtasks/4");

        HttpRequest request2 = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri2)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();

        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(5, taskManager.getAllSubtasks().getFirst().getId());
    }
}