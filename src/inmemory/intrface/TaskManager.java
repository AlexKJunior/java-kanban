package inmemory.intrface;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.List;
import java.util.Map;

public interface TaskManager {

    Task creationOfTask (Task task);

    Epic creationOfEpic (Epic epic);

    SubTask creationOfSubTask (SubTask subTask);

    SubTask updateStatusSubTask (SubTask subTask);

    Map<Integer, Task> getListOfAllTasks ();

    Map<Integer, Task> getListOfTasks ();

    Map<Integer, Epic> getListOfEpics ();

    Map<Integer, SubTask> getListOfSubTasks ();

    Map<Integer, Task> deleteAllTasks ();

    Map<Integer, Task> deleteTasks ();

    Map<Integer, Epic> deleteEpics ();

    Map<Integer, Task> deleteALLTasks ();

    Map<Integer, Epic> deleteAllEpics ();

    Map<Integer, SubTask> deleteSubTasks ();

    Map<Integer, SubTask> deleteAllSubTasks ();

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