package test.manager;

import inmemory.Managers;
import inmemory.interfaces.TaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest {
    TaskManager manager = Managers.getDefaultInMemoryTaskManager();

    TaskManager createManager() {
        return manager;
    }
}