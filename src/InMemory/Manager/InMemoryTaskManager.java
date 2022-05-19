package InMemory.Manager;

import InMemory.Interface.HistoryManager;
import InMemory.Interface.TaskManager;
import InMemory.Managers;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;

public  class InMemoryTaskManager implements TaskManager {
    private static int uniqueTaskId = 0;
    private final HashMap<Integer, Task> taskList;
    private final HashMap<Integer, SubTask> subTaskList;
    private final HashMap<Integer, Epic> epicList;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.taskList = new HashMap<>();
        this.subTaskList = new HashMap<>();
        this.epicList = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public Task creationOfTask(Task task) {
        uniqueTaskId++;
        Task newTask = new Task(uniqueTaskId, task.getName(), task.getDescription());
        newTask.setStatus(Status.NEW);
        taskList.put(uniqueTaskId, newTask);
        return newTask;
    }

    @Override
    public Epic creationOfEpic(Epic epic) {
        if (epic.getSubTaskIdList() != null) {
            uniqueTaskId++;
            Epic newEpic = new Epic(uniqueTaskId, epic.getName(), epic.getDescription(), epic.getSubTaskIdList());
            newEpic.setStatus(Status.NEW);
            epicList.put(uniqueTaskId, newEpic);
            return newEpic;
        } else {
            return null;
        }
    }

    @Override
    public SubTask creationOfSubTask(SubTask subTask) {
        if (epicList.containsKey(subTask.getEpicId())) {
            uniqueTaskId++;
            SubTask newSubTask = new SubTask(uniqueTaskId, subTask.getName(), subTask.getDescription()
                    , subTask.getEpicId());
            newSubTask.setStatus(Status.NEW);
            subTaskList.put(uniqueTaskId, newSubTask);
            Epic epicForUpdate = epicList.get(subTask.getEpicId());
            epicForUpdate.getSubTaskIdList().add(uniqueTaskId);
            return newSubTask;
        } else {
            return null;
        }
    }

    @Override
    public HashMap<Integer, Task> getListOfAllTasks() {
        HashMap<Integer, Task> listOfAllTasks = new HashMap<>();
        listOfAllTasks.putAll(epicList);
        listOfAllTasks.putAll(taskList);
        listOfAllTasks.putAll(subTaskList);
        return listOfAllTasks;
    }

    @Override
    public HashMap<Integer, Epic> getListOfEpics() {
        return epicList;
    }

    @Override
    public Task getTaskById(Integer id) {
        if (taskList.get(id) != null) {
            historyManager.add(taskList.get(id));
            return taskList.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Epic getEpicById(Integer id) {
        if (epicList.get(id) != null) {
            historyManager.add(epicList.get(id));
            return epicList.get(id);
        } else {
            return null;
        }
    }

    @Override
    public SubTask getSubTaskById(Integer id) {
        if (subTaskList.get(id) != null) {
            historyManager.add(subTaskList.get(id));
            return subTaskList.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void updateTaskByNewTask(Task task) {
        if (taskList.containsKey(task.getId())) {
            taskList.replace(task.getId(), task);
        }
    }

    @Override
    public void updateSubTaskByNewSubTask(SubTask subTask) {
        Epic epicForCheckStatus = epicList.get(subTask.getEpicId());
        if (subTaskList.containsKey(subTask.getId())
                && epicForCheckStatus.getSubTaskIdList().contains(subTask.getId())) {
            epicForCheckStatus.setStatus(checkEpicStatus(epicForCheckStatus.getSubTaskIdList()));
        }
    }

    @Override
    public void deleteTaskById(Integer id) {
        taskList.remove(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        if (epicList.containsKey ( id )) {
            for (Integer subTaskId : epicList.get ( id ).getSubTaskIdList ()) {
                if (subTaskList.containsKey ( subTaskId ) && subTaskList.get ( subTaskId ).getEpicId () == id) {
                    subTaskList.remove ( subTaskId );
                } else {
                    return;
                }
            }
            epicList.remove ( id );
        }
    }

    private Status checkEpicStatus(ArrayList<Integer> subTaskIdList) {
        boolean isNew = false;
        boolean isInProgress = false;
        boolean isDone = false;
        if (subTaskIdList.isEmpty()) {
            return Status.NEW;
        } else {
            for (Integer subTaskId : subTaskIdList) {
                if ((subTaskList.get(subTaskId)).getStatus() == Status.NEW) {
                    isNew = true;
                } else if ((subTaskList.get(subTaskId)).getStatus() == Status.IN_PROGRESS) {
                    isInProgress = true;
                } else {
                    isDone = true;
                }
            }
            if ((isNew && !isInProgress && !isDone)) {
                return Status.NEW;
            } else if (isDone && !isNew && !isInProgress) {
                return Status.DONE;
            } else {
                return Status.IN_PROGRESS;
            }
        }
    }
}