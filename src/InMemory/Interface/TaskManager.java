package InMemory.Interface;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.HashMap;

public interface TaskManager {

    HistoryManager<Task> getHistory();

    Task creationOfTask (Task task);

    Epic creationOfEpic (Epic epic);

    SubTask creationOfSubTask (SubTask subTask);

    Task getTaskById (Integer id);

    Epic getEpicById (Integer id);

    SubTask getSubTaskById (Integer id);

    HashMap<Integer, Task> getListOfAllTasks ();

    HashMap<Integer, Epic> getListOfEpics ();

    void updateTaskByNewTask (Task task);

    void updateSubTaskByNewSubTask (SubTask subTask);

    void deleteTaskById (Integer id);

    void deleteEpicById (Integer id);
}