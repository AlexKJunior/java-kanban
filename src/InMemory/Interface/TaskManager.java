package InMemory.Interface;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {

    Task creationOfTask(Task task);

    Epic creationOfEpic(Epic epic);

    SubTask creationOfSubTask(SubTask subTask);

    HashMap<Integer, Task> getListOfAllTasks();

    HashMap<Integer, Epic> getListOfEpics();

    Task getTaskById(Integer id);

    Epic getEpicById(Integer id);

    SubTask getSubTaskById(Integer id);

    void updateTaskByNewTask(Task task);

    void updateSubTaskByNewSubTask(SubTask subTask);

    void deleteTaskById(Integer id);

    void deleteEpicById(Integer id);

    ArrayList<Task> getHistory();
}