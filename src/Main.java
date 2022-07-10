import inmemory.Managers;
import inmemory.manager.FileBackedTasksManager;
import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = Managers.getDefault();


        Task task1 = new Task(100, "Тестирование 1", "Создать тестовый Task 1");
        fileBackedTasksManager.creationOfTask(task1);


        Task task2 = new Task(100, "Тестирование 2", "Создать тестовый Task 2");
        fileBackedTasksManager.creationOfTask(task2);


        ArrayList<Integer> subTasksListIdOfEpic1 = new ArrayList<>();
        Epic epic1 = new Epic(
        );
        int idOfCreatedEpic1 = fileBackedTasksManager.creationOfEpic(epic1).getId();


        SubTask subTask1OfEpic1 = new SubTask(100, "Тестирование 4"
                , "Создать тестовый SubTask 1", idOfCreatedEpic1);
        fileBackedTasksManager.creationOfSubTask(subTask1OfEpic1);


        SubTask subTask2OfEpic1 = new SubTask(10, "Тестирование 5"
                , "Создать тестовый SubTask 2", idOfCreatedEpic1);
        fileBackedTasksManager.creationOfSubTask(subTask2OfEpic1);


        fileBackedTasksManager.getTaskById(2);
        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(2);
        fileBackedTasksManager.getEpicById(3);
        fileBackedTasksManager.getEpicById(3);
        fileBackedTasksManager.getSubTaskById(5);
        fileBackedTasksManager.getSubTaskById(4);
    }
}
