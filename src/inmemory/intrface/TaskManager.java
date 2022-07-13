package inmemory.intrface;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TypeTask;

import java.util.List;
import java.util.Map;

public interface TaskManager {

    TypeTask getType ();

    Task creationOfTask (Task task);

    Epic creationOfEpic (Epic epic);

    SubTask creationOfSubTask (SubTask subTask);

    SubTask updateStatusSubTask (SubTask subTask);

    Map<Integer, Task> getListOfAllTasks ();

    Map<Integer, Task> getListOfTasks ();

    Map<Integer, Epic> getListOfEpics ();

    Map<Integer, SubTask> getListOfSubTasks ();

    void deleteAllTasks ();

    void deleteALLTasks ();

    void deleteAllEpics ();

    void deleteAllSubTasks ();

    Task getTaskById (Integer id);

    Epic getEpicById (Integer id);

    SubTask getSubTaskById (Integer id);

    Task updateTaskByNewTask (Task task);

    Epic updateEpicByNewEpic (Epic epic);

    SubTask updateSubTaskByNewSubTask (SubTask subTask);

    Task deleteTaskById (Integer id);

    Epic deleteEpicById (Integer id);

    SubTask deleteSubTaskById (Integer id);

    List<Integer> getListOfSubTasksOfEpic (Integer epicId);

    List<Task> getHistory ();
}