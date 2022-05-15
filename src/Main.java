import InMemory.Manager;
import model.*;

import java.util.ArrayList;
import java.util.Arrays;
//«Трекер задач»

public class Main {
    public static void main (String[] args) {

        Manager manager = new Manager ();

        Task taskFirst = new Task ( "Поспать", "Хорошо выспаться", "NEW" );
        Task taskSecond = new Task ( "Потренироваться в зале", "Сделать силовую тренировку",
                "DONE" );

        ArrayList<Epic> subTasksEpicFirst = new ArrayList<> ();
        Epic subtaskFirstEpicFirst = new Epic ( "Завершить обучение",
                "Успеть к дедлайнам", "Вовремя сдать ТЗ" );
        Epic subtaskSecondEpicFirst = new Epic ( "Завершить обучение",
                "Защитить проект диплома", "Создать проект диплома" );

        subTasksEpicFirst.add ( subtaskFirstEpicFirst );
        subTasksEpicFirst.add ( subtaskSecondEpicFirst );

        Epic EpicFirst = new Epic ( "Завершить обучение",
                "Получить диплом", subTasksEpicFirst );

        Epic subtaskFirstEpicSecond = new Epic ( "Поменять машину",
                "Продать старую машину", "Подготовить старую машину к продаже"
        );
        ArrayList<Epic> subTasksEpicSecond = new ArrayList<> ();

        subTasksEpicSecond.add ( subtaskFirstEpicSecond );

        Epic EpicSecond = new Epic ( "Поменять машину",
                "Купить новую машину", subTasksEpicSecond );

        manager.saveToStorage ( taskFirst );
        manager.saveToStorage ( taskSecond );
        manager.saveToStorage ( subtaskFirstEpicFirst );
        manager.saveToStorage ( subtaskSecondEpicFirst );
        manager.saveToStorage ( EpicFirst );
        manager.saveToStorage ( subtaskFirstEpicSecond );
        manager.saveToStorage ( EpicSecond );

        System.out.println ( "\n    2.1 Получение списка всех задач:" );
        System.out.println ( Arrays.toString ( manager.getCompleteListOfAnyTasks ( manager.getTaskStorage () ).toArray () ) );
        System.out.println ( Arrays.toString ( manager.getCompleteListOfAnyTasks ( manager.getEpicStorage () ).toArray () ) );
        System.out.println ( Arrays.toString ( manager.getCompleteListOfAnyTasks ( manager.getSubTaskStorage () ).toArray () ) );

        manager.deleteAllTasksOfAnyType ( manager.getEpicStorage () );

        System.out.println ( "\n    2.2 Удаление всех задач:" );
        System.out.println ( Arrays.toString ( manager.getCompleteListOfAnyTasks ( manager.getTaskStorage () ).toArray () ) );
        System.out.println ( Arrays.toString ( manager.getCompleteListOfAnyTasks ( manager.getEpicStorage () ).toArray () ) );
        System.out.println ( Arrays.toString ( manager.getCompleteListOfAnyTasks ( manager.getSubTaskStorage () ).toArray () ) );

        System.out.println ( "\n    2.3 Получение по идентификатору:" );
        System.out.println ( manager.getTaskOfAnyTypeById ( 0 ) );
        System.out.println ( manager.getTaskOfAnyTypeById ( 1 ) );
        System.out.println ( manager.getTaskOfAnyTypeById ( 2 ) );
        System.out.println ( manager.getTaskOfAnyTypeById ( 3 ) );
        System.out.println ( manager.getTaskOfAnyTypeById ( 4 ) );
        System.out.println ( manager.getTaskOfAnyTypeById ( 5 ) );
        System.out.println ( manager.getTaskOfAnyTypeById ( 6 ) );
        System.out.println ( manager.getTaskOfAnyTypeById ( 7 ) );
        System.out.println ( manager.getTaskOfAnyTypeById ( 8 ) );

        System.out.println ( "\n    2.4 Создание. Сам объект должен передаваться в качестве параметра:" );
        System.out.println ( manager.createCopyOfTaskOfAnyType ( taskFirst ) );
        System.out.println ( manager.createCopyOfTaskOfAnyType ( EpicFirst ) );
        System.out.println ( manager.createCopyOfTaskOfAnyType ( subtaskFirstEpicFirst ) );

        manager.updateTaskOfAnyType ( 5, EpicFirst );
        manager.updateTaskOfAnyType ( 7, EpicSecond );

        System.out.println ( "\n    2.5 Обновление. Новая версия объекта с верным идентификатором передаются в виде"
                + " параметра:" );
        System.out.println ( Arrays.toString ( manager.getCompleteListOfAnyTasks ( manager.getEpicStorage () ).toArray () ) );

        manager.removeTaskOfAnyTypeById ( 1 );
        manager.removeTaskOfAnyTypeById ( 2 );

        System.out.println ( "\n    2.6 Удаление по идентификатору:" );
        System.out.println ( Arrays.toString ( manager.getCompleteListOfAnyTasks ( manager.getTaskStorage () ).toArray () ) );

        System.out.println ( "\n    3.1 Получение списка всех подзадач определённого эпика:" );
        System.out.println ( manager.getCompleteListOfSubTaskByEpic ( EpicFirst ) );
        System.out.println ( manager.getCompleteListOfSubTaskByEpic ( EpicSecond ) );
    }
}