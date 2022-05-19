package InMemory;

import InMemory.Interface.HistoryManager;
import InMemory.Interface.TaskManager;
import InMemory.Manager.InMemoryHistoryManager;
import InMemory.Manager.InMemoryTaskManager;
import model.Task;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager () {
            @Override
            public HistoryManager<Task> getHistory () {
                return null;
            }
        };
    }

    public static <T> HistoryManager<T> getDefaultHistory() {
        return new InMemoryHistoryManager ();
    }
}