package inmemory;

import inmemory.intrface.HistoryManager;
import inmemory.intrface.TaskManager;
import inmemory.manager.FileBackedTasksManager;
import inmemory.manager.InMemoryHistoryManager;
import inmemory.manager.InMemoryTaskManager;

import java.io.File;
public class Managers {

    public static InMemoryTaskManager getDefaultManager() {
        return new InMemoryTaskManager();
    }
    public static FileBackedTasksManager getDefault() {
        return new FileBackedTasksManager(new File("resources/task.csv"));
    }

    public static TaskManager getDefaultInMemoryTaskManager () {
        return new InMemoryTaskManager ();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager () {

            @Override
            public void clear () {

            }
        };
    }
}