package test.manager;

import static org.junit.jupiter.api.Assertions.*;

import inmemory.manager.FileBackedTasksManager;
import inmemory.intrface.TaskManager;

import java.io.File;

class FileBackedTasksManagerTest extends TaskManagerTest {
    TaskManager manager = new FileBackedTasksManager( new File ( "resources/task.csv" ));

    TaskManager createManager() {
        return manager;
    }

}
