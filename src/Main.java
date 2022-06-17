import inmemory.intrface.TaskManager;
import inmemory.Managers;
import model.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task(10, "Поспать", "Хорошо выспаться");
        System.out.println("Создана задача: \n" + taskManager.creationOfTask(task1) + "\n");

        Task task2 = new Task(10, "Потренироваться в зале", "Сделать силовую тренировку");
        System.out.println("Создана задача: \n" + taskManager.creationOfTask(task2) + "\n");

        ArrayList<Integer> subTasksListIdOfEpic1 = new ArrayList<>();
        Epic epic1 = new Epic(10, "Завершить обучение", "Успеть к дедлайнам", subTasksListIdOfEpic1);

        int idOfCreatedEpic1 = taskManager.creationOfEpic(epic1).getId();
        System.out.println("Создан Epic: \n" + taskManager.getListOfEpics().get(idOfCreatedEpic1) + "\n");

        SubTask subTask1OfEpic1 = new SubTask(10, "Вовремя сдать ТЗ",
                "Выполнить все задания", idOfCreatedEpic1);
        System.out.println("Создана подзадача: \n" + taskManager.creationOfSubTask(subTask1OfEpic1) + "\n");

        SubTask subTask2OfEpic1 = new SubTask(10, "Защитить проект диплома",
                "Создать проект диплома", idOfCreatedEpic1);
        System.out.println("Создана подзадача: \n" + taskManager.creationOfSubTask(subTask2OfEpic1) + "\n");

        SubTask subTask3OfEpic1 = new SubTask(10, "Проработать проект диплома",
                "Найти необходимую информацию для проекта", idOfCreatedEpic1);
        System.out.println("Создана подзадача: \n" + taskManager.creationOfSubTask(subTask3OfEpic1) + "\n");

        ArrayList<Integer> subTasksListIdOfEpic2 = new ArrayList<>();
        Epic epic2 = new Epic(10, "Поменять машину", "Продать старую машину", subTasksListIdOfEpic2);

        int idOfCreatedEpic2 = taskManager.creationOfEpic(epic2).getId();
        System.out.println("Создан Epic: \n" + taskManager.getListOfEpics().get(idOfCreatedEpic2) + "\n");


        printAllTasks(taskManager);

        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getTaskById(2);
        taskManager.getEpicById(3);
        taskManager.getEpicById(3);
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(4);
        taskManager.getSubTaskById(5);
        taskManager.getSubTaskById(5);
        taskManager.getSubTaskById(6);
        taskManager.getTaskById(1);
        taskManager.getEpicById(7);

        printHistory(taskManager);

        taskManager.deleteTaskById(1);

        taskManager.deleteEpicById(3);

        printAllTasks(taskManager);

        printHistory(taskManager);
    }

    public static void printAllTasks(TaskManager taskManager) {
        String[] array = taskManager.getListOfAllTasks().toString().split("},");
        StringBuilder result = new StringBuilder("Список всех задач: \n");
        for (String line : array) {
            result.append(line).append(".\n");
        }
        System.out.println(result);
    }

    public static void printHistory(TaskManager taskManager) {
        String[] array = taskManager.getListOfEpics().toString().split("},");
        StringBuilder result = new StringBuilder("История обращения к задачам: \n");
        for (String line : array) {
            result.append(line).append(".\n");
        }
        System.out.println(result);
    }
}