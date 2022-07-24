package test.manager;

import inmemory.manager.FileBackedTasksManager;
import inmemory.interfaces.TaskManager;

import java.io.File;

class FileBackedTasksManagerTest extends TaskManagerTest {
    TaskManager manager = new FileBackedTasksManager( new File ( "resources/task.csv" ));

    TaskManager createManager() {
        return manager;
    }
}
