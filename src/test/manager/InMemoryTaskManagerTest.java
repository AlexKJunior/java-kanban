package test.manager;

import static org.junit.jupiter.api.Assertions.*;

import inmemory.Managers;
import inmemory.intrface.TaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest {
    TaskManager manager = Managers.getDefaultInMemoryTaskManager();

    TaskManager createManager() {
        return manager;
    }
}