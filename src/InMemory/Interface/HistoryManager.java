package InMemory.Interface;

import model.Task;

public interface HistoryManager<T> {
    HistoryManager<Task> getHistory();

    void add(Task task);


}
