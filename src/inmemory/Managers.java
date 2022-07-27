package inmemory;

import inmemory.interfaces.HistoryManager;
import inmemory.interfaces.TaskManager;
import inmemory.manager.FileBackedTasksManager;
import inmemory.manager.HTTPTasksManager;
import inmemory.manager.InMemoryHistoryManager;
import inmemory.manager.InMemoryTaskManager;

import java.io.File;

public class Managers {

    public static InMemoryTaskManager getDefaultManager() {
        return new InMemoryTaskManager();
    }

    public static HTTPTasksManager getDefaultManager(String url, String keyForSave) {
        return new HTTPTasksManager(url, keyForSave);
    }

    public static FileBackedTasksManager getDefault() {
        return new FileBackedTasksManager(new File("resources/task.csv"));
    }

    public static TaskManager getDefaultInMemoryTaskManager() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager() {

            @Override
            public void clear() {
            }
        };
    }
}