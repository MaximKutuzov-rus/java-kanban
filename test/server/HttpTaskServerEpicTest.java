package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import managers.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerEpicTest {
    TaskManager taskManager = HttpTaskServer.createTestManager();
    HttpTaskServer taskServer = new HttpTaskServer(taskManager);
    Gson gson = taskServer.getGson();

    public HttpTaskServerEpicTest() throws IOException {}

    @BeforeEach
    void startServer() {
        taskServer.start();
    }

    @AfterEach
    void stopServer() {
        taskServer.stop();
    }

    @Test
    void getEpicsTest() throws IOException, InterruptedException {
        URI uri = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        class TaskListTypeToken extends TypeToken<List<Epic>> {}

        List<Epic> epics = gson.fromJson(response.body(), new TaskListTypeToken().getType());

        Epic expectedEpic1 = taskManager.getAllEpics().getFirst();
        Epic expectedEpic2 = taskManager.getAllEpics().getLast();

        Epic actualEpic1 = epics.getFirst();
        Epic actualEpic2 = epics.getLast();

        assertEquals(expectedEpic1.getId(), actualEpic1.getId());
        assertEquals(expectedEpic1.getName(), actualEpic1.getName());
        assertEquals(expectedEpic1.getDescription(), actualEpic1.getDescription());
        assertEquals(expectedEpic1.getType(), actualEpic1.getType());
        assertEquals(expectedEpic1.getStatus(), actualEpic1.getStatus());
        assertEquals(expectedEpic1.getStartTime(), actualEpic1.getStartTime());
        assertEquals(expectedEpic1.getDuration(), actualEpic1.getDuration());

        assertEquals(expectedEpic2.getId(), actualEpic2.getId());
        assertEquals(expectedEpic2.getName(), actualEpic2.getName());
        assertEquals(expectedEpic2.getDescription(), actualEpic2.getDescription());
        assertEquals(expectedEpic2.getType(), actualEpic2.getType());
        assertEquals(expectedEpic2.getStatus(), actualEpic2.getStatus());
        assertEquals(expectedEpic2.getStartTime(), actualEpic2.getStartTime());
        assertEquals(expectedEpic2.getDuration(), actualEpic2.getDuration());

        assertEquals(200, response.statusCode());
    }

    @Test
    void getTaskByIdTestAndNotFoundException() throws IOException, InterruptedException{
        URI uri1 = URI.create("http://localhost:8080/epics/7");
        URI uri2 = URI.create("http://localhost:8080/epics/12");

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

        Epic epic = gson.fromJson(response1.body(), Epic.class);

        assertEquals(taskManager.getEpicById(7), epic);
        assertEquals(200, response1.statusCode());
        assertEquals(404, response2.statusCode());
    }

    @Test
    void postAndDeleteTaskTest() throws IOException, InterruptedException{
        URI uri = URI.create("http://localhost:8080/epics");
        Epic epic = new Epic("Пройти курс", "Пройти курс от ЯП");
        String jsonTask = gson.toJson(epic);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonTask))
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();
        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals("Пройти курс", taskManager.getEpicById(9).getName());

        URI uri2 = URI.create("http://localhost:8080/epics/3");

        HttpRequest request2 = HttpRequest.newBuilder()
                .DELETE()
                .uri(uri2)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "applications/json")
                .build();

        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        assertEquals(7, taskManager.getAllEpics().getFirst().getId());
    }

    @Test
    void getSubtasksOfEpicTest() throws IOException, InterruptedException{
        URI uri = URI.create("http://localhost:8080/epics/3/subtasks");
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

        Subtask expectedSubtask1 = taskManager.getSubtasksOfEpic(3).getFirst();
        Subtask expectedSubtask2 = taskManager.getSubtasksOfEpic(3).getLast();

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
}