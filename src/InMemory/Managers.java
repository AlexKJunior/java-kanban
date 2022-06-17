package InMemory;

import InMemory.Interface.HistoryManager;
import InMemory.Interface.TaskManager;
import InMemory.Manager.InMemoryHistoryManager;
import InMemory.Manager.InMemoryTaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}