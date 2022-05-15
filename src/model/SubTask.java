package model;

import java.util.Objects;

public class SubTask extends Task {
    private int nameEpicTask;

    public <TaskStatus> SubTask(String title, String description, TaskStatus taskStatus) {
        super(title, description, String.valueOf(taskStatus));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask subTask = (SubTask) o;
        return nameEpicTask == subTask.nameEpicTask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameEpicTask);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "nameEpicTask=" + nameEpicTask +
                '}';
    }
}
