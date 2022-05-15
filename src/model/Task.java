package model;

import java.util.Objects;

public class Task {

    private int id;
    private final String name;
    private final String description;
    private String status;

     public Task(String nameTask, String descriptionTask, String statusTask) {

        this.name = nameTask;
        this.description = descriptionTask;
        this.status = statusTask;
    }

     public Task(String nameTask, String descriptionTask) {

        this.name = nameTask;
        this.description = descriptionTask;
    }

    Task(Task task) {
        this(task.name, task.description, task.status);
    }

     public int getId() {
        return id;
    }

     public String getName() {
        return name;
    }

     public String getDescription() {
        return description;
    }

     public String getStatus() {
        return status;
    }

     public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID задачи model.Task=\"" + id + "\", Название задачи=\"" + name + "\", Описание=\"" + description
                + "\", Статус=\"" + status + "\"";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, status);
    }



}

