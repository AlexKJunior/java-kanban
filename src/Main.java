import java.util.ArrayList;
import java.util.Arrays;
//«Трекер задач»

public class Main {
    public static void main(String[] args) {

        Manager manager = new Manager();

        Task taskFirst = new Task("Поспать","Хорошо выспаться", "NEW");
        Task taskSecond = new Task("Потренироваться в зале","Сделать силовую тренировку",
                "DONE");

        ArrayList<EpicTask.SubTask> subTasksEpicTaskFirst = new ArrayList<>();
        EpicTask.SubTask subtaskFirstEpicTaskFirst = new EpicTask.SubTask("Завершить обучение",
                "Успеть к дедлайнам", "Вовремя сдать ТЗ", "NEW");
        EpicTask.SubTask subtaskSecondEpicTaskFirst = new EpicTask.SubTask("Завершить обучение",
                "Защитить проект диплома", "Создать проект диплома", "DONE");

        subTasksEpicTaskFirst.add(subtaskFirstEpicTaskFirst);
        subTasksEpicTaskFirst.add(subtaskSecondEpicTaskFirst);

        EpicTask epicTaskFirst = new EpicTask("Завершить обучение",
                "Получить диплом", subTasksEpicTaskFirst);

        EpicTask.SubTask subtaskFirstEpicTaskSecond = new EpicTask.SubTask("Поменять машину",
                "Продать старую машину","Подготовить старую машину к продаже",
                "NEW");
        ArrayList<EpicTask.SubTask> subTasksEpicTaskSecond = new ArrayList<>();

        subTasksEpicTaskSecond.add(subtaskFirstEpicTaskSecond);

        EpicTask epicTaskSecond = new EpicTask("Поменять машину",
                "Купить новую машину", subTasksEpicTaskSecond);

        manager.saveToStorage(taskFirst);
        manager.saveToStorage(taskSecond);
        manager.saveToStorage(subtaskFirstEpicTaskFirst);
        manager.saveToStorage(subtaskSecondEpicTaskFirst);
        manager.saveToStorage(epicTaskFirst);
        manager.saveToStorage(subtaskFirstEpicTaskSecond);
        manager.saveToStorage(epicTaskSecond);

        System.out.println("\n    2.1 Получение списка всех задач:");
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getTaskStorage()).toArray()));
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getEpicTaskStorage()).toArray()));
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getSubTaskStorage()).toArray()));

        manager.deleteAllTasksOfAnyType(manager.getEpicTaskStorage());

        System.out.println("\n    2.2 Удаление всех задач:");
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getTaskStorage()).toArray()));
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getEpicTaskStorage()).toArray()));
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getSubTaskStorage()).toArray()));

        System.out.println("\n    2.3 Получение по идентификатору:");
        System.out.println(manager.getTaskOfAnyTypeById(0));
        System.out.println(manager.getTaskOfAnyTypeById(1));
        System.out.println(manager.getTaskOfAnyTypeById(2));
        System.out.println(manager.getTaskOfAnyTypeById(3));
        System.out.println(manager.getTaskOfAnyTypeById(4));
        System.out.println(manager.getTaskOfAnyTypeById(5));
        System.out.println(manager.getTaskOfAnyTypeById(6));
        System.out.println(manager.getTaskOfAnyTypeById(7));
        System.out.println(manager.getTaskOfAnyTypeById(8));

        System.out.println("\n    2.4 Создание. Сам объект должен передаваться в качестве параметра:");
        System.out.println(manager.createCopyOfTaskOfAnyType(taskFirst));
        System.out.println(manager.createCopyOfTaskOfAnyType(epicTaskFirst));
        System.out.println(manager.createCopyOfTaskOfAnyType(subtaskFirstEpicTaskFirst));

        manager.updateTaskOfAnyType(5, epicTaskFirst);
        manager.updateTaskOfAnyType(7, epicTaskSecond);

        System.out.println("\n    2.5 Обновление. Новая версия объекта с верным идентификатором передаются в виде"
                + " параметра:");
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getEpicTaskStorage()).toArray()));

        manager.removeTaskOfAnyTypeById(1);
        manager.removeTaskOfAnyTypeById(2);

        System.out.println("\n    2.6 Удаление по идентификатору:");
        System.out.println(Arrays.toString(manager.getCompleteListOfAnyTasks(manager.getTaskStorage()).toArray()));

        System.out.println("\n    3.1 Получение списка всех подзадач определённого эпика:");
        System.out.println(manager.getCompleteListOfSubTaskByEpicTask(epicTaskFirst));
        System.out.println(manager.getCompleteListOfSubTaskByEpicTask(epicTaskSecond));
    }
}