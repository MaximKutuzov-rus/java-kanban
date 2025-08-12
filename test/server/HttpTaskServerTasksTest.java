package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Status;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTasksTest{
    TaskManager taskManager = HttpTaskServer.createTestManager();
    HttpTaskServer taskServer = new HttpTaskServer(taskManager);
    Gson gson = taskServer.getGson();

    public HttpTaskServerTasksTest() throws IOException {}

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
        URI uri = URI.create("http://localhost:8080/tasks");
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

        Task expectedTask1 = taskManager.getAllTasks().getFirst();
        Task expectedTask2 = taskManager.getAllTasks().getLast();

        Task actualTask1 = tasks.getFirst();
        Task actualTask2 = tasks.getLast();

        assertEquals(expectedTask1.getId(), actualTask1.getId());
        assertEquals(expectedTask1.getName(), actualTask1.getName());
        assertEquals(expectedTask1.getDescription(), actualTask1.getDescription());
        assertEquals(expectedTask1.getType(), actualTask1.getType());
        assertEquals(expectedTask1.getStatus(), actualTask1.getStatus());
        assertEquals(expectedTask1.getStartTime(), actualTask1.getStartTime());
        assertEquals(expectedTask1.getDuration(), actualTask1.getDuration());

        assertEquals(expectedTask2.getId(), actualTask2.getId());
        assertEquals(expectedTask2.getName(), actualTask2.getName());
        assertEquals(expectedTask2.getDescription(), actualTask2.getDescription());
        assertEquals(expectedTask2.getType(), actualTask2.getType());
        assertEquals(expectedTask2.getStatus(), actualTask2.getStatus());
        assertEquals(expectedTask2.getStartTime(), actualTask2.getStartTime());
        assertEquals(expectedTask2.getDuration(), actualTask2.getDuration());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getTaskByIdTestAndNotFoundException() throws IOException, InterruptedException{
        URI uri1 = URI.create("http://localhost:8080/tasks/2");
        URI uri2 = URI.create("http://localhost:8080/tasks/3");

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

        Task task = gson.fromJson(response1.body(), Task.class);

        assertEquals(taskManager.getTaskById(2), task);
        assertEquals(200, response1.statusCode());
        assertEquals(404, response2.statusCode());
    }

    @Test
    void postAndDeleteTaskTest() throws IOException, InterruptedException{
        URI uri = URI.create("http://localhost:8080/tasks");
        Task task = new Task("Переезд", "В новую квартиру", Status.NEW);
        String jsonTask = gson.toJson(task);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask))
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals("Переезд", taskManager.getTaskById(9).getName());

        URI uri2 = URI.create("http://localhost:8080/tasks/1");

        HttpRequest request2 = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri2)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();

        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(2, taskManager.getAllTasks().getFirst().getId());
    }
}