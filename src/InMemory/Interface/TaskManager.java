package InMemory.Interface;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.Map;

public interface TaskManager {

    Task creationOfTask(Task task);

    Epic creationOfEpic(Epic epic);

    SubTask creationOfSubTask(SubTask subTask);

    Map<Integer, Task> getListOfAllTasks();

    Map<Integer, Epic> getListOfEpics();

    Task getTaskById(Integer id);

    Epic getEpicById(Integer id);

    SubTask getSubTaskById(Integer id);

    void updateTaskByNewTask(Task task);

    void updateSubTaskByNewSubTask(SubTask subTask);

    void deleteTaskById(Integer id);

    void deleteEpicById(Integer id);
}