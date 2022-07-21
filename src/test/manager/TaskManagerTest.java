package test.manager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import inmemory.Managers;
import inmemory.intrface.TaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    static TaskManager taskManager = Managers.getDefaultManager();
    static TaskManager fileBackedTasksManager = Managers.getDefault();
    static DateTimeFormatter dateTimeFormatter;

    @BeforeAll
    static void beforeAll() {
        taskManager = Managers.getDefaultManager();
        fileBackedTasksManager = Managers.getDefault();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    }

    @Test
    void shouldCreateNewTaskInFileBackedTasksManager() {
        Task testTask = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        int taskId = testTask.getId();
        Task savedTask = fileBackedTasksManager.getTaskById(taskId);
        assertNotNull(testTask, "Task не создан.");
        assertNotNull(savedTask, "Task на возвращается по Id.");
        assertEquals(testTask, savedTask, "Созданный Task и возвращенный по Id не совпадают.");
    }

    @Test
    void shouldUpdateTaskInFileBackedTasksManager() {
        Task testTask = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        testTask.setName("newName");
        testTask.setDescription("newDescription");
        testTask.setStatus(Status.IN_PROGRESS);
        testTask.setStartTime(LocalDateTime.parse("10.09.2022 12:43", dateTimeFormatter));
        testTask.setDuration(90);
        Task updatedTestTask = fileBackedTasksManager.updateTaskByNewTask(testTask);
        assertEquals(testTask, updatedTestTask, "Task не обновился.");
        assertEquals(updatedTestTask.getName(), "newName", "Имя Task не обновилось.");
        assertEquals(updatedTestTask.getDescription(), "newDescription", "Описание Task не обновилось.");
        assertEquals(updatedTestTask.getStartTime().format(dateTimeFormatter), "10.09.2022 12:43",
                "Время начала Task не обновилось.");
        assertEquals(updatedTestTask.getDuration(), 90, "Длительность Task не обновилось.");
        assertEquals(Status.IN_PROGRESS, updatedTestTask.getStatus(), "Статус Task не обновился.");

        testTask.setStatus(null);
        assertEquals(Status.IN_PROGRESS, testTask.getStatus(), "Статус Task некоректен.");

        testTask = new Task(Integer.MAX_VALUE, "testTask1", "testTask1 description");
        updatedTestTask = fileBackedTasksManager.updateTaskByNewTask(testTask);
        assertNull(updatedTestTask, "Проведено обновление Task с аргументом null.");
    }

    @Test
    void shouldGetListOfTasksInFileBackedTasksManager() {
        fileBackedTasksManager.deleteAllTasks();
        Map<Integer, Task> testList = fileBackedTasksManager.getListOfTasks();
        assertEquals(0, testList.size(), " Ошибка при формировании taskList");

        Task testTask1 = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        Task testTask2 = fileBackedTasksManager.creationOfTask(new Task(10, "testTask2",
                "testTask2 description"));
        Task testTask3 = fileBackedTasksManager.creationOfTask(new Task(10, "testTask3",
                "testTask3 description"));
        assertEquals(3, testList.size(), "Ошибка при формировании taskList");
        assertEquals(testTask1, fileBackedTasksManager.getListOfTasks().get(testTask1.getId()));
        assertEquals(testTask2, fileBackedTasksManager.getListOfTasks().get(testTask2.getId()));
        assertEquals(testTask3, fileBackedTasksManager.getListOfTasks().get(testTask3.getId()));
    }

    @Test
    void shouldDeleteTaskByIdInFileBackedTasksManager() {
        Task testTask = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        int testTaskId = testTask.getId();
        fileBackedTasksManager.deleteTaskById(testTaskId);
        assertNull(fileBackedTasksManager.getTaskById(testTaskId), "Task не удалился.");

        assertNull(fileBackedTasksManager.deleteTaskById(-1), "Выполнена попытка удления несуществующего Task");
    }

    @Test
    void shouldCreateNewTaskInTasksManager() {
        Task testTask = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        int taskId = testTask.getId();
        Task savedTask = taskManager.getTaskById(taskId);
        assertNotNull(testTask, "Task не создан.");
        assertNotNull(savedTask, "Task на возвращается по Id.");
        assertEquals(testTask, savedTask, "Созданный Task и возвращенный по Id не совпадают.");
    }

    @Test
    void shouldUpdateTaskInTasksManager() {
        Task testTask = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        testTask.setName("newName");
        testTask.setDescription("newDescription");
        testTask.setStatus(Status.IN_PROGRESS);
        testTask.setStartTime(LocalDateTime.parse("10.09.2022 12:43", dateTimeFormatter));
        testTask.setDuration(90);
        Task updatedTestTask = taskManager.updateTaskByNewTask(testTask);
        assertEquals(testTask, updatedTestTask, "Task не обновился.");
        assertEquals(updatedTestTask.getName(), "newName", "Имя Task не обновилось.");
        assertEquals(updatedTestTask.getDescription(), "newDescription", "Описание Task не обновилось.");
        assertEquals(updatedTestTask.getStartTime().format(dateTimeFormatter), "10.09.2022 12:43",
                "Время начала Task не обновилось.");
        assertEquals(updatedTestTask.getDuration(), 90, "Длительность Task не обновилось.");
        assertEquals(Status.IN_PROGRESS, updatedTestTask.getStatus(), "Статус Task не обновился.");

        testTask.setStatus(null);
        assertEquals(Status.IN_PROGRESS, testTask.getStatus(), "Статус Task некоректен.");

        testTask = new Task(Integer.MAX_VALUE, "testTask1", "testTask1 description");
        updatedTestTask = taskManager.updateTaskByNewTask(testTask);
        assertNull(updatedTestTask, "Проведено обновление Task с аргументом null.");
    }

    @Test
    void shouldGetListOfTasksInTasksManager() {
        taskManager.deleteAllTasks();
        Map<Integer, Task> testList = taskManager.getListOfTasks();
        assertEquals(0, testList.size(), " Ошибка при формировании taskList");

        Task testTask1 = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        Task testTask2 = taskManager.creationOfTask(new Task(10, "testTask2",
                "testTask2 description"));
        Task testTask3 = taskManager.creationOfTask(new Task(10, "testTask3",
                "testTask3 description"));
        assertEquals(3, testList.size(), "Ошибка при формировании taskList");
        assertEquals(testTask1, taskManager.getListOfTasks().get(testTask1.getId()));
        assertEquals(testTask2, taskManager.getListOfTasks().get(testTask2.getId()));
        assertEquals(testTask3, taskManager.getListOfTasks().get(testTask3.getId()));
    }

    @Test
    void shouldDeleteTaskByIdInTasksManager() {
        Task testTask = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        int testTaskId = testTask.getId();
        taskManager.deleteTaskById(testTaskId);
        assertNull(taskManager.getTaskById(testTaskId), "Task не удалился.");

        assertNull(taskManager.deleteTaskById(-1),
                "Выполнена попытка удления несуществующего Task");
    }

    @Test
    void shouldCreateEpicWithoutSubtasksInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        assertEquals(Status.NEW, testEpic1.getStatus(), "Неверный статус Epic");

        Epic testEpic2 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        assertNull(testEpic2, "Создан Epic, с subTaskList = null");
    }

    @Test
    void shouldCreateEpicWithTwoSubtasksWIthStatusNEWInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        SubTask testSubTask1 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1Description", testEpic1.getId()));
        SubTask testSubTask2 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2Description", testEpic1.getId()));
        assertEquals(Status.NEW, testSubTask1.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.NEW, testSubTask2.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.NEW, testEpic1.getStatus(), "Неверный статус Epic");
    }

    @Test
    void shouldCreateEpicWithTwoSubtasksWIthStatusDONEInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        SubTask testSubTask1 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1Description", testEpic1.getId()));
        testSubTask1.setStatus(Status.DONE);
        fileBackedTasksManager.updateSubTaskByNewSubTask(testSubTask1);
        SubTask testSubTask2 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2Description", testEpic1.getId()));
        testSubTask2.setStatus(Status.DONE);
        fileBackedTasksManager.updateSubTaskByNewSubTask(testSubTask2);
        assertEquals(Status.DONE, testSubTask1.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.DONE, testSubTask2.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.DONE, testEpic1.getStatus(), "Неверный статус Epic");
    }

    @Test
    void shouldCreateEpicWithTwoSubtasksWIthStatusesNEWandDONEInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        SubTask testSubTask1 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1Description", testEpic1.getId()));
        SubTask testSubTask2 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2Description", testEpic1.getId()));
        testSubTask2.setStatus(Status.DONE);
        fileBackedTasksManager.updateSubTaskByNewSubTask(testSubTask2);
        assertEquals(Status.NEW, testSubTask1.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.DONE, testSubTask2.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.IN_PROGRESS, testEpic1.getStatus(), "Неверный статус Epic");
    }

    @Test
    void shouldCreateEpicWithTwoSubtasksWIthStatusIN_PROGRESSInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        SubTask testSubTask1 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1Description", testEpic1.getId()));
        testSubTask1.setStatus(Status.IN_PROGRESS);
        fileBackedTasksManager.updateSubTaskByNewSubTask(testSubTask1);
        SubTask testSubTask2 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2Description", testEpic1.getId()));
        testSubTask2.setStatus(Status.IN_PROGRESS);
        fileBackedTasksManager.updateSubTaskByNewSubTask(testSubTask2);
        assertEquals(Status.IN_PROGRESS, testSubTask1.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.IN_PROGRESS, testSubTask2.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.IN_PROGRESS, testEpic1.getStatus(), "Неверный статус Epic");
    }

    @Test
    void shouldGetListOfEpicsInFileBackedTasksManager() {
        fileBackedTasksManager.deleteAllTasks();
        Map<Integer, Epic> testList = fileBackedTasksManager.getListOfEpics();
        assertEquals(0, testList.size(), " Ошибка при формировании taskList");

        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic1",
                "testEpic1 description"));
        Epic testEpic2 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic2",
                "testEpic2 description"));
        Epic testEpic3 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic3",
                "testEpic3 description"));
        assertEquals(3, testList.size(), "Ошибка при формировании SubtaskList");
        assertEquals(testEpic1, fileBackedTasksManager.getListOfEpics().get(testEpic1.getId()),
                "Ошибка при формировании Epics");
        assertEquals(testEpic2, fileBackedTasksManager.getListOfEpics().get(testEpic2.getId()),
                "Ошибка при формировании Epics");
        assertEquals(testEpic3, fileBackedTasksManager.getListOfEpics().get(testEpic3.getId()),
                "Ошибка при формировании Epics");
    }

    @Test
    void shouldDeleteEpicByIdInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        SubTask testSubTask1 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1Description", testEpic1.getId()));
        int idOfTestEpic1 = testEpic1.getId();
        int idOftestSubTask1 = testSubTask1.getEpicId();
        fileBackedTasksManager.deleteEpicById(idOfTestEpic1);
        assertNull(fileBackedTasksManager.getEpicById(idOfTestEpic1), "Epic не удален");
        assertNull(fileBackedTasksManager.getSubTaskById(idOftestSubTask1), "SubTask  не удален");

        assertNull(fileBackedTasksManager.deleteEpicById(-1), "Выполнена попытка удления несуществующего Epic");
    }

    @Test
    void shouldGetListOfSubTasksOfEpicInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        assertNull(fileBackedTasksManager.getListOfSubTasksOfEpic(-1),
                "При вызове ListOfSubTasks несуществующего Epic не возвращается  Null");
        SubTask testSubTask1 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        SubTask testSubTask2 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        List<Integer> listOfSubTask = fileBackedTasksManager.getListOfSubTasksOfEpic(testEpic1.getId());
        assertEquals(2, listOfSubTask.size());
        assertEquals(testSubTask1.getId(), listOfSubTask.get(0), "Получен неверный Id для SubTask " +
                testSubTask1.getId());
        assertEquals(testSubTask2.getId(), listOfSubTask.get(1), "Получен неверный Id для SubTask " +
                testSubTask1.getId());
    }

    @Test
    void shouldUpdateEpicInFileBackedTasksManager() {
        Epic testEpic = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        testEpic.setName("newName");
        testEpic.setDescription("newDescription");
        testEpic.setStatus(Status.IN_PROGRESS);
        testEpic.setStartTime(LocalDateTime.parse("10.09.2022 12:43", dateTimeFormatter));
        testEpic.setDuration(90);
        Epic updatedTestEpic = fileBackedTasksManager.updateEpicByNewEpic(testEpic);
        assertEquals(testEpic, updatedTestEpic, "Epic не обновился.");
        assertEquals(updatedTestEpic.getName(), "newName", "Имя Epic не обновилось.");
        assertEquals(updatedTestEpic.getDescription(), "newDescription", "Описание SEpic не обновилось.");
        assertEquals(updatedTestEpic.getStartTime().format(dateTimeFormatter), "10.09.2022 12:43",
                "Время начала Epic не обновилось.");
        assertEquals(updatedTestEpic.getDuration(), 90, "Длительность Epic не обновилось.");
        assertEquals(Status.NEW, updatedTestEpic.getStatus(), "Статус Epic не обновился.");

        testEpic.setStatus(null);
        assertEquals(Status.NEW, testEpic.getStatus(), "Статус Epic некоректен.");

        Epic testEpic2 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        testEpic2.setId(Integer.MAX_VALUE);
        updatedTestEpic = fileBackedTasksManager.updateEpicByNewEpic(testEpic2);
        assertNull(updatedTestEpic, "Прошло обновление Epic не входящего в epicList");
    }

    @Test
    void shouldCreateEpicWithoutSubtasksInTaskManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        assertEquals(Status.NEW, testEpic1.getStatus(), "Неверный статус Epic");

        Epic testEpic2 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        assertNull(testEpic2, "Создан Epic, с subTaskList = null");
    }

    @Test
    void shouldCreateEpicWithTwoSubtasksWIthStatusNEWInTaskManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask1 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        SubTask testSubTask2 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        assertEquals(Status.NEW, testSubTask1.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.NEW, testSubTask2.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.NEW, testEpic1.getStatus(), "Неверный статус Epic");
    }

    @Test
    void shouldCreateEpicWithTwoSubtasksWIthStatusDONEInTaskManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask1 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        testSubTask1.setStatus(Status.DONE);
        taskManager.updateSubTaskByNewSubTask(testSubTask1);
        SubTask testSubTask2 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        testSubTask2.setStatus(Status.DONE);
        taskManager.updateSubTaskByNewSubTask(testSubTask2);
        assertEquals(Status.DONE, testSubTask1.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.DONE, testSubTask2.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.DONE, testEpic1.getStatus(), "Неверный статус Epic");
    }

    @Test
    void shouldCreateEpicWithTwoSubtasksWIthStatusesNEWandDONEInTaskManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        SubTask testSubTask1 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        SubTask testSubTask2 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        testSubTask2.setStatus(Status.DONE);
        taskManager.updateSubTaskByNewSubTask(testSubTask2);
        assertEquals(Status.NEW, testSubTask1.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.DONE, testSubTask2.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.IN_PROGRESS, testEpic1.getStatus(), "Неверный статус Epic");
    }

    @Test
    void shouldCreateEpicWithTwoSubtasksWIthStatusIN_PROGRESSInTaskManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        SubTask testSubTask1 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        testSubTask1.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTaskByNewSubTask(testSubTask1);
        SubTask testSubTask2 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        testSubTask2.setStatus(Status.IN_PROGRESS);
        taskManager.updateSubTaskByNewSubTask(testSubTask2);
        assertEquals(Status.IN_PROGRESS, testSubTask1.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.IN_PROGRESS, testSubTask2.getStatus(), "Неверный статус subTask1");
        assertEquals(Status.IN_PROGRESS, testEpic1.getStatus(), "Неверный статус Epic");
    }

    @Test
    void shouldDeleteEpicByIdInTasksManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1Description"));
        SubTask testSubTask1 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        int idOfTestEpic1 = testEpic1.getId();
        int idOftestSubTask1 = testSubTask1.getEpicId();
        taskManager.deleteEpicById(idOfTestEpic1);
        assertNull(taskManager.getEpicById(idOfTestEpic1), "Epic не удален");
        assertNull(taskManager.getSubTaskById(idOftestSubTask1), "SubTask входяший в Epic не удален");

        assertNull(taskManager.deleteEpicById(-1), "Выполнена попытка удления несуществующего Epic");
    }

    @Test
    void shouldGetListOfSubTasksOfEpicInTasksManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        assertNull(taskManager.getListOfSubTasksOfEpic(-1),
                "При вызове ListOfSubTasks несуществующего Epic не возвращается  Null");
        SubTask testSubTask1 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        SubTask testSubTask2 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        List<Integer> listOfSubTask = taskManager.getListOfSubTasksOfEpic(testEpic1.getId());
        assertEquals(2, taskManager.getListOfSubTasksOfEpic(testEpic1.getId()).size());
        assertEquals(testSubTask1.getId(), listOfSubTask.get(0), "Получен неверный Id для SubTask " +
                testSubTask1.getId());
        assertEquals(testSubTask2.getId(), listOfSubTask.get(1), "Получен неверный Id для SubTask " +
                testSubTask1.getId());
    }

    @Test
    void shouldUpdateEpicInTasksManager() {
        Epic testEpic = taskManager.creationOfEpic(new Epic(10, "testEpic", "testEpic1 description"
               ));
        testEpic.setName("newName");
        testEpic.setDescription("newDescription");
        testEpic.setStatus(Status.IN_PROGRESS);
        testEpic.setStartTime(LocalDateTime.parse("10.09.2022 12:43", dateTimeFormatter));
        testEpic.setDuration(90);
        Epic updatedTestEpic = taskManager.updateEpicByNewEpic(testEpic);
        assertEquals(testEpic, updatedTestEpic, "Epic не обновился.");
        assertEquals(updatedTestEpic.getName(), "newName", "Имя Epic не обновилось.");
        assertEquals(updatedTestEpic.getDescription(), "newDescription", "Описание SEpic не обновилось.");
        assertEquals(updatedTestEpic.getStartTime().format(dateTimeFormatter), "10.09.2022 12:43",
                "Время начала Epic не обновилось.");
        assertEquals(updatedTestEpic.getDuration(), 90, "Длительность Epic не обновилось.");
        assertEquals(Status.NEW, updatedTestEpic.getStatus(), "Статус Epic не обновился.");

        Epic testEpic2 = taskManager.creationOfEpic(new Epic(10, "testEpic", "testEpic1Description"
        ));
        testEpic2.setId(Integer.MAX_VALUE);
        updatedTestEpic = taskManager.updateEpicByNewEpic(testEpic2);
        assertNull(updatedTestEpic, "Прошло обновление Epic не входящего в epicList");
    }

    @Test
    void shouldGetListOfEpicsInTasksManager() {
        taskManager.deleteAllTasks();
        Map<Integer, Epic> testList = taskManager.getListOfEpics();
        assertEquals(0, testList.size(), " Ошибка при формировании taskList");

        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic1",
                "testEpic1 description"));
        Epic testEpic2 = taskManager.creationOfEpic(new Epic(10, "testEpic2",
                "testEpic2 description"));
        Epic testEpic3 = taskManager.creationOfEpic(new Epic(10, "testEpic3",
                "testEpic3 description"));
        assertEquals(3, testList.size(), "Ошибка при формировании SubtaskList");
        assertEquals(testEpic1, taskManager.getListOfEpics().get(testEpic1.getId()),
                "Ошибка при формировании Epics");
        assertEquals(testEpic2, taskManager.getListOfEpics().get(testEpic2.getId()),
                "Ошибка при формировании Epics");
        assertEquals(testEpic3, taskManager.getListOfEpics().get(testEpic3.getId()),
                "Ошибка при формировании Epics");
    }

    @Test
    void shouldCreateNewSubTaskInFileBackedTasksManager() {
        Epic testEpic = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic.getId()));
        int testSubTaskId = testSubTask.getId();
        SubTask savedTestSubTask = fileBackedTasksManager.getSubTaskById(testSubTaskId);
        assertNotNull(testSubTask, "SubTask не создан.");
        assertNotNull(savedTestSubTask, "SubTask на возвращается по Id.");
        assertEquals(testSubTask, savedTestSubTask, "Созданный SubTask и возвращенный по Id не совпадают.");

        SubTask testSubTask2 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", Integer.MAX_VALUE));
        assertNull(testSubTask2, "Создан SubTask для несуществующего Epic");
    }

    @Test
    void shouldUpdateSubTaskInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        testSubTask.setName("newName");
        testSubTask.setDescription("newDescription");
        testSubTask.setStatus(Status.IN_PROGRESS);
        testSubTask.setStartTime(LocalDateTime.parse("10.09.2022 12:43", dateTimeFormatter));
        testSubTask.setDuration(90);
        SubTask updatedTestSubTask = fileBackedTasksManager.updateSubTaskByNewSubTask(testSubTask);
        assertEquals(testSubTask, updatedTestSubTask, "SubTask не обновился.");
        assertEquals(updatedTestSubTask.getName(), "newName", "Имя SubTask не обновилось.");
        assertEquals(updatedTestSubTask.getDescription(), "newDescription",
                "Описание SubTask не обновилось.");
        assertEquals(updatedTestSubTask.getStartTime().format(dateTimeFormatter), "10.09.2022 12:43",
                "Время начала SubTask не обновилось.");
        assertEquals(updatedTestSubTask.getDuration(), 90, "Длительность SubTask не обновилось.");
        assertEquals(Status.IN_PROGRESS, updatedTestSubTask.getStatus(), "Статус SubTask не обновился.");

        testSubTask.setStatus(null);
        assertEquals(Status.IN_PROGRESS, testSubTask.getStatus(), "Статус SubTask некоректен.");

        testSubTask = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        testSubTask. setEpicId(Integer.MAX_VALUE);
        updatedTestSubTask = fileBackedTasksManager.updateSubTaskByNewSubTask(testSubTask);
        assertNull(updatedTestSubTask, "Прошло обновление SubTask на SubTask не хранящийся в listOfSubTask.");

        testSubTask = new SubTask(Integer.MAX_VALUE, "testSubTask1", "testSubTask1 description",
                testEpic1.getId());
        updatedTestSubTask = fileBackedTasksManager.updateSubTaskByNewSubTask(testSubTask);
        assertNull(updatedTestSubTask, "Проведено обновление SubTask на SubTask не хранящийся в subTaskList.");
    }

    @Test
    void shouldSetDateTimeAndDurationInFileBackedTasksManager() {
        fileBackedTasksManager.deleteAllTasks();
        Task testTask1 = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        fileBackedTasksManager.setTaskAndSubTaskStartDateTime(testTask1, "10.09.2023 12:00");
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask1 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        SubTask testSubTask2 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        fileBackedTasksManager.setTaskAndSubTaskStartDateTime(testSubTask1, "10.09.2023 12:00");
        assertEquals(fileBackedTasksManager.getStartDateTime(testTask1), LocalDateTime.parse("10.08.2023 12:00",
                dateTimeFormatter));
        assertNull(fileBackedTasksManager.getStartDateTime(testSubTask1), "Пересесчение времени с другой Task");

        fileBackedTasksManager.setTaskAndSubTaskDuration(testTask1, 120);
        fileBackedTasksManager.setTaskAndSubTaskStartDateTime(testSubTask1, "10.09.2023 14:00");
        assertNull(fileBackedTasksManager.getStartDateTime(testSubTask1), "Пересесчение времени с другой Task");
        assertEquals(120, fileBackedTasksManager.getTaskDuration(testTask1),
                "Неверно установлена длительность Task");

        fileBackedTasksManager.setTaskAndSubTaskStartDateTime(testSubTask1, "10.09.2023 11:00");
        assertEquals(LocalDateTime.parse("20.08.2024 10:00", dateTimeFormatter),
                fileBackedTasksManager.getStartDateTime(testSubTask1));
        fileBackedTasksManager.setTaskAndSubTaskDuration(testSubTask1, 120);
        assertEquals(0, fileBackedTasksManager.getTaskDuration(testSubTask1),
                "Неверно установлена длительность SubTask");
        fileBackedTasksManager.setTaskAndSubTaskDuration(testSubTask1, 30);
        assertEquals(30, fileBackedTasksManager.getTaskDuration(testSubTask1),
                "Неверно установлена длительность SubTask");
        assertEquals(LocalDateTime.parse("20.08.2024 10:00", dateTimeFormatter),
                fileBackedTasksManager.getStartDateTime(testEpic1), "Неверно установлена длительность Epic");
        assertEquals(0, fileBackedTasksManager.getTaskDuration(testEpic1),
                "Неверно установлена длительность Epic");

        fileBackedTasksManager.setTaskAndSubTaskStartDateTime(testSubTask2, "10.09.2023 10:00");
        fileBackedTasksManager.setTaskAndSubTaskDuration(testSubTask2, 10);
        assertEquals(90, fileBackedTasksManager.getTaskDuration(testEpic1),
                "Неверно установлена длительность Epic");
    }

    @Test
    void shouldGetListOfSubTasksInFileBackedTasksManager() {
        fileBackedTasksManager.deleteAllTasks();
        Map<Integer, SubTask> testList = fileBackedTasksManager.getListOfSubTasks();
        assertEquals(0, testList.size(), " Ошибка при формировании taskList");

        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask1 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        SubTask testSubTask2 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        SubTask testSubTask3 = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask3",
                "testSubTask3 description", testEpic1.getId()));
        assertEquals(3, testList.size(), "Ошибка при формировании SubtaskList");
        assertEquals(testSubTask1, fileBackedTasksManager.getListOfSubTasks().get(testSubTask1.getId()),
                "Ошибка при формировании SubtaskList");
        assertEquals(testSubTask2, fileBackedTasksManager.getListOfSubTasks().get(testSubTask2.getId()),
                "Ошибка при формировании SubtaskList");
        assertEquals(testSubTask3, fileBackedTasksManager.getListOfSubTasks().get(testSubTask3.getId()),
                "Ошибка при формировании SubtaskList");
    }

    @Test
    void shouldDeleteSubTaskByIdInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        int testSubTaskId = testSubTask.getId();
        fileBackedTasksManager.deleteSubTaskById(testSubTaskId);
        assertNull(fileBackedTasksManager.getTaskById(testSubTaskId), "SubTask не удалился.");

        assertNull(fileBackedTasksManager.deleteSubTaskById(-1), "Попытка удления несуществующего SubTask");
    }

    @Test
    void shouldCreateNewSubTaskInTaskManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        int testSubTaskId = testSubTask.getId();
        SubTask savedTestSubTask = taskManager.getSubTaskById(testSubTaskId);
        assertNotNull(testSubTask, "SubTask не создан.");
        assertNotNull(savedTestSubTask, "SubTask на возвращается по Id.");
        assertEquals(testSubTask, savedTestSubTask, "Созданный SubTask и возвращенный по Id не совпадают.");

        SubTask testSubTask2 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", Integer.MAX_VALUE));
        assertNull(testSubTask2, "Создан SubTask для несуществующего Epic");
    }

    @Test
    void shouldUpdateSubTaskInTaskManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        testSubTask.setName("newName");
        testSubTask.setDescription("newDescription");
        testSubTask.setStatus(Status.IN_PROGRESS);
        testSubTask.setStartTime(LocalDateTime.parse("10.09.2023 12:43", dateTimeFormatter));
        testSubTask.setDuration(90);
        SubTask updatedTestSubTask = taskManager.updateSubTaskByNewSubTask(testSubTask);
        assertEquals(testSubTask, updatedTestSubTask, "SubTask не обновился.");
        assertEquals(updatedTestSubTask.getName(), "newName", "Имя SubTask не обновилось.");
        assertEquals(updatedTestSubTask.getDescription(), "newDescription", "Описание не обновилось.");
        assertEquals(updatedTestSubTask.getStartTime().format(dateTimeFormatter), "10.09.2023 12:43",
                "Время начала SubTask не обновилось.");
        assertEquals(updatedTestSubTask.getDuration(), 90, "Длительность SubTask не обновилось.");
        assertEquals(Status.IN_PROGRESS, updatedTestSubTask.getStatus(), "Статус SubTask не обновился.");

        testSubTask.setStatus(null);
        assertEquals(Status.IN_PROGRESS, testSubTask.getStatus(), "Статус SubTask некоректен.");

        testSubTask = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        testSubTask.setEpicId(Integer.MAX_VALUE);
        updatedTestSubTask = taskManager.updateSubTaskByNewSubTask(testSubTask);
        assertNull(updatedTestSubTask, "Проведено обновление по SubTask не хранящийся в listOfSubTask.");

        testSubTask = new SubTask(Integer.MAX_VALUE, "testSubTask1", "testSubTask1 description",
                testEpic1.getId());
        updatedTestSubTask = taskManager.updateSubTaskByNewSubTask(testSubTask);
        assertNull(updatedTestSubTask, "Проведено обновление SubTask на SubTask не хранящийся в subTaskList.");
    }

    @Test
    void shouldSetDateTimeAndDurationInTasksManager() {
        taskManager.deleteAllTasks();
        Task testTask1 = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        taskManager.setTaskAndSubTaskStartDateTime(testTask1, "10.09.2023 12:00");
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask1 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        SubTask testSubTask2 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        taskManager.setTaskAndSubTaskStartDateTime(testSubTask1, "10.09.2023 11:00");
        assertEquals(taskManager.getStartDateTime(testTask1), LocalDateTime.parse("10.09.2023 12:00",
                dateTimeFormatter));
        assertNull(taskManager.getStartDateTime(testSubTask1), "Установлено время занятое другой Task");

        taskManager.setTaskAndSubTaskDuration(testTask1, 120);
        taskManager.setTaskAndSubTaskStartDateTime(testSubTask1, "10.09.2023 14:00");
        assertNull(taskManager.getStartDateTime(testSubTask1), "Установлено время занятое другой Task");
        assertEquals(120, taskManager.getTaskDuration(testTask1), "Неверная  длительность Task");

        taskManager.setTaskAndSubTaskStartDateTime(testSubTask1, "11.09.2023 10:00");
        assertEquals(LocalDateTime.parse("10.09.2023 11:00", dateTimeFormatter),
                taskManager.getStartDateTime(testSubTask1));
        taskManager.setTaskAndSubTaskDuration(testSubTask1, 120);
        assertEquals(0, taskManager.getTaskDuration(testSubTask1), "Неверная  длительность Task");
        taskManager.setTaskAndSubTaskDuration(testSubTask1, 30);
        assertEquals(30, taskManager.getTaskDuration(testSubTask1), "Неверная  длительность Task");
        assertEquals(LocalDateTime.parse("10.09.2023 11:00", dateTimeFormatter),
                taskManager.getStartDateTime(testEpic1), "Неверно установлена длительность Epic");
        assertEquals(0, taskManager.getTaskDuration(testEpic1), "Неверная  длительность Epic");

        taskManager.setTaskAndSubTaskStartDateTime(testSubTask2, "10.09.2023 10:00");
        taskManager.setTaskAndSubTaskDuration(testSubTask2, 10);
        assertEquals(90, taskManager.getTaskDuration(testEpic1), "Неверная  длительность Epic");
    }

    @Test
    void shouldGetListOfSubTasksInTasksManager() {
        taskManager.deleteAllTasks();
        Map<Integer, SubTask> testList = taskManager.getListOfSubTasks();
        assertEquals(0, testList.size(), " Ошибка при формировании taskList");

        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask1 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        SubTask testSubTask2 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask2",
                "testSubTask2 description", testEpic1.getId()));
        SubTask testSubTask3 = taskManager.creationOfSubTask(new SubTask(10, "testSubTask3",
                "testSubTask3 description", testEpic1.getId()));
        assertEquals(3, testList.size(), "Ошибка при формировании SubtaskList");
        assertEquals(testSubTask1, taskManager.getListOfSubTasks().get(testSubTask1.getId()),
                "Ошибка при формировании SubtaskList");
        assertEquals(testSubTask2, taskManager.getListOfSubTasks().get(testSubTask2.getId()),
                "Ошибка при формировании SubtaskList");
        assertEquals(testSubTask3, taskManager.getListOfSubTasks().get(testSubTask3.getId()),
                "Ошибка при формировании SubtaskList");
    }

    @Test
    void shouldDeleteSubTaskByIdInTaskManager() {
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        int testSubTaskId = testSubTask.getId();
        taskManager.deleteSubTaskById(testSubTaskId);
        assertNull(taskManager.getTaskById(testSubTaskId), "SubTask не удалился.");

        assertNull(taskManager.deleteSubTaskById(-1), "Выполнена попытка удления несуществующего SubTask");
    }

    @Test
    void shouldGetListOfAllTasksInFileBackedTasksManager() {
        fileBackedTasksManager.deleteAllTasks();
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        Task testTask = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        assertEquals(3, fileBackedTasksManager.getListOfAllTasks().size());
    }

    @Test
    void shouldGetPrioritizedTasksInFileBackedTasksManager() {
        fileBackedTasksManager.deleteAllTasks();
        Task testTask = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        Task testTask2 = fileBackedTasksManager.creationOfTask(new Task(10, "testTask2",
                "testTask2 description"));
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        fileBackedTasksManager.setTaskAndSubTaskStartDateTime(testTask, "06.06.2026 16:06");
        fileBackedTasksManager.setTaskAndSubTaskStartDateTime(testTask2, "06.06.2026 13:06");
        fileBackedTasksManager.setTaskAndSubTaskStartDateTime(testSubTask, "06.06.2026 11:06");
        List<Task> resultListOfTime = fileBackedTasksManager.getPrioritizedTasks();
        assertEquals(testEpic1, resultListOfTime.get(0));
        assertEquals(testSubTask, resultListOfTime.get(1));
        assertEquals(testTask2, resultListOfTime.get(2));
        assertEquals(testTask, resultListOfTime.get(3));
    }

    @Test
    void shouldDeleteAllTasksInFileBackedTasksManager() {
        Epic testEpic1 = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        Task testTask = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        fileBackedTasksManager.deleteAllTasks();
        assertEquals(0, fileBackedTasksManager.getListOfAllTasks().size());
    }

    @Test
    void shouldGetHistoryInFileBackedTasksManager() {
        fileBackedTasksManager.deleteAllTasks();
        Epic testEpic = fileBackedTasksManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = fileBackedTasksManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic.getId()));
        Task testTask = fileBackedTasksManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        fileBackedTasksManager.getEpicById(testEpic.getId());
        fileBackedTasksManager.getSubTaskById(testSubTask.getId());
        fileBackedTasksManager.getTaskById(testTask.getId());
        List<Task> history = new ArrayList<>(fileBackedTasksManager.getHistory());
        StringBuilder expectedHistory = new StringBuilder();
        StringBuilder resultHistory = new StringBuilder();
        for (Task task : history) {
            resultHistory.append(task.getId()).append(",");
        }
        expectedHistory.append(testEpic.getId()).append(",").
                append(testSubTask.getId()).append(",").
                append(testTask.getId()).append(",");
        assertEquals(expectedHistory.toString(), resultHistory.toString(),
                "Неверно отображается история вызовов Task");
        fileBackedTasksManager.getTaskById(testTask.getId());
        fileBackedTasksManager.getTaskById(testTask.getId());
        fileBackedTasksManager.getSubTaskById(testSubTask.getId());
        fileBackedTasksManager.getSubTaskById(testSubTask.getId());
        fileBackedTasksManager.getEpicById(testEpic.getId());
        fileBackedTasksManager.getEpicById(testEpic.getId());
        history = new ArrayList<>(fileBackedTasksManager.getHistory());
        resultHistory.delete(0, resultHistory.length());
        for (Task task : history) {
            resultHistory.append(task.getId()).append(",");
        }
        expectedHistory.delete(0, expectedHistory.length()).
                append(testTask.getId()).append(",").
                append(testSubTask.getId()).append(",").
                append(testEpic.getId()).append(",");
        assertEquals(expectedHistory.toString(), resultHistory.toString(),
                "Неверно отображается история вызовов Task");
    }

    @Test
    void shouldGetListOfAllTasksInTasksManager() {
        taskManager.deleteAllTasks();
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        Task testTask = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        assertEquals(3, taskManager.getListOfAllTasks().size());
    }

    @Test
    void shouldDeleteAllTasksInTasksManager() {
        taskManager.deleteAllTasks();
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        Task testTask = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        taskManager.deleteAllTasks();
        assertEquals(0, taskManager.getListOfAllTasks().size());
    }

    @Test
    void shouldGetHistoryInTasksManager() {
        taskManager.deleteAllTasks();
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        Task testTask = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        taskManager.getEpicById(1);
        taskManager.getSubTaskById(2);
        taskManager.getTaskById(3);
        List<Task> history = new ArrayList<>(taskManager.getHistory());
        StringBuilder resultHistory = new StringBuilder();
        for (Task task : history) {
            resultHistory.append(task.getId()).append(",");
        }
        assertEquals("1,2,3,", resultHistory.toString(), "Неверно отображается история вызовов Task");
        taskManager.getTaskById(3);
        taskManager.getTaskById(3);
        taskManager.getSubTaskById(2);
        taskManager.getSubTaskById(2);
        taskManager.getEpicById(1);
        taskManager.getEpicById(1);
        history = new ArrayList<>(taskManager.getHistory());
        resultHistory.delete(0, resultHistory.length());
        for (Task task : history) {
            resultHistory.append(task.getId()).append(",");
        }
        assertEquals("3,2,1,", resultHistory.toString(), "Неверно отображается история вызовов Task");
    }

    @Test
    void shouldGetPrioritizedTasksInTasksManager() {
        taskManager.deleteAllTasks();
        Task testTask = taskManager.creationOfTask(new Task(10, "testTask1",
                "testTask1 description"));
        Task testTask2 = taskManager.creationOfTask(new Task(10, "testTask2",
                "testTask2 description"));
        Epic testEpic1 = taskManager.creationOfEpic(new Epic(10, "testEpic",
                "testEpic1 description"));
        SubTask testSubTask = taskManager.creationOfSubTask(new SubTask(10, "testSubTask1",
                "testSubTask1 description", testEpic1.getId()));
        taskManager.setTaskAndSubTaskStartDateTime(testTask, "06.06.2026 16:06");
        taskManager.setTaskAndSubTaskStartDateTime(testTask2, "06.06.2026 13:06");
        taskManager.setTaskAndSubTaskStartDateTime(testSubTask, "06.06.2026 11:06");
        List<Task> resultListOfTime = taskManager.getPrioritizedTasks();
        assertEquals(testEpic1, resultListOfTime.get(0));
        assertEquals(testSubTask, resultListOfTime.get(1));
        assertEquals(testTask2, resultListOfTime.get(2));
        assertEquals(testTask, resultListOfTime.get(3));
    }
}