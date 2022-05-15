package InMemory;

import model.Epic;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private final HashMap<Integer, Task> taskStorage = new HashMap<> ();
    private final HashMap<Integer, Epic> EpicStorage = new HashMap<> ();
    private final HashMap<Integer, Epic> subTaskStorage = new HashMap<> ();

    public HashMap<Integer, Task> getTaskStorage () {
        return taskStorage;
    }

    public HashMap<Integer, Epic> getEpicStorage () {
        return EpicStorage;
    }

    public HashMap<Integer, Epic> getSubTaskStorage () {
        return subTaskStorage;
    }

    public void saveToStorage (Object object) {
        switch (object.getClass ().toString ()) {
            case "class Task": {
                taskStorage.put ( ((Task) object).getId (), (Task) object );
                break;
            }
            case "class Epic": {
                EpicStorage.put ( ((Epic) object).getId (), (Epic) object );
                break;
            }
            case "class Epic$SubTask": {
                subTaskStorage.put ( ((Epic) object).getId (), (Epic) object );
                break;
            }
        }
    }

    public ArrayList<Object> getCompleteListOfAnyTasks (HashMap<Integer, ? extends Task> HashMap) {
        ArrayList<Object> completeListOfAnyTasks = new ArrayList<> ();

        for (Integer key : HashMap.keySet ()) {
            completeListOfAnyTasks.add ( HashMap.get ( key ) );
        }
        return completeListOfAnyTasks;
    }

    //Удаление всех задач;

    public void deleteAllTasksOfAnyType (HashMap<Integer, ? extends Task> HashMap) {
        HashMap.clear ();
    }
    //Получение по идентификатору;

    public Object getTaskOfAnyTypeById (int id) {
        Object taskOfAnyKind = null;

        if (taskStorage.get ( id ) != null) {
            taskOfAnyKind = taskStorage.get ( id );
        } else if (EpicStorage.get ( id ) != null) {
            taskOfAnyKind = EpicStorage.get ( id );
        } else if (subTaskStorage.get ( id ) != null) {
            taskOfAnyKind = subTaskStorage.get ( id );
        }
        return taskOfAnyKind;
    }

    public Object createCopyOfTaskOfAnyType (Object object) {
        switch (object.getClass ().toString ()) {
            case "class Task": {
                return new Task ( (Task) object );
            }
            case "class Epic$SubTask":
            case "class Epic": {
                return new Epic ( (Epic) object );
            }
            default:
                return null;
        }
    }

    public void updateTaskOfAnyType (int id, Object object) {
        switch (object.getClass ().toString ()) {
            case "class Task": {
                taskStorage.put ( id, (Task) object );
                break;
            }
            case "class Epic": {
                EpicStorage.put ( id, (Epic) object );
                break;
            }
            case "class Epic$SubTask": {
                subTaskStorage.put ( id, (Epic) object );
                break;
            }
        }
    }
    //Удаление по идентификатору.

    public void removeTaskOfAnyTypeById (int id) {
        for (Integer task : taskStorage.keySet ()) {
            if (id == task) {
                taskStorage.remove ( id );
                break;
            }
        }
        for (Integer Epic : EpicStorage.keySet ()) {
            if (id == Epic) {
                EpicStorage.remove ( id );
                break;
            }
        }
        for (Integer subTask : subTaskStorage.keySet ()) {
            if (id == subTask) {
                subTaskStorage.remove ( id );
                break;
            }
        }
    }

    public Enum getCompleteListOfSubTaskByEpic (Epic Epic) {
        return Epic.getStatus ();
    }

}