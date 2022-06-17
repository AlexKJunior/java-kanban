package inmemory;

import inmemory.intrface.HistoryManager;
import inmemory.intrface.TaskManager;
import inmemory.manager.InMemoryHistoryManager;
import inmemory.manager.InMemoryTaskManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}