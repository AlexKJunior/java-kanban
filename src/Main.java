import InMemory.Managers;
import InMemory.Interface.TaskManager;
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

        SubTask subTask2OfEpic2 = new SubTask(10, "Защитить проект диплома",
                "Создать проект диплома", idOfCreatedEpic1);
        System.out.println("Создана подзадача: \n" + taskManager.creationOfSubTask(subTask2OfEpic2) + "\n");

        ArrayList<Integer> subTasksListIdOfEpic2 = new ArrayList<>();
        Epic epic2 = new Epic(10, "Поменять машину", "Продать старую машину",
                subTasksListIdOfEpic2);

        int idOfCreatedEpic2 = taskManager.creationOfEpic(epic2).getId();
        System.out.println("Создан Epic: \n" + taskManager.getListOfEpics().get(idOfCreatedEpic2) + "\n");

        SubTask subTask1OfEpic2 = new SubTask(10, "Подготовка старой машины к продаже",
                "Купить новую машину", idOfCreatedEpic2);
        System.out.println("Создана подзадача: \n" + taskManager.creationOfSubTask(subTask1OfEpic2) + "\n");

        printAllTasks(taskManager);

        System.out.println("Получаю задачу по Id 1: \n" + taskManager.getTaskById(1));
        System.out.println("Получаю задачу по Id 2: \n" + taskManager.getTaskById(2));
        System.out.println("Получаю задачу по Id 3: \n" + taskManager.getEpicById(3));
        System.out.println("Получаю задачу по Id 4: \n" + taskManager.getSubTaskById(4));
        System.out.println("Получаю задачу по Id 5: \n" + taskManager.getSubTaskById(5));
        System.out.println("Получаю задачу по Id 6: \n" + taskManager.getEpicById(6));
        System.out.println("Получаю задачу по Id 7: \n" + taskManager.getSubTaskById(7));
        System.out.println("Получаю задачу по Id 1: \n" + taskManager.getTaskById(1));
        System.out.println("Получаю задачу по Id 2: \n" + taskManager.getTaskById(2));
        System.out.println("Получаю задачу по Id 3: \n" + taskManager.getEpicById(3));
        System.out.println("Получаю задачу по Id 4: \n" + taskManager.getSubTaskById(4));
        System.out.println("Получаю задачу по Id 5: \n" + taskManager.getSubTaskById(5));
        System.out.println("Получаю задачу по Id 6: \n" + taskManager.getEpicById(6));
        System.out.println("Получаю задачу по Id 7: \n" + taskManager.getSubTaskById(7) + "\n");

        Task task3 = new Task(1, "Поспать (обновлено)", "Хорошо выспаться (обновлено)");
        task3.setStatus(Status.DONE);
        taskManager.updateTaskByNewTask(task3);

        SubTask subTask3OfEpic1 = new SubTask(5, "Защитить проект диплома(обновлено)",
                "Создать проект диплома(обновлено)", 3);
        subTask3OfEpic1.setStatus(Status.DONE);
        taskManager.updateSubTaskByNewSubTask(subTask3OfEpic1);

        printAllTasks(taskManager);

        taskManager.deleteTaskById(2);

        taskManager.deleteEpicById(6);

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
        String[] array = taskManager.getHistory().toString().split("},");
        StringBuilder result = new StringBuilder("История обращения к задачам: \n");
        for (String line : array) {
            result.append(line).append(".\n");
        }
        System.out.println(result);
    }
}