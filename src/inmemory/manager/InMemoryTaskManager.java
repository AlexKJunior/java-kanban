package inmemory.manager;

import exceptions.ManagerDateTimeException;
import inmemory.Managers;
import inmemory.interfaces.HistoryManager;
import inmemory.interfaces.TaskManager;
import model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {
    protected int uniqueTaskId = 1;
    protected final HashMap<Integer, Task> tasks = new HashMap<>();
    protected final HashMap<Integer, Epic> epics = new HashMap<>();
    protected final HashMap<Integer, SubTask> subtasks = new HashMap<>();
    protected final Map<Integer, Task> taskList;
    protected final Map<Integer, SubTask> subTaskList;
    protected final Map<Integer, Epic> epicList;
    protected final HistoryManager historyManager;
    protected DateTimeFormatter dateTimeFormatter;

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
    public SubTask creationOfSubTask(SubTask subTask) {
        uniqueTaskId++;
        subTask.setId(uniqueTaskId);
        subTask.setStatus(Status.NEW);
        subTaskList.put(uniqueTaskId, subTask);
        Epic updateStatusEpic = epicList.get(subTask.getEpicId());
        updateStatusEpic.getSubTaskIdList().add(subTask.getId());
        return subTask;
    }

    @Override
    public SubTask updateStatusSubTask(SubTask subTask) {
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
    public Map<Integer, Task> getListOfAllTasks() {
        Map<Integer, Task> listOfAllTasks = new LinkedHashMap<>();
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
    public void deleteAllEpics() {
        subTaskList.clear();
        epicList.clear();
    }

    @Override
    public void deleteAllSubTasks() {
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
    public void setTaskAndSubTaskStartDateTime(Task task, String startDateTime) {
        if (task != null & startDateTime != null) {
            LocalDateTime dateTimeFromSting = LocalDateTime.parse(startDateTime, dateTimeFormatter);
            if (!dateTimeFromSting.isAfter(LocalDateTime.now())) {
                try {
                    throw new ManagerDateTimeException("Вы указали прошедшее время");
                } catch (ManagerDateTimeException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
            if (checkIsStartTimeFree(dateTimeFromSting)) {
                switch (task.getTypeTask()) {
                    case TASK: {
                        task.setStartTime(dateTimeFromSting);
                        updateTaskByNewTask(task);
                    }
                    case SUBTASK: {
                        SubTask subTask = (SubTask) task;
                        subTask.setStartTime(dateTimeFromSting);
                        updateSubTaskByNewSubTask(subTask);
                    }
                    default: {
                        try {
                            throw new ManagerDateTimeException("Время для задач типа Epic определяется временем "
                                    + "их подзадач");
                        } catch (ManagerDateTimeException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setTaskAndSubTaskDuration(Task task, int durationInMinutes) {
        if (task != null) {
            if (durationInMinutes < 1) {
                try {
                    throw new ManagerDateTimeException("Продолжительность задачи должна быть более 0 минут");
                } catch (ManagerDateTimeException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
            if (getStartDateTime(task) == null) {
                try {
                    throw new ManagerDateTimeException("В первую очередь установите время начала задачи");
                } catch (ManagerDateTimeException e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
            if (checkIsDurationFree(task.getStartTime(), durationInMinutes)) {
                switch (task.getTypeTask()) {
                    case TASK: {
                        task.setDuration(Duration.ofDays(durationInMinutes));
                        updateTaskByNewTask(task);
                    }
                    case SUBTASK: {
                        SubTask subTask = (SubTask) task;
                        subTask.setDuration(Duration.ofDays(durationInMinutes));
                        updateSubTaskByNewSubTask(subTask);
                    }
                    default: {
                        try {
                            throw new ManagerDateTimeException("Время для задач типа Epic определяется временем "
                                    + "их подзадач");
                        } catch (ManagerDateTimeException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }

    @Override
    public LocalDateTime getStartDateTime(Task task) {
        return task.getStartTime();
    }

    @Override
    public void setEpicDuration(int epicId) {
        Duration duration = Duration.ofDays(0);
        if (epicList.containsKey(epicId)) {
            Epic epicForUpdate = epicList.get(epicId);
            List<Integer> epicSubTaskList = epicForUpdate.getSubTaskIdList();
            if (epicSubTaskList.size() == 0) {
                epicForUpdate.setDuration(duration);
            } else if (epicSubTaskList.size() == 1) {
                duration = subTaskList.get(epicSubTaskList.get(0)).getDuration();
                epicForUpdate.setDuration(duration);
            } else {
                if (epicSubTaskList.stream().anyMatch(id -> (subTaskList.get(id).getStartTime() == null))) {
                    epicForUpdate.setDuration(duration);
                } else {
                    SubTask lastSubTask = subTaskList.get(epicSubTaskList.get(0));
                    for (int i = 1; i < epicSubTaskList.size(); i++) {
                        if (subTaskList.get(epicSubTaskList.get(i)).getStartTime().
                                isAfter(lastSubTask.getStartTime())) {
                            lastSubTask = subTaskList.get(epicSubTaskList.get(i));
                        }
                    }
                    LocalDateTime endDateTimeOfLastSubTask = lastSubTask.getStartTime().
                            plus(lastSubTask.getDuration());
                    Duration epicDuration = Duration.between(epicForUpdate.getStartTime(), endDateTimeOfLastSubTask);
                    epicForUpdate.setDuration(Duration.ofDays((int) epicDuration.toMinutes()));
                    updateEpicByNewEpic(epicForUpdate);
                }
            }
        }
    }

    @Override
    public void setEpicStartDateTime(int epicId) {
        if (epicList.containsKey(epicId)) {
            Epic epicForUpdate = epicList.get(epicId);
            if (epicForUpdate.getSubTaskIdList().size() == 1) {
                LocalDateTime dateTime = subTaskList.get(epicForUpdate.getSubTaskIdList().get(0)).getStartTime();
                epicForUpdate.setStartTime(dateTime);
            } else if (epicForUpdate.getSubTaskIdList().size() > 1) {
                Optional<LocalDateTime> optionalEarliestTime = epicForUpdate.getSubTaskIdList().stream()
                        .filter(id -> subTaskList.get(id).getStartTime() != null)
                        .map(id -> subTaskList.get(id).getStartTime())
                        .reduce((startTime1, startTime2) -> startTime1.isBefore(startTime2) ? startTime1 : startTime2);
                if (optionalEarliestTime.isPresent()) {
                    epicForUpdate.setStartTime(optionalEarliestTime.get());
                } else {
                    epicForUpdate.setStartTime(null);
                }
                updateEpicByNewEpic(epicForUpdate);
            }
        }
    }

    @Override
    public Duration getTaskDuration(Task task) {
        return task.getDuration();
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        List<Task> tasksWithoutStartDateTime = new ArrayList<>();
        List<Task> prioritizedListOfAllTasks = getListOfAllTasks()
                .values()
                .stream()
                .peek((Task task) -> {
                    if (task.getStartTime() == null) {
                        tasksWithoutStartDateTime.add(task);
                    }
                }).filter(task -> task.getStartTime() != null)
                .sorted((Task task1, Task task2) -> {
                    if (task1.getStartTime().isEqual(task2.getStartTime())) {
                        return 0;
                    } else if (task1.getStartTime().isBefore(task2.getStartTime())) {
                        return -1;
                    } else {
                        return 1;
                    }
                }).collect(Collectors.toList());
        prioritizedListOfAllTasks.addAll(tasksWithoutStartDateTime);
        return prioritizedListOfAllTasks;
    }

    private boolean checkIsStartTimeFree(LocalDateTime startTime) {
        boolean isStartTimeFree = true;
        if (getListOfAllTasks().size() == 1) {
            return isStartTimeFree;
        } else {
            for (Task task : getListOfAllTasks().values()) {
                if ((!task.getTypeTask().equals(TypeTask.EPIC)) & getStartDateTime(task) != null) {
                    if (startTime.isEqual(getStartDateTime(task))) {
                        isStartTimeFree = false;
                        try {
                            new ManagerDateTimeException("Время начала задачи пересекается с ранее "
                                    + "запланированной задачей " + task.getId());
                        } catch (ManagerDateTimeException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } else {
                    if ((startTime.isEqual(getStartDateTime(task)) || (startTime.isAfter(getStartDateTime(task)))
                            & ((startTime.isEqual(getStartDateTime(task)
                            .plus((task.getDuration()))))
                            || startTime.isBefore(getStartDateTime(task)
                            .plus((task.getDuration())))))) {
                        isStartTimeFree = false;
                        try {
                            new ManagerDateTimeException("Время начала задачи пересекается с ранее "
                                    + "запланированной задачей " + task.getId());
                        } catch (ManagerDateTimeException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        }
        return isStartTimeFree;
    }

    private boolean checkIsDurationFree(LocalDateTime startTime, int duration) {
        boolean isDurationFree = true;
        if (getListOfAllTasks().size() == 1) {
            return isDurationFree;
        } else {
            for (Task task : getListOfAllTasks().values()) {
                if ((!task.getTypeTask().equals(TypeTask.EPIC)) & getStartDateTime(task) != null) {
                    if (startTime.isBefore(task.getStartTime()
                            .plus((task.getDuration())))
                            & startTime.plus(Duration.ofMinutes(duration))
                            .isAfter(task.getStartTime())) {
                        isDurationFree = false;
                        try {
                            new ManagerDateTimeException("Время отведённое на  выполнение задачи пересекается "
                                    + "с " + "ранее " + "запланированной задачей " + task.getId());
                        } catch (ManagerDateTimeException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            return isDurationFree;
        }
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
