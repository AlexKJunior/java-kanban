package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private final HashMap<Integer, Task> taskStorage = new HashMap<>();
    private final HashMap<Integer, EpicTask> epicTaskStorage = new HashMap<>();
    private final HashMap<Integer, EpicTask.SubTask> subTaskStorage = new HashMap<>();

    public HashMap<Integer, Task> getTaskStorage() {
        return taskStorage;
    }

    public HashMap<Integer, EpicTask> getEpicTaskStorage() {
        return epicTaskStorage;
    }

    public HashMap<Integer, EpicTask.SubTask> getSubTaskStorage() {
        return subTaskStorage;
    }

    public void saveToStorage(Object object) {
        switch (object.getClass().toString()) {
            case "class model.Task": {
                taskStorage.put(((Task) object).getId(), (Task) object);
                break;
            }
            case "class model.EpicTask": {
                epicTaskStorage.put(((EpicTask) object).getId(), (EpicTask) object);
                break;
            }
            case "class model.EpicTask$SubTask": {
                subTaskStorage.put(((EpicTask.SubTask) object).getId(), (EpicTask.SubTask) object);
                break;
            }
        }
    }

    public ArrayList<Object> getCompleteListOfAnyTasks(HashMap<Integer, ? extends Task> HashMap) {
        ArrayList<Object> completeListOfAnyTasks = new ArrayList<>();

        for (Integer key : HashMap.keySet()) {
            completeListOfAnyTasks.add(HashMap.get(key));
        }
        return completeListOfAnyTasks;
    }

    //Удаление всех задач;

    public void deleteAllTasksOfAnyType(HashMap<Integer, ? extends Task> HashMap) {
        HashMap.clear();
    }
    //Получение по идентификатору;

    public Object getTaskOfAnyTypeById(int id) {
        Object taskOfAnyKind = null;

        if (taskStorage.get(id) != null) {
            taskOfAnyKind = taskStorage.get(id);
        } else if (epicTaskStorage.get(id) != null) {
            taskOfAnyKind = epicTaskStorage.get(id);
        } else if (subTaskStorage.get(id) != null) {
            taskOfAnyKind = subTaskStorage.get(id);
        }
        return taskOfAnyKind;
    }

    public Object createCopyOfTaskOfAnyType(Object object) {
        switch (object.getClass().toString()) {
            case "class model.Task": {
                return new Task((Task) object);
            }
            case "class model.EpicTask$SubTask": {
                return new EpicTask.SubTask((EpicTask.SubTask) object);
            }
            case "class model.EpicTask": {
                return new EpicTask((EpicTask) object);
            }
            default:
                return null;
        }
    }

    public void updateTaskOfAnyType(int id, Object object) {
        switch (object.getClass().toString()) {
            case "class model.Task": {
                taskStorage.put(id, (Task) object);
                break;
            }
            case "class model.EpicTask": {
                epicTaskStorage.put(id, (EpicTask) object);
                break;
            }
            case "class model.EpicTask$SubTask": {
                subTaskStorage.put(id, (EpicTask.SubTask) object);
                break;
            }
        }
    }
    //Удаление по идентификатору.

    public void removeTaskOfAnyTypeById(int id) {
        for (Integer task : taskStorage.keySet()) {
            if (id == task) {
                taskStorage.remove(id);
                break;
            }
        }
        for (Integer epicTask : epicTaskStorage.keySet()) {
            if (id == epicTask) {
                epicTaskStorage.remove(id);
                break;
            }
        }
        for (Integer subTask : subTaskStorage.keySet()) {
            if (id == subTask) {
                subTaskStorage.remove(id);
                break;
            }
        }
    }

    public ArrayList<EpicTask.SubTask> getCompleteListOfSubTaskByEpicTask(EpicTask epicTask) {
        return epicTask.getSubTasks();
    }

}

