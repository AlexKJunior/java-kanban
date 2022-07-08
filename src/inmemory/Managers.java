package inmemory;

import inmemory.intrface.HistoryManager;
import inmemory.manager.FileBackedTasksManager;
import inmemory.manager.InMemoryHistoryManager;

import java.io.File;
public class Managers {

    public static FileBackedTasksManager getDefault() {
        return new FileBackedTasksManager(new File("resources/task.csv"));
    }

    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager () {
            @Override
            public void clear () {

            }
        };
    }
}