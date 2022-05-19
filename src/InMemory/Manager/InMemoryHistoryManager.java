package InMemory.Manager;

import InMemory.Interface.HistoryManager;
import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager<T> implements HistoryManager<T> {
    private final static int MAX_HISTORY_LENGTH = 10;
    private final ArrayList<Task> historyOfRequestsList;
    private InMemory.Interface.HistoryManager<Task> HistoryManager;

    public InMemoryHistoryManager() {
        this.historyOfRequestsList = new ArrayList<>(MAX_HISTORY_LENGTH);
    }

    @Override
    public void add(Task task) {
        if (task != null) {
            if (historyOfRequestsList.size() < MAX_HISTORY_LENGTH) {
                historyOfRequestsList.add(task);
            } else {
                historyOfRequestsList.remove(0);
                historyOfRequestsList.add(task);
            }
        }
    }

    @Override
    public HistoryManager<Task> getHistory() {
        return HistoryManager;
    }
}