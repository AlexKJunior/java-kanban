package inmemory.manager;

import inmemory.Managers;
import inmemory.intrface.HistoryManager;
import inmemory.intrface.TaskManager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    protected int uniqueTaskId = 1;
    protected final Map<Integer, Task> taskList;
    protected final Map<Integer, SubTask> subTaskList;
    protected final Map<Integer, Epic> epicList;
    protected final HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.taskList = new HashMap<>();
        this.subTaskList = new HashMap<>();
        this.epicList = new HashMap<>();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public Task creationOfTask(Task task) {
        uniqueTaskId++;
        task.setId(uniqueTaskId);
        task.setStatus(Status.NEW);
        taskList.put(uniqueTaskId, task);
        return task;
    }

    @Override
    public Epic creationOfEpic(Epic epic) {
        uniqueTaskId++;
        epic.setId(uniqueTaskId);
        epic.setStatus(Status.NEW);
        epicList.put(uniqueTaskId, epic);
        return epic;
    }

    @Override
    public SubTask creationOfSubTask (SubTask subTask) {
        uniqueTaskId++;
        subTask.setId(uniqueTaskId);
        subTask.setStatus(Status.NEW);
        subTaskList.put(uniqueTaskId, subTask);
        return subTask;

    }

    @Override
    public SubTask updateStatusSubTask (SubTask subTask) {

        if (epicList.containsKey(subTask.getEpicId())) {
            uniqueTaskId++;
            SubTask newSubTask = new SubTask(uniqueTaskId, subTask.getName(), subTask.getDescription(),
                    subTask.getEpicId());
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
    public  Map<Integer, Task> getListOfAllTasks () {
        Map<Integer, Task> listOfAllTasks = new LinkedHashMap<> ();
        listOfAllTasks.putAll(epicList);
        listOfAllTasks.putAll(taskList);
        listOfAllTasks.putAll(subTaskList);
        return listOfAllTasks;
    }

    @Override
    public HashMap<Integer, Task> getListOfTasks() {
        return new HashMap<>(taskList);
    }

    @Override
    public HashMap<Integer, Epic> getListOfEpics() {
        return new HashMap<>(epicList);
    }

    @Override
    public HashMap<Integer, SubTask> getListOfSubTasks() {
        return new HashMap<>(subTaskList);
    }

    @Override
    public void deleteAllTasks() {
        epicList.clear();
        taskList.clear();
        subTaskList.clear();
        historyManager.clear();
    }

    @Override
    public void deleteAllEpics () {
        subTaskList.clear();
        epicList.clear();
    }

    @Override
    public void deleteAllSubTasks () {
        for (Epic epic : epicList.values()) {
            epic.getSubTaskIdList().clear();
            epic.setStatus(Status.NEW);
        }
        subTaskList.clear();
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
    public Task updateTaskByNewTask(Task task) {
        if (taskList.containsKey(task.getId())) {
            return taskList.replace(task.getId(), task);
        } else {
            return null;
        }
    }

    @Override
    public Epic updateEpicByNewEpic(Epic epic) {
        if (epicList.containsKey(epic.getId())) {
            if (epic.updateSubTaskIdList()) {
                Epic replacedEpic = epicList.replace(epic.getId(), epic);
                epic.setStatus(checkEpicStatus(epic.getSubTaskIdList()));
                return replacedEpic;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public SubTask updateSubTaskByNewSubTask(SubTask subTask) {
        Epic epicForCheckStatus = epicList.get(subTask.getEpicId());
        if (subTaskList.containsKey(subTask.getId())
                && epicForCheckStatus.getSubTaskIdList().contains(subTask.getId())) {
            SubTask replacedSubTask = subTaskList.replace(subTask.getId(), subTask);
            epicForCheckStatus.setStatus(checkEpicStatus(epicForCheckStatus.getSubTaskIdList()));
            return replacedSubTask;
        } else {
            return null;
        }
    }

    @Override
    public Task deleteTaskById(Integer id) {
        if (taskList.containsKey(id)) {
            historyManager.remove(id);
            return taskList.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public Epic deleteEpicById(Integer id) {
        if (epicList.containsKey(id)) {
            for (Integer subTaskId : epicList.get(id).getSubTaskIdList()) {
                if (subTaskList.containsKey(subTaskId) && subTaskList.get(subTaskId).getEpicId() == id) {
                    historyManager.remove(subTaskId);
                    subTaskList.remove(subTaskId);
                } else {
                    return null;
                }
            }
            historyManager.remove(id);
            return epicList.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public SubTask deleteSubTaskById(Integer id) {
        if (subTaskList.containsKey(id)) {
            Integer idOfEpicForClearItSubTasksList = subTaskList.get(id).getEpicId();
            if (epicList.get(idOfEpicForClearItSubTasksList).getSubTaskIdList().contains(id)) {
                historyManager.remove(id);
                epicList.get(idOfEpicForClearItSubTasksList).getSubTaskIdList().remove(id);
                epicList.get(idOfEpicForClearItSubTasksList)
                        .setStatus(checkEpicStatus(epicList.get(idOfEpicForClearItSubTasksList).getSubTaskIdList()));
            } else {
                return null;
            }
            return subTaskList.remove(id);
        } else {
            return null;
        }
    }

    @Override
    public List<Integer> getListOfSubTasksOfEpic(Integer epicId) {
        if (epicList.containsKey(epicId)) {
            return epicList.get(epicId).getSubTaskIdList();
        } else {
            return null;
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Map<Integer, Task> getTasksMap () {
        return null;
    }

    @Override
    public Map<Integer, SubTask> getSubTasksMap () {
        return null;
    }

    protected Status checkEpicStatus(List<Integer> subTaskIdList) {
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
