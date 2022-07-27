import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import gson.LocalDateTimeAdapter;
import http.HttpTaskServer;
import http.KVServer;
import model.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {
    private final static Gson GSON = new GsonBuilder().serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static void main(String[] args) throws IOException, InterruptedException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        new KVServer().start();
        HttpTaskServer httpTaskServer1 = new HttpTaskServer("http://localhost:8078", "save1");
        httpTaskServer1.start();
        HttpClient client = HttpClient.newHttpClient();

        Task task1 = new Task(100, "Тест 1", "Создать Task 1");
        task1.setStartTime(LocalDateTime.parse("27.07.2022 10:00", dateTimeFormatter));
        task1.setDuration(Duration.ofDays(60));
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(GSON.toJson(task1));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task/"))
                .POST(body).build();
        client.send(request, HttpResponse.BodyHandlers.ofString());

        Task task2 = new Task(100, "Тест 2", "Создать Task 2");
        task2.setStartTime(LocalDateTime.parse("27.07.2022 13:00", dateTimeFormatter));
        task2.setDuration(Duration.ofDays(60));
        HttpRequest.BodyPublisher body2 = HttpRequest.BodyPublishers.ofString(GSON.toJson(task2));
        HttpRequest request2 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task/"))
                .POST(body2).build();
        client.send(request2, HttpResponse.BodyHandlers.ofString());

        HttpRequest.BodyPublisher body3 = HttpRequest.BodyPublishers
                .ofString(GSON.toJson(new Task(100, "Тест 3", "Создать Task 3")));
        HttpRequest request3 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task/"))
                .POST(body3).build();
        client.send(request3, HttpResponse.BodyHandlers.ofString());

        HttpRequest.BodyPublisher body4 = HttpRequest.BodyPublishers
                .ofString(GSON.toJson(new Task(100, "Тест 4", "Создать Task 4")));
        HttpRequest request4 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task/"))
                .POST(body4).build();
        client.send(request4, HttpResponse.BodyHandlers.ofString());

        HttpRequest.BodyPublisher body5 = HttpRequest.BodyPublishers
                .ofString(GSON.toJson(new Task(100, "Тест 5", "Создать Task 5")));
        HttpRequest request5 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task/"))
                .POST(body5).build();
        client.send(request5, HttpResponse.BodyHandlers.ofString());

        HttpRequest.BodyPublisher body6 = HttpRequest.BodyPublishers
                .ofString(GSON.toJson(new Epic(100, "Тест 6", "Создать Epic 6")));
        HttpRequest request6 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/epic/"))
                .POST(body6).build();
        client.send(request6, HttpResponse.BodyHandlers.ofString());

        SubTask subTask1 = new SubTask(100, "Тест 7", "Создать SubTask 7", 6);
        subTask1.setStartTime(LocalDateTime.parse("27.07.2022 14:00", dateTimeFormatter));
        subTask1.setDuration(Duration.ofDays(60));
        HttpRequest.BodyPublisher body7 = HttpRequest.BodyPublishers.ofString(GSON.toJson(subTask1));
        HttpRequest request7 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                .POST(body7).build();
        HttpResponse<String> httpResponse = client.send(request7, HttpResponse.BodyHandlers.ofString());
        subTask1 = GSON.fromJson(httpResponse.body(), SubTask.class);

        SubTask subTask2 = new SubTask(100, "Тест 8", "Создать SubTask 8", 6);
        subTask2.setStartTime(LocalDateTime.parse("27.07.2022 12:00", dateTimeFormatter));
        subTask2.setDuration(Duration.ofDays(60));
        HttpRequest.BodyPublisher body8 = HttpRequest.BodyPublishers.ofString(GSON.toJson(subTask2));
        HttpRequest request8 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                .POST(body8).build();
        httpResponse = client.send(request8, HttpResponse.BodyHandlers.ofString());
        subTask2 = GSON.fromJson(httpResponse.body(), SubTask.class);

        SubTask subTask3 = new SubTask(100, "Тест 9", "Создать SubTask 9", 6);
        subTask3.setStartTime(LocalDateTime.parse("27.07.2022 17:00", dateTimeFormatter));
        subTask3.setDuration(Duration.ofDays(60));
        HttpRequest.BodyPublisher body9 = HttpRequest.BodyPublishers.ofString(GSON.toJson(subTask3));
        HttpRequest request9 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                .POST(body9).build();
        client.send(request9, HttpResponse.BodyHandlers.ofString());

        subTask1.setStatus(Status.IN_PROGRESS);
        HttpRequest.BodyPublisher body10 = HttpRequest.BodyPublishers.ofString(GSON.toJson(subTask1));
        HttpRequest request10 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask?7"))
                .POST(body10).build();
        client.send(request10, HttpResponse.BodyHandlers.ofString());

        subTask2.setStatus(Status.DONE);
        HttpRequest.BodyPublisher body11 = HttpRequest.BodyPublishers.ofString(GSON.toJson(subTask2));
        HttpRequest request11 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask?8"))
                .POST(body11).build();
        client.send(request11, HttpResponse.BodyHandlers.ofString());

        HttpRequest request12 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask?8"))
                .GET()
                .build();
        httpResponse = client.send(request12, HttpResponse.BodyHandlers.ofString());
        System.out.println("Результат вызова по ID: " + GSON.fromJson(httpResponse.body(), SubTask.class));

        HttpRequest request13 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask?8"))
                .GET()
                .build();
        httpResponse = client.send(request13, HttpResponse.BodyHandlers.ofString());
        System.out.println("Результат вызова по ID: " + GSON.fromJson(httpResponse.body(), SubTask.class));

        HttpRequest request14 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/epic?6"))
                .GET()
                .build();
        httpResponse = client.send(request14, HttpResponse.BodyHandlers.ofString());
        System.out.println("Результат вызова по ID: " + GSON.fromJson(httpResponse.body(), Epic.class));

        HttpRequest request15 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/epic?6"))
                .GET()
                .build();
        httpResponse = client.send(request15, HttpResponse.BodyHandlers.ofString());
        System.out.println("Результат вызова по ID: " + GSON.fromJson(httpResponse.body(), Epic.class));

        HttpRequest request16 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task?4"))
                .GET()
                .build();
        httpResponse = client.send(request16, HttpResponse.BodyHandlers.ofString());
        System.out.println("Результат вызова по ID: " + GSON.fromJson(httpResponse.body(), Task.class));

        HttpRequest request17 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task?4"))
                .GET()
                .build();
        httpResponse = client.send(request17, HttpResponse.BodyHandlers.ofString());
        System.out.println("Результат вызова по ID: " + GSON.fromJson(httpResponse.body(), Task.class));

        HttpRequest request18 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();
        httpResponse = client.send(request18, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nЗадачи в порядке срока выполнения:");
        printJsonList(httpResponse);

        HttpRequest request19 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/history"))
                .GET()
                .build();
        httpResponse = client.send(request19, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nИстория обращения к задачам:");
        printJsonList(httpResponse);

        httpTaskServer1.stop();
        HttpTaskServer httpTaskServer2 = new HttpTaskServer("http://localhost:8078", "save1");
        httpTaskServer2.start();
        HttpClient client2 = HttpClient.newHttpClient();

        HttpRequest request20 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks"))
                .GET()
                .build();
        httpResponse = client2.send(request20, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nЗадачи в порядке срока выполнения: (из сохранения)");
        printJsonList(httpResponse);

        HttpRequest request21 = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/history"))
                .GET()
                .build();
        httpResponse = client2.send(request21, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nИстория обращения к задачам: (из сохранения)");
        printJsonList(httpResponse);
    }

    public static void printJsonList(HttpResponse<String> httpResponse) {
        List<String> prioritizedList = GSON.fromJson(httpResponse.body(), List.class);
        prioritizedList.stream().map(JsonParser::parseString)
                .map(JsonElement::getAsJsonObject)
                .map(jsonObject -> {
                    switch (TypeTask.valueOf(GSON.fromJson(jsonObject.get("typeTask"), String.class))) {
                        case EPIC: {
                            return GSON.fromJson(jsonObject, Epic.class);
                        }
                        case SUBTASK: {
                            return GSON.fromJson(jsonObject, SubTask.class);
                        }
                        default: {
                            return GSON.fromJson(jsonObject, Task.class);
                        }
                    }
                }).forEach(System.out::println);
    }
}
